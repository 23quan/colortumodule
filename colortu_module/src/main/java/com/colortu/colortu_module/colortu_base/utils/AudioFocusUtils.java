package com.colortu.colortu_module.colortu_base.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;

/**
 * @author : Code23
 * @time : 2021/7/7
 * @module : AudioFocusUtils
 * @describe :音频焦点工具类
 */
public class AudioFocusUtils {
    //音频焦点管理
    private static AudioManager audioManager;

    /**
     * 初始化音频焦点
     */
    public static void initAudioFocus(Context context) {
        //音频焦点
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //AudioAttributes 配置(多媒体场景，申请的是音乐流)
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            // 初始化AudioFocusRequest
            AudioFocusRequest audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(audioAttributes)
                    //设置是否允许延迟获取焦点
                    .setAcceptsDelayedFocusGain(true)
                    //设置AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK会暂停，系统不会压低声音
                    .setWillPauseWhenDucked(true)
                    //设置焦点监听回调
                    .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                    .build();
            //申请焦点
            audioManager.requestAudioFocus(audioFocusRequest);
        } else {
            audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    /**
     * 注销音频焦点
     */
    public static void abandonAudioFocus() {
        if (audioManager != null) {
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
            audioManager = null;
        }
    }

    /**
     * 音频焦点监听
     */
    private static AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    if (onAudioFocusListener != null) {
                        onAudioFocusListener.onLossAudioFocus();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    if (onAudioFocusListener != null) {
                        onAudioFocusListener.onGainAudioFocus();
                    }
                    break;
            }
        }
    };

    private static OnAudioFocusListener onAudioFocusListener;

    public static void setOnAudioFocusListener(OnAudioFocusListener onAudioFocusListener) {
        AudioFocusUtils.onAudioFocusListener = onAudioFocusListener;
    }

    public interface OnAudioFocusListener {
        //失去焦点
        void onLossAudioFocus();

        //获取焦点
        void onGainAudioFocus();
    }
}
