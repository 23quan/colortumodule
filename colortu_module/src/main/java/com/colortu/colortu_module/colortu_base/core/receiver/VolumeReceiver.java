package com.colortu.colortu_module.colortu_base.core.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @author : Code23
 * @time : 2021/9/16
 * @module : VolumeReceiver
 * @describe :媒体音量广播监听
 */
public class VolumeReceiver extends BroadcastReceiver {
    //媒体音量action
    private static String VOLUMEACTION = "android.media.VOLUME_CHANGED_ACTION";
    //媒体音量广播
    private static VolumeReceiver volumeReceiver;

    @Override
    public void onReceive(Context context, Intent intent) {
        //音量发生变化
        if (intent.getAction().equals(VOLUMEACTION)) {
            if (onVolumeListener != null) {
                onVolumeListener.OnVolumeChange();
            }
        }
    }

    /**
     * 注册媒体音量广播
     */
    public static void onRegisterVolume(Context context) {
        IntentFilter intentFilter = new IntentFilter(VOLUMEACTION);
        volumeReceiver = new VolumeReceiver();
        context.registerReceiver(volumeReceiver, intentFilter);
    }

    /**
     * 注销媒体音量广播
     */
    public static void unRegisterVolume(Context context) {
        if (volumeReceiver != null) {
            try {
                context.unregisterReceiver(volumeReceiver);
                volumeReceiver = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private OnVolumeListener onVolumeListener;

    public void setOnVolumeListener(OnVolumeListener onVolumeListener) {
        this.onVolumeListener = onVolumeListener;
    }

    public interface OnVolumeListener {
        void OnVolumeChange();
    }
}
