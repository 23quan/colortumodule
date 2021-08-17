package com.colortu.colortu_module.colortu_study.activity;

import android.Manifest;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.api.PermissionListener;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyAudioCreateViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyAudiocreateBinding;
import com.colortu.colortu_module.databinding.ActivityStudyCreateBinding;

/**
 * @author : Code23
 * @time : 2021/4/16
 * @module : StudyAudioCreateActivity
 * @describe :语音创建自习室界面
 */
@Route(path = BaseConstant.STUDY_AUDIOCREATE)
public class StudyAudioCreateActivity extends BaseActivity<StudyAudioCreateViewModel, ActivityStudyAudiocreateBinding> {
    //是否长按
    private boolean isLongClick;
    //权限列表
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_audiocreate;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.audiocreateParentview);

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

        binding.audiocreateTimetip.setText(getResources().getString(R.string.record_message2));
        binding.audiocreateTranslate.setMovementMethod(ScrollingMovementMethod.getInstance());

        //录音监听
        viewModel.isvisibleLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//显示开始录音界面
                    binding.audiocreateEndview.setVisibility(View.GONE);
                    binding.audiocreateGif.setVisibility(View.GONE);
                    binding.audiocreateStartview.setVisibility(View.VISIBLE);
                } else {//显示结束界面
                    binding.audiocreateStartview.setVisibility(View.GONE);
                    binding.audiocreateEndview.setVisibility(View.VISIBLE);
                }
            }
        });

        initData();
    }

    public void initData() {
        //长按录音
        binding.audiocreateInputview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isLongClick = true;
                binding.audiocreateInputview.setKeepScreenOn(true);
                OnStartRecorder();
                return false;
            }
        });
        //取消监听
        binding.audiocreateInputview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP://离开or取消
                    case MotionEvent.ACTION_CANCEL:
                        if (isLongClick) {
                            isLongClick = false;
                            binding.audiocreateInputview.setKeepScreenOn(false);
                            OnStopRecorder();
                        }
                        break;
                }
                return false;
            }
        });
    }

    //开始录音
    private void OnStartRecorder() {
        //抢占音频焦点
        AudioFocusUtils.initAudioFocus(BaseApplication.getContext());
        binding.audiocreateInputview.setBackgroundColor(getResources().getColor(R.color.base_blue10));
        binding.audiocreateGif.setVisibility(View.VISIBLE);
        viewModel.isstop.set(true);
        viewModel.audioRecord.OnRecorder(true);
    }

    //结束录音
    private void OnStopRecorder() {
        //解绑音频焦点
        AudioFocusUtils.abandonAudioFocus();
        binding.audiocreateInputview.setBackgroundColor(getResources().getColor(R.color.base_blue7));
        viewModel.isstop.set(false);
        viewModel.audioRecord.OnRecorder(false);
    }
}
