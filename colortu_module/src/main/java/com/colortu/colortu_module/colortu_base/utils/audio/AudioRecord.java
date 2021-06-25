package com.colortu.colortu_module.colortu_base.utils.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.text.format.DateFormat;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author : Code23
 * @time : 2020/12/1
 * @module : AudioRecord
 * @describe :录音
 */
public class AudioRecord {
    //文件路径
    private String filepath = null;
    //文件名
    private String filename = null;
    //播放
    private MediaPlayer mediaPlayer = null;
    //录音
    private MediaRecorder mediaRecorder = null;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public AudioRecord() {
        AudioManager audioManager = (AudioManager) BaseApplication.getContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
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
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            onRecorderListener.onStopPlayer();
                        }
                    }
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
            }
        }
    };

    /**
     * @param isplayer true 播放；false 暂停
     */
    public void OnPlayer(boolean isplayer) {
        if (isplayer) {
            onStartPlayer();
        } else {
            onStopPlayer();
        }
    }

    /**
     * @param isrecorder true 录音 ；false 结束
     */
    public void OnRecorder(boolean isrecorder) {
        if (isrecorder) {
            onStartRecorder();
        } else {
            onStopRecorder();
        }
    }

    /**
     * 播放录音
     */
    private void onStartPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            onRecorderListener.onStartPlayer();
            mediaPlayer.setDataSource(filepath + "/" + filename);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    onRecorderListener.onFinishPlayer();
                }
            });
        } catch (IOException e) {
            onRecorderListener.onFailurePlayer();
            onStopPlayer();
        }
    }

    /**
     * 暂停播放
     */
    private void onStopPlayer() {
        if (mediaPlayer != null) {
            onRecorderListener.onStopPlayer();
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 开始录音
     */
    private void onStartRecorder() {
        createFileName();
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 声源(MIC表示来自于麦克风)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//设置输出文件格式
        mediaRecorder.setOutputFile(filepath + "/" + filename);//设置输出文件名
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//设置音频编码器
        try {
            mediaRecorder.prepare();//完成初始化
        } catch (IOException e) {
            e.printStackTrace();
            //准备失败
            onRecorderListener.onFailureRecorder();
            return;
        }
        mediaRecorder.start();//启动
        onRecorderListener.onStartRecorder();
    }

    /**
     * 结束录音
     */
    private void onStopRecorder() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
            } catch (RuntimeException e) {
                mediaRecorder.reset();
                mediaRecorder.release();
                mediaRecorder = null;
            }
            File file = new File(filepath + filename);
            onRecorderListener.onStopRecorder(file);
        }
    }

    /**
     * 取消录音
     */
    public void onCancelRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;

            File file = new File(filepath + filename);
            if (file.exists())
                file.delete();

            onRecorderListener.onCanceRecorderl();
        }
    }

    /**
     * 创建文件夹及文件名
     */
    public void createFileName() {
        filename = String.valueOf(DateFormat.format("yyyyMMddHHmm", Calendar.getInstance(Locale.CHINA))) + randomnum() + ".m4a";
        filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Homework/InputAudio/";
        File file = new File(filepath);
        if (!file.exists()) { //文件夹不存在
            // 创建文件夹
            file.mkdirs();
        }
    }

    /**
     * 删除本地文件
     */
    public static void deleteLocal(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                //如果为文件，直接删除
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File file1 : files) {
                        //如果为文件夹，递归调用
                        deleteLocal(file1);
                    }
                }
            }
            file.delete();
        }
    }

    /**
     * 随机生成六位数
     *
     * @return
     */
    private int randomnum() {
        int num = (int) ((Math.random() * 9 + 1) * 100000);
        return num;
    }

    public void setOnRecorderListener(OnRecorderListener onRecorderListener) {
        this.onRecorderListener = onRecorderListener;
    }

    private OnRecorderListener onRecorderListener;

    public interface OnRecorderListener {
        //开始播放
        void onStartPlayer();

        //播放完成
        void onFinishPlayer();

        //播放暂停
        void onStopPlayer();

        //播放失败
        void onFailurePlayer();

        //开始录音
        void onStartRecorder();

        //取消录音
        void onCanceRecorderl();

        //暂停录音
        void onStopRecorder(File file);

        //录音失败
        void onFailureRecorder();
    }
}
