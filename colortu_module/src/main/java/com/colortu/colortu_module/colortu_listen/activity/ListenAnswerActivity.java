package com.colortu.colortu_module.colortu_listen.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationUtil;
import com.colortu.colortu_module.colortu_listen.adapter.ListenAnswerAdapter;
import com.colortu.colortu_module.colortu_base.bean.ListenClassBean;
import com.colortu.colortu_module.colortu_listen.viewmodel.ListenAnswerViewModel;
import com.colortu.colortu_module.databinding.ActivityListenAnswerBinding;

import java.io.IOException;
import java.util.List;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenAnswerActivity
 * @describe :听写答案界面
 */
@Route(path = BaseConstant.LISTEN_ANSWER)
public class ListenAnswerActivity extends BaseActivity<ListenAnswerViewModel, ActivityListenAnswerBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //是否播放
    private boolean isPlay = false;
    private MediaPlayer mediaPlayer;
    //听写答案列表适配器
    private ListenAnswerAdapter listenAnswerAdapter;
    //单词音频列表数据
    private List<ListenClassBean.DataBean.PoetryVOSBean.WordsBean> wordsBeanList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_listen_answer;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.answerParentview);

        wordsBeanList = (List<ListenClassBean.DataBean.PoetryVOSBean.WordsBean>) bundle.get("wordsbean");

        //听写答案列表适配器实例化
        listenAnswerAdapter = new ListenAnswerAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.answerList.setLayoutManager(linearLayoutManager);
        binding.answerList.setAdapter(listenAnswerAdapter);
        //单词音频列表数据刷新
        listenAnswerAdapter.clear();
        listenAnswerAdapter.addAll(wordsBeanList);
        listenAnswerAdapter.notifyDataSetChanged();

        /**
         * 播放监听
         */
        listenAnswerAdapter.setOnClickAnswerListener(new ListenAnswerAdapter.OnClickAnswerListener() {
            @Override
            public void OnClickAnswer(final ListenClassBean.DataBean.PoetryVOSBean.WordsBean wordsBean, final int position) {
                if (isPlay) {
                    return;
                }
                wordsBeanList.get(position).setPlaying(true);
                listenAnswerAdapter.notifyItemChanged(position);
                isPlay = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //取消息屏app销毁
                        SuicideUtils.onCancelKill();

                        if (ChannelUtil.isHuaWei()) {
                            //发送通知栏消息
                            BaseApplication.onStartNotification();
                        }

                        onStartDictationVoice(wordsBean, position);
                    }
                }).start();
            }
        });
    }

    /**
     * 播放听写语音
     *
     * @param wordsBean
     * @param position
     */
    private void onStartDictationVoice(ListenClassBean.DataBean.PoetryVOSBean.WordsBean wordsBean, final int position) {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(BaseConstant.ListenAudioUrl + wordsBean.getWordAudioUrl());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.setLooping(false);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                onReleaseDictationVoice();

                wordsBeanList.get(position).setPlaying(false);
                listenAnswerAdapter.notifyItemChanged(position);
                isPlay = false;

                //启动息屏app销毁
                SuicideUtils.onStartKill();
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (mediaPlayer == mediaPlayer) {
                    mediaPlayer.start();
                }
            }
        });
        mediaPlayer.prepareAsync();
    }

    /**
     * 释放开始听写语音
     */
    private void onReleaseDictationVoice() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ChannelUtil.isHuaWei()) {
            //销毁通知栏消息
            if (NotificationUtil.isExistNotification) {
                BaseApplication.onStopNotification();
            }
        }

        onReleaseDictationVoice();
    }
}
