package com.colortu.colortu_module.colortu_record.activity;

import android.Manifest;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.api.PermissionListener;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_record.viewmodel.RecordInputViewModel;
import com.colortu.colortu_module.databinding.ActivityRecordInputBinding;

/**
 * @author : Code23
 * @time : 2020/12/156
 * @module : RecordInputActivity
 * @describe :录音界面
 */
@Route(path = BaseConstant.RECORD_INPUT)
public class RecordInputActivity extends BaseActivity<RecordInputViewModel, ActivityRecordInputBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //1 未选择科目，2科目详情进来
    private int type;
    //科目id
    private int subjectId;
    //权限列表
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

    @Override
    public int getLayoutId() {
        return R.layout.activity_record_input;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.inputParentview);
        BaseApplication.getInstance().addActivity(this);

        //检查是否有相应的权限
        if (ChannelUtil.isHuaWei()) {
            checkDevicePermission(permissions, new PermissionListener() {
                @Override
                public void permissionSuccess() {//成功
                }

                @Override
                public void permissionFail() {//失败
                    TipToast.tipToastShort(getResources().getString(R.string.refused_permission));
                    finish();
                }
            });
        }

        type = bundle.getInt("type");
        subjectId = bundle.getInt("subjectId");

        viewModel.type.set(type);
        if (type == 2) {
            viewModel.subjectId.set(subjectId);
        }

        binding.inputTranslate.setMovementMethod(ScrollingMovementMethod.getInstance());

        //会员提示监听
        viewModel.isvisibleVip.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.inputViptip.setVisibility(View.GONE);
                }
            }
        });

        //录音监听
        viewModel.isvisibleLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//显示开始录音界面
                    binding.inputEndview.setVisibility(View.GONE);
                    binding.inputGif.setVisibility(View.GONE);
                    binding.inputInputcommandbtn.setVisibility(View.VISIBLE);
                    binding.inputStartview.setVisibility(View.VISIBLE);
                } else {//显示结束界面
                    binding.inputStartview.setVisibility(View.GONE);
                    binding.inputEndview.setVisibility(View.VISIBLE);
                }
            }
        });

        initData();
    }

    /**
     * 录音按钮监听
     */
    public void initData() {
        binding.inputInputview.setEnabled(false);
        binding.inputInputview.setClickable(false);
        binding.inputInputview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下
                        binding.inputViptip.setVisibility(View.VISIBLE);
                        binding.inputViptip.setText(getResources().getString(R.string.record_message3));
                        binding.inputInputview.setKeepScreenOn(true);
                        OnStartRecorder();
                        break;
                    case MotionEvent.ACTION_UP://离开or取消
                    case MotionEvent.ACTION_CANCEL:
                        viewModel.showVipTip();
                        binding.inputInputview.setKeepScreenOn(false);
                        OnStopRecorder();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 开始录音
     */
    private void OnStartRecorder() {
        //获取音频焦点
        AudioFocusUtils.initAudioFocus(BaseApplication.getContext());
        binding.inputInputcommandbtn.setVisibility(View.GONE);
        binding.inputInputbtn.setBackgroundColor(getResources().getColor(R.color.base_blue10));
        binding.inputGif.setVisibility(View.VISIBLE);
        viewModel.isstop.set(true);
        viewModel.audioRecord.OnRecorder(true);
    }

    /**
     * 结束录音
     */
    private void OnStopRecorder() {
        //解绑音频焦点
        AudioFocusUtils.abandonAudioFocus();
        binding.inputInputbtn.setBackgroundColor(getResources().getColor(R.color.base_blue7));
        viewModel.isstop.set(false);
        viewModel.audioRecord.OnRecorder(false);
    }
}
