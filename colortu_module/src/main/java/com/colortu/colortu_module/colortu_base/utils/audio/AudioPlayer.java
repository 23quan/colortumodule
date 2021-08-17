package com.colortu.colortu_module.colortu_base.utils.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : AudioPlayer
 * @describe :语音播放工具类
 */
public class AudioPlayer {
    //音频播放器
    private MediaPlayer mediaPlayer;
    //是否暂停
    private boolean isPause;

    public AudioPlayer() {
        mediaPlayer = new MediaPlayer();
    }

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
     * 初始化播放
     */
    private void onInitPlay(Context context, Uri uri) {
        if (mediaPlayer != null) {
            if (isPlay()) {
                onStop();
            }
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(context, uri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
                //异常
                onPlayerListener.playerfailure();
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
     * 播放
     */
    public void onPlay(Context context, Uri uri) {
        if (mediaPlayer != null) {
            if (isPause) {
                isPause = false;
                mediaPlayer.start();
            } else {
                isPause = false;
                onInitPlay(context, uri);
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

        void playerpause();//暂停

        void playerstop();//停止

        void playerfinish();//结束

        void playerfailure();//失败
    }
}
