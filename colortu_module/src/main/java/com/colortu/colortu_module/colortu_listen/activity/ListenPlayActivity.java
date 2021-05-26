package com.colortu.colortu_module.colortu_listen.activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationUtil;
import com.colortu.colortu_module.colortu_base.bean.ListenClassBean;
import com.colortu.colortu_module.colortu_listen.viewmodel.ListenPlayViewModel;
import com.colortu.colortu_module.databinding.ActivityListenPlayBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenPlayActivity
 * @describe :听力播放界面
 */
@Route(path = BaseConstant.LISTEN_PLAY)
public class ListenPlayActivity extends BaseActivity<ListenPlayViewModel, ActivityListenPlayBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //音频焦点管理
    private AudioManager audioManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_listen_play;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.playParentview);

        viewModel.subjectid.set(bundle.getInt("subjectid"));
        viewModel.versionid.set(bundle.getInt("versionid"));
        viewModel.listenClassBean.set((List<ListenClassBean.DataBean.PoetryVOSBean.WordsBean>) bundle.getSerializable("wordsbean"));

        if (viewModel.listenClassBean.get() != null) {
            if (viewModel.listenClassBean.get().size() == 0) {
                TipToast.tipToastShort(getResources().getString(R.string.none_dictation));
                finish();
            }
        }

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        /**
         * 开始播放监听
         */
        viewModel.isPlay.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.playTime.setVisibility(View.VISIBLE);
                    binding.playPlay.setVisibility(View.VISIBLE);
                    binding.playStarting.setVisibility(View.GONE);
                }
            }
        });

        /**
         * 答案显示监听
         */
        viewModel.isShowAnswer.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.playAnswer.setVisibility(View.VISIBLE);
                } else {
                    binding.playAnswer.setVisibility(View.INVISIBLE);
                }
            }
        });

        viewModel.initData();
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
                    if (!viewModel.isStart) {
                        viewModel.onStopTipVoice();
                    }
                    viewModel.onStopWords();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ChannelUtil.isHuaWei()) {
            //销毁通知栏消息
            if (NotificationUtil.isExistNotification) {
                BaseApplication.onStopNotification();
            }
        }
        viewModel.onDispose();
        audioManager.abandonAudioFocus(onAudioFocusChangeListener);
    }
}
