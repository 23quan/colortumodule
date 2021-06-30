package com.colortu.colortu_module.colortu_base.core.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.colortu.colortu_module.BR;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.api.PermissionListener;
import com.colortu.colortu_module.colortu_base.core.http.NetWorkUtils;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.OSUtil;
import com.colortu.colortu_module.colortu_base.utils.TipToast;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * @author : Code23
 * @time : 2020/11/24
 * @module : BaseActivity
 * @describe :Activity父类
 */
public abstract class BaseActivity<VM extends BaseActivityViewModel, VDB extends ViewDataBinding> extends SwipeBackActivity {
    protected VM viewModel;
    protected VDB binding;

    //记录处于前台的Activity
    private static BaseActivity baseActivity = null;

    //设置layoutId
    public abstract int getLayoutId();

    //初始化视图
    public abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ChannelUtil.isTeeMo()) {
            this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        }
        setContentView(getLayoutId());
        BaseApplication.getInstance().addActivity(this);
        //路由注册
        ARouter.getInstance().inject(this);
        viewModel = new ViewModelProvider(this).get(getTClass());
        viewModel.finish.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                finish();
            }
        });
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        //所有布局中dababinding对象变量名称都是vm
        binding.setVariable(BR.viewmodel, viewModel);
        //立即更新UI
        binding.executePendingBindings();
        getLifecycle().addObserver(viewModel);
        //初始化view
        initView(savedInstanceState);
        //判断网络是否可用
        if (!NetWorkUtils.isConnected(this)) {
            TipToast.tipToastLong(getResources().getString(R.string.no_networt));
        }
        //初始化音频焦点
        initAudioFocus();
        //控制二维码显示
        BaseApplication.getInstance().startAfterActivity(BaseUIKit.AfterEnter);
    }

    @Override
    protected void onStart() {
        baseActivity = this;
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        getLifecycle().removeObserver(viewModel);
        //控制二维码显示
        BaseApplication.getInstance().startAfterActivity(BaseUIKit.AfterExit);
        super.onDestroy();
    }

    /**
     * 获取泛型对相应的Class对象
     *
     * @return
     */
    private Class<VM> getTClass() {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        //返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
        return (Class) type.getActualTypeArguments()[0];//<T>
    }

    /**
     * 获取当前处于前台的activity
     */
    public static BaseActivity getBaseActivity() {
        return baseActivity;
    }

    /**
     * ----------------------------------------音频焦点监听---------------------------------------------
     */
    //音频焦点管理
    private AudioManager audioManager;

    /**
     * 初始化音频焦点
     */
    public void initAudioFocus() {
        if (audioManager == null) {
            //音频焦点
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //AudioAttributes 配置(多媒体场景，申请的是音乐流)
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                // 初始化AudioFocusRequest
                AudioFocusRequest audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                        .setAudioAttributes(audioAttributes)
                        //设置是否允许延迟获取焦点
                        .setAcceptsDelayedFocusGain(true)
                        //设置AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK会暂停，系统不会压低声音
                        .setWillPauseWhenDucked(true)
                        //设置焦点监听回调
                        .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                        .build();
                //申请焦点
                audioManager.requestAudioFocus(audioFocusRequest);
            } else {
                audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            }
        }
    }

    /**
     * 注销音频焦点
     */
    public void abandonAudioFocus() {
        if (audioManager != null) {
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
            audioManager = null;
        }
    }

    /**
     * 音频焦点监听
     */
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    if (onAudioFocusListener != null) {
                        onAudioFocusListener.onLossAudioFocus();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    if (onAudioFocusListener != null) {
                        onAudioFocusListener.onGainAudioFocus();
                    }
                    break;
            }
        }
    };

    private OnAudioFocusListener onAudioFocusListener;

    public void setOnAudioFocusListener(OnAudioFocusListener onAudioFocusListener) {
        this.onAudioFocusListener = onAudioFocusListener;
    }

    public interface OnAudioFocusListener {
        //失去焦点
        void onLossAudioFocus();

        //获取焦点
        void onGainAudioFocus();
    }

    /**
     * ----------------------------------------申请动态权限---------------------------------------------
     */
    private final int REQUEST_CODE_PERMISSION = 10000;
    private PermissionListener mPermissionListener;
    //用于存放需要授权的权限
    private List<String> permissionList = new ArrayList<>();

    /**
     * 检测设备信息权限
     *
     * @param listener
     */
    public void checkDevicePermission(String[] permission, PermissionListener listener) {
        if (OSUtil.isVersionM()) {
            checkRequestPermission(permission, listener);
        } else {
            listener.permissionSuccess();
        }
    }

    /**
     * 权限申请
     *
     * @param listener
     */
    public void checkRequestPermission(String[] permissions, PermissionListener listener) {
        this.mPermissionListener = listener;
        if (permissions == null || permissions.length <= 0) {
            return;
        }
        permissionList.clear();
        for (String permission : permissions) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(this, permission);
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (permissionList.isEmpty()) {
            mPermissionListener.permissionSuccess();
        } else {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    /**
     * 申请权限结果返回处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        mPermissionListener.permissionFail();
                        return;
                    }
                }
                mPermissionListener.permissionSuccess();
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
