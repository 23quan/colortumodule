package com.colortu.colortu_module.colortu_listen.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioPlayer;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenAnswerViewModel
 * @describe :听写答案界面ViewModel
 */
public class ListenAnswerViewModel extends BaseActivityViewModel<BaseRequest> {
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
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                isPlayLiveData.setValue(true);
            }

            @Override
            public void playerstop() {//停止
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                isPlayLiveData.setValue(true);
            }

            @Override
            public void playerfinish() {//完成
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                isPlayLiveData.setValue(true);
            }

            @Override
            public void playerfailure() {//失败
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                isPlayLiveData.setValue(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //暂停播放，释放资源
        if (audioPlayer != null) {
            audioPlayer.onRelease();
        }
    }
}
