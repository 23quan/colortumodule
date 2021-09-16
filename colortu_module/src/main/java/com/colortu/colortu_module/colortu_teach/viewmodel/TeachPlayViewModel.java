package com.colortu.colortu_module.colortu_teach.viewmodel;

import android.os.Bundle;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.receiver.BlueToothReceiver;
import com.colortu.colortu_module.colortu_base.core.receiver.VolumeReceiver;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioMngHelper;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioPlayer;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationClickReceiver;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationUtil;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachPlayViewModel
 * @describe :听力播放界面ViewModel
 */
public class TeachPlayViewModel extends BaseActivityViewModel<BaseRequest> implements NotificationClickReceiver.OnNotificationListener,
        BlueToothReceiver.OnBluetoothListener, VolumeReceiver.OnVolumeListener, AudioFocusUtils.OnAudioFocusListener {
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
    //音量图标
    public ObservableField<Integer> volumeicon = new ObservableField<>();

    //调节音量
    private int volumeLevel;
    //媒体音量管理
    private AudioMngHelper audioMngHelper;
    //语音播放工具类
    public AudioPlayer audioPlayer;
    //是否音频焦点失去暂停播放
    private boolean isLoseFocus;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        audioPlayer = new AudioPlayer();
        audioMngHelper = new AudioMngHelper(BaseApplication.getContext());
        NotificationClickReceiver.setOnNotificationListener(this);
        AudioFocusUtils.setOnAudioFocusListener(this);

        BlueToothReceiver.setOnBluetoothListener(this);
        isPlayLiveData.setValue(false);

        initVolumeIcon();
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
                //发送通知栏消息
                //NotificationUtil.createNotification(classname.get(), false);

                isPlayLiveData.setValue(true);
            }

            @Override
            public void playerpause() {//暂停
                unPlayer(false);
            }

            @Override
            public void playerstop() {//停止
                unPlayer(true);
            }

            @Override
            public void playerfinish() {//完成
                unPlayer(true);
            }

            @Override
            public void playerfailure() {//失败
                unPlayer(true);
            }
        });
    }

    private void unPlayer(boolean isAbandon) {
        if (isAbandon) {
            //解绑音频焦点
            AudioFocusUtils.abandonAudioFocus();
        }
        //启动息屏app销毁
        SuicideUtils.onStartKill();
        //发送通知栏消息
        //NotificationUtil.createNotification(classname.get(), false);

        isPlayLiveData.setValue(false);
    }

    /**
     * 初始化音量图标
     */
    public void initVolumeIcon() {
        //设置媒体音量图标
        try {
            int volume = audioMngHelper.get100CurrentVolume();
            if (volume <= 33) {
                volumeLevel = 0;
                volumeicon.set(R.mipmap.icon_listen_volume1);
            } else if (volume > 33 && volume <= 66) {
                volumeLevel = 1;
                volumeicon.set(R.mipmap.icon_listen_volume2);
            } else if (volume > 66) {
                volumeLevel = 2;
                volumeicon.set(R.mipmap.icon_listen_volume3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调节音量
     */
    public void onAdjustVolume() {
        volumeLevel = (volumeLevel + 1) % 3;
        try {
            switch (volumeLevel) {
                case 0:
                    audioMngHelper.setVoice100(33);
                    volumeicon.set(R.mipmap.icon_listen_volume1);
                    break;
                case 1:
                    audioMngHelper.setVoice100(66);
                    volumeicon.set(R.mipmap.icon_listen_volume2);
                    break;
                case 2:
                    audioMngHelper.setVoice100(100);
                    volumeicon.set(R.mipmap.icon_listen_volume3);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放听写
     */
    public void onPlay() {
        if (EmptyUtils.stringIsEmpty(audiourl.get())) {
            if (audioPlayer != null) {
                if (isPlayLiveData.getValue()) {
                    audioPlayer.onPause();
                } else {
                    isLoseFocus = false;
                    //获取音频焦点
                    AudioFocusUtils.initAudioFocus(BaseApplication.getContext());
                    audioPlayer.onPlay(BaseConstant.HomeWorkImgUrl + audiourl.get());
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
            audioPlayer.onStop();
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
     * 失去焦点
     */
    @Override
    public void onLossAudioFocus() {
        if (audioPlayer != null) {
            if (audioPlayer.isPlay()) {
                isLoseFocus = true;
                audioPlayer.onPause();
            }
        }
    }

    /**
     * 获取焦点
     */
    @Override
    public void onGainAudioFocus() {
        if (isLoseFocus) {
            //取消息屏app销毁
            SuicideUtils.onCancelKill();
            //发送通知栏消息
            //NotificationUtil.createNotification(classname.get(), false);
            isLoseFocus = false;
            isPlayLiveData.setValue(true);
            onPlay();
        }
    }

    /**
     * 通知栏上一首
     */
    @Override
    public void onNotificationLast() {
        TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.not_last));
    }

    /**
     * 通知栏暂停播放
     */
    @Override
    public void onNotificationPlay() {
        onPlay();
    }

    /**
     * 通知栏下一首
     */
    @Override
    public void onNotificationNext() {
        TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.not_next));
    }

    /**
     * 蓝牙监听
     */
    @Override
    public void onBluetoothDisConnected() {
        onPlay();
    }

    /**
     * 媒体音量变化监听
     */
    @Override
    public void OnVolumeChange() {
        initVolumeIcon();
    }

    /**
     * 销毁资源
     */
    public void onDispose() {
        //销毁通知栏消息
        //NotificationUtil.cancelNotification();
        //解绑音频焦点
        AudioFocusUtils.abandonAudioFocus();

        //暂停播放，释放资源
        if (audioPlayer != null) {
            if (audioPlayer.isPlay()) {
                isPlayLiveData.setValue(false);
            }
            audioPlayer.onRelease();
        }
    }
}
