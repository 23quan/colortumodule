package com.colortu.colortu_module.colortu_base.utils.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

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

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public AudioPlayer() {
        AudioManager audioManager = (AudioManager) BaseApplication.getContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        mediaPlayer = new MediaPlayer();
    }

    /**
     * 音频焦点监听
     */
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                //失去焦点
                case AudioManager.AUDIOFOCUS_LOSS:
                    if (isPlay()) {
                        stop();
                        onPlayerListener.playerstop();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
            }
        }
    };

    /**
     * 播放
     */
    public void play(String audiourl) {
        if (mediaPlayer != null) {
            if (isPlay()) {
                stop();
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
     * 暂停
     */
    public void stop() {
        if (mediaPlayer != null) {
            onPlayerListener.playerstop();
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mediaPlayer != null) {
            onPlayerListener.playerfinish();
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
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
        //播放
        void playerstart();

        //暂停
        void playerstop();

        //结束
        void playerfinish();

        //失败
        void playerfailure();
    }
}
