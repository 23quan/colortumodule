package com.colortu.colortu_module.colortu_teach.viewmodel;

import android.os.Bundle;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioPlayer;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationUtil;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachPlayViewModel
 * @describe :听力播放界面ViewModel
 */
public class TeachPlayViewModel extends BaseActivityViewModel<BaseRequest> {
    //暂停播放监听
    public MutableLiveData<Boolean> isPlayLiveData = new MutableLiveData<>();

    //true 解锁 false 去解锁
    public ObservableField<Boolean> isVip = new ObservableField<>();
    //课名
    public ObservableField<String> classname = new ObservableField<>();
    //听写url
    public ObservableField<String> audiourl = new ObservableField<>();
    //课id
    public ObservableField<Integer> examid = new ObservableField<>();
    //语音播放工具类
    private AudioPlayer audioPlayer;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        audioPlayer = new AudioPlayer();

        isPlayLiveData.setValue(false);

        audioPlayer.setOnPlayerListener(new AudioPlayer.OnPlayerListener() {
            @Override
            public void playerstart() {//播放
                isPlayLiveData.setValue(true);
                //取消息屏app销毁
                SuicideUtils.onCancelKill();

                if (ChannelUtil.isHuaWei()) {
                    //发送通知栏消息
                    BaseApplication.onStartNotification();
                }
            }

            @Override
            public void playerstop() {//暂停
                isPlayLiveData.setValue(false);
                //启动息屏app销毁
                SuicideUtils.onStartKill();
            }

            @Override
            public void playerfinish() {//播放完成
                isPlayLiveData.setValue(false);
                //启动息屏app销毁
                SuicideUtils.onStartKill();
            }

            @Override
            public void playerfailure() {//播放失败
                isPlayLiveData.setValue(false);
            }
        });
    }

    /**
     * 播放听写
     */
    public void onPlay() {
        if (EmptyUtils.stringIsEmpty(audiourl.get())) {
            if (audioPlayer != null) {
                if (isPlayLiveData.getValue()) {
                    onStopPlayer();
                } else {
                    onPlayPlayer(audiourl.get());
                }
            }
        } else {
            TipToast.tipToastShort(BaseApplication.getContext().getString(R.string.update_content));
        }
    }

    /**
     * 跳转答案界面
     */
    public void onJumpAnswer() {
        if (isPlayLiveData.getValue()) {
            isPlayLiveData.setValue(false);
            onStopPlayer();
        }

        if (isVip.get()) {
            //答案界面
            Bundle bundle = new Bundle();
            bundle.putString("classname", classname.get());
            bundle.putInt("examid", examid.get());
            BaseUIKit.startActivity(UIKitName.TEACH_PLAY, UIKitName.TEACH_ANSWER,
                    BaseConstant.TEACH_ANSWER, BaseUIKit.OTHER, bundle);
        } else {
            //扫描解锁
            Bundle bundle = new Bundle();
            bundle.putInt("status", 1);
            BaseUIKit.startActivity(UIKitName.TEACH_PLAY, UIKitName.QRCODE_PAY,
                    BaseConstant.QRCODE_PAY, BaseUIKit.NONE, bundle);
        }
    }

    /**
     * 播放
     */
    public void onPlayPlayer(String audiourl) {
        audioPlayer.play(audiourl);
    }

    /**
     * 暂停
     */
    public void onStopPlayer() {
        audioPlayer.stop();
    }

    /**
     * 释放
     */
    public void onReleasePlayer() {
        audioPlayer.release();
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

        //暂停播放
        if (audioPlayer != null) {
            if (audioPlayer.isPlay()) {
                isPlayLiveData.setValue(false);
            }
            onReleasePlayer();
        }
    }
}
