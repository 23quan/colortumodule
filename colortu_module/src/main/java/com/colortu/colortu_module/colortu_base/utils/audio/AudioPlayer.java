package com.colortu.colortu_module.colortu_base.utils.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;

import java.io.IOException;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : AudioPlayer
 * @describe :语音播放工具类
 */
public class AudioPlayer {
    private MediaPlayer mediaPlayer;
    //是否暂停
    private boolean isPause;
    //是否音频焦点失去暂停播放
    private boolean isLoseFocus;
    //音频焦点管理
    private AudioManager audioManager;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public AudioPlayer() {
        //音频焦点
        audioManager = (AudioManager) BaseApplication.getContext().getSystemService(Context.AUDIO_SERVICE);
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

        mediaPlayer = new MediaPlayer();
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
                    if (isPlay()) {
                        isLoseFocus = true;
                        onPause();
                        onPlayerListener.playerpause();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    if (isLoseFocus) {
                        isLoseFocus = false;
                        onPlayerListener.recoverplayerstart();
                    }
                    break;
            }
        }
    };

    /**
     * 初始化播放
     */
    private void onInitPlay(String audiourl) {
        if (mediaPlayer != null) {
            if (isPlay()) {
                onStop();
            }
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(audiourl);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
                //异常
                onPlayerListener.playerfailure();
                return;
            }

            if (mediaPlayer == null) {
                return;
            }

            mediaPlayer.setLooping(false);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //暂停播放
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    onPlayerListener.playerfinish();
                }
            });

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //播放
                    mediaPlayer.start();
                    onPlayerListener.playerstart();
                }
            });
        }
    }

    /**
     * 播放
     */
    public void onPlay(String audiourl) {
        if (mediaPlayer != null) {
            if (isPause) {
                isPause = false;
                mediaPlayer.start();
            } else {
                isPause = false;
                onInitPlay(audiourl);
            }
            onPlayerListener.playerstart();
        }
    }

    /**
     * 暂停播放
     */
    public void onPause() {
        if (mediaPlayer != null) {
            isPause = true;
            mediaPlayer.pause();
            onPlayerListener.playerpause();
        }
    }

    /**
     * 停止播放
     */
    public void onStop() {
        if (mediaPlayer != null) {
            isPause = false;
            mediaPlayer.stop();
            mediaPlayer.reset();
            onPlayerListener.playerstop();
        }
    }

    /**
     * 释放播放资源
     */
    public void onRelease() {
        if (mediaPlayer != null) {
            isPause = false;
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            onPlayerListener.playerfinish();
        }
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlay() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        } else {
            return false;
        }
    }

    public void setOnPlayerListener(OnPlayerListener onPlayerListener) {
        this.onPlayerListener = onPlayerListener;
    }

    private OnPlayerListener onPlayerListener;

    public interface OnPlayerListener {
        void playerstart();//播放

        void recoverplayerstart();//恢复播放

        void playerpause();//暂停

        void playerstop();//停止

        void playerfinish();//结束

        void playerfailure();//失败
    }
}
