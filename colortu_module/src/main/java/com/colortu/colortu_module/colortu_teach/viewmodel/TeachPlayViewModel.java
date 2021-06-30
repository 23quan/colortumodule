package com.colortu.colortu_module.colortu_teach.viewmodel;

import android.os.Bundle;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.receiver.BlueToothReceiver;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioPlayer;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationClickReceiver;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationUtil;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachPlayViewModel
 * @describe :听力播放界面ViewModel
 */
public class TeachPlayViewModel extends BaseActivityViewModel<BaseRequest> implements NotificationClickReceiver.OnNotificationListener ,
        BlueToothReceiver.OnBluetoothListener{
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
    public AudioPlayer audioPlayer;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        audioPlayer = new AudioPlayer();
        NotificationClickReceiver.setOnNotificationListener(this);

        BlueToothReceiver.setOnBluetoothListener(this);
        isPlayLiveData.setValue(false);
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
                NotificationUtil.createNotification(false);

                isPlayLiveData.setValue(true);
            }

            @Override
            public void playerpause() {//暂停
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                //发送通知栏消息
                NotificationUtil.createNotification(false);

                isPlayLiveData.setValue(false);
            }

            @Override
            public void playerstop() {//停止
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                //发送通知栏消息
                NotificationUtil.createNotification(false);

                isPlayLiveData.setValue(false);
            }

            @Override
            public void playerfinish() {//完成
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                //发送通知栏消息
                NotificationUtil.createNotification(false);

                isPlayLiveData.setValue(false);
            }

            @Override
            public void playerfailure() {//失败
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                //发送通知栏消息
                NotificationUtil.createNotification(false);

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
                    audioPlayer.onPause();
                } else {
                    audioPlayer.onPlay(audiourl.get());
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

    @Override
    public void onNotificationLast() {

    }

    @Override
    public void onNotificationPlay() {
        onPlay();
    }

    @Override
    public void onNotificationNext() {

    }

    /**
     * 蓝牙监听
     */
    @Override
    public void onBluetoothDisConnected() {
        onPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁通知栏消息
        NotificationUtil.cancelNotification();

        //暂停播放，释放资源
        if (audioPlayer != null) {
            if (audioPlayer.isPlay()) {
                isPlayLiveData.setValue(false);
            }
            audioPlayer.onRelease();
        }
    }
}
