package com.colortu.colortu_module.colortu_listen.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioPlayer;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenAnswerViewModel
 * @describe :听写答案界面ViewModel
 */
public class ListenAnswerViewModel extends BaseActivityViewModel<BaseRequest> implements AudioFocusUtils.OnAudioFocusListener {
    //判断是否播放完成
    public MutableLiveData<Boolean> isPlayLiveData = new MutableLiveData<>();

    //播放器工具类
    public AudioPlayer audioPlayer;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        audioPlayer = new AudioPlayer();
        initPlay();
        AudioFocusUtils.setOnAudioFocusListener(this);
    }

    /**
     * 初始化播放
     */
    public void initPlay() {
        audioPlayer.setOnPlayerListener(new AudioPlayer.OnPlayerListener() {
            @Override
            public void playerstart() {//播放
                //取消息屏app销毁
                SuicideUtils.onCancelKill();
            }

            @Override
            public void playerpause() {//暂停
                unPlayer();
            }

            @Override
            public void playerstop() {//停止
                unPlayer();
            }

            @Override
            public void playerfinish() {//完成
                unPlayer();
            }

            @Override
            public void playerfailure() {//失败
                unPlayer();
            }
        });
    }

    private void unPlayer() {
        //解绑音频焦点
        AudioFocusUtils.abandonAudioFocus();
        //启动息屏app销毁
        SuicideUtils.onStartKill();
        isPlayLiveData.setValue(true);
    }

    /**
     * 失去焦点
     */
    @Override
    public void onLossAudioFocus() {
        if (audioPlayer.isPlay()) {
            //暂停当前播放
            audioPlayer.onStop();
        }
    }

    /**
     * 获取焦点
     */
    @Override
    public void onGainAudioFocus() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑音频焦点
        AudioFocusUtils.abandonAudioFocus();
        //暂停播放，释放资源
        if (audioPlayer != null) {
            audioPlayer.onRelease();
        }
    }
}
