package com.colortu.colortu_module.colortu_study.activity;

import android.Manifest;
import android.content.Intent;
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
import com.colortu.colortu_module.colortu_base.core.service.AudioFocusService;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyInputViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyInputBinding;

/**
 * @author : Code23
 * @time : 2021/4/13
 * @module : StudyInputActivity
 * @describe :录入个性语音界面
 */
@Route(path = BaseConstant.STUDY_INPUT)
public class StudyInputActivity extends BaseActivity<StudyInputViewModel, ActivityStudyInputBinding> implements AudioFocusService.OnAudioFocusListener{
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //权限列表
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_input;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.inputParentview);
        AudioFocusService.setOnAudioFocusListener(this);

        /**
         * 检查是否有相应的权限
         */
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

        viewModel.isfirst.set(bundle.getInt("isfirst"));

        binding.inputTranslate.setMovementMethod(ScrollingMovementMethod.getInstance());

        /**
         * 录音监听
         */
        viewModel.isvisibleLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//显示开始录音界面
                    binding.inputEndview.setVisibility(View.GONE);
                    binding.inputGif.setVisibility(View.GONE);
                    binding.inputStartview.setVisibility(View.VISIBLE);
                } else {//显示结束界面
                    binding.inputStartview.setVisibility(View.GONE);
                    binding.inputEndview.setVisibility(View.VISIBLE);
                }
            }
        });

        /**
         * 保存录音
         */
        binding.inputSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (viewModel.isupload.get()) {
                    case 1://正在上传
                        TipToast.tipToastShort(getResources().getString(R.string.record_translate));
                        break;
                    case 2://上传失败
                        TipToast.tipToastShort(getResources().getString(R.string.record_uploadfailure));
                        break;
                    case 3://上传成功
                        if (EmptyUtils.stringIsEmpty(viewModel.audiourl.get())) {
                            Intent intent = new Intent();
                            intent.putExtra("audiourl", viewModel.audiourl.get());
                            setResult(BaseConstant.REQUEDT_STUDYINPUT, intent);
                            finish();
                        } else {
                            TipToast.tipToastShort(getResources().getString(R.string.record_uploadfailure));
                        }
                        break;
                }
            }
        });

        /**
         * 录音按钮监听
         */
        binding.inputInputview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下
                        OnStartRecorder();
                        break;
                    case MotionEvent.ACTION_UP://离开or取消
                    case MotionEvent.ACTION_CANCEL:
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
        binding.inputInputview.setBackgroundColor(getResources().getColor(R.color.base_blue10));
        binding.inputGif.setVisibility(View.VISIBLE);
        viewModel.isstop.set(true);
        viewModel.audioRecord.OnRecorder(true);
    }

    /**
     * 结束录音
     */
    private void OnStopRecorder() {
        binding.inputInputview.setBackgroundColor(getResources().getColor(R.color.base_blue7));
        viewModel.isstop.set(false);
        viewModel.audioRecord.OnRecorder(false);
    }

    /**
     * 失去焦点
     */
    @Override
    public void onLossAudioFocus() {
        if (viewModel.audioRecord != null) {
            if (viewModel.audioRecord.isPlayer()) {
                viewModel.audioRecord.OnPlayer(false);
            }
        }
    }

    /**
     * 获取焦点
     */
    @Override
    public void onGainAudioFocus() {

    }
}
