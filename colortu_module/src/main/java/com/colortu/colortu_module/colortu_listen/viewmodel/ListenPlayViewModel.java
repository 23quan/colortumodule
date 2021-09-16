package com.colortu.colortu_module.colortu_listen.viewmodel;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;

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
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioMngHelper;
import com.colortu.colortu_module.colortu_base.utils.download.DownloadAudio;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationClickReceiver;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationUtil;
import com.colortu.colortu_module.colortu_base.utils.string.StringUtil;
import com.colortu.colortu_module.colortu_base.bean.ListenClassBean;
import com.colortu.colortu_module.colortu_base.bean.ListenFinishBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenPlayViewModel
 * @describe :听力播放界面ViewModel
 */
public class ListenPlayViewModel extends BaseActivityViewModel<BaseRequest> implements DownloadAudio.DownloadAudioListener, NotificationClickReceiver.OnNotificationListener,
        BlueToothReceiver.OnBluetoothListener, VolumeReceiver.OnVolumeListener, AudioFocusUtils.OnAudioFocusListener {
    //听写完成接口
    private Call<ListenFinishBean> listenFinishBeanCall;

    //课名
    public ObservableField<String> classname = new ObservableField<>();
    //科目id
    public ObservableField<Integer> subjectid = new ObservableField<>();
    //版本id
    public ObservableField<Integer> versionid = new ObservableField<>();
    //单词音频列表数据
    public ObservableField<List<ListenClassBean.DataBean.PoetryVOSBean.WordsBean>> listenClassBean = new ObservableField<>();

    //音量图标
    public ObservableField<Integer> volumeicon = new ObservableField<>();
    //速度text
    public ObservableField<String> speedtext = new ObservableField<>();
    //播放下标text
    public ObservableField<String> curItemText = new ObservableField<>();
    //播放按钮图标
    public ObservableField<Integer> playicon = new ObservableField<>();
    //播放进度max
    public ObservableField<Integer> progressmax = new ObservableField<>();
    //播放进度
    public ObservableField<Integer> progress = new ObservableField<>();
    //播放倒计时
    public ObservableField<String> curtime = new ObservableField<>();

    private Handler handler;
    //年级id
    private int gradeid;
    //课id
    private int lessonid;
    //是否可以点击
    private boolean isClick = false;
    //是否音频焦点失去暂停播放
    private boolean isLoseFocus;
    //是否播放
    public boolean playing = false;
    //播放结束
    private boolean playingFinish;
    //调节音量
    private int volumeLevel;
    //播放下标
    private int curItem;
    //调节速度type
    private int speedtype;
    //播放速度
    private int speedtime;
    //文件路径
    private String savePath;
    //下载的音频路径
    private String[] audiourllist;
    //MD5音频路径
    private List<String> audionamelist = new ArrayList<>();
    //是否开始播放
    public MutableLiveData<Boolean> isPlay = new MutableLiveData<>();
    //是否显示答案
    public MutableLiveData<Boolean> isShowAnswer = new MutableLiveData<>();
    //媒体播放器
    private MediaPlayer mediaPlayer;
    //倒计时工具
    private CountDownTimer countDownTimer;
    //媒体音量管理
    private AudioMngHelper audioMngHelper;
    //音频下载工具类
    private DownloadAudio downloadAudio;

    public void initData() {
        //停止播放提示语音
        BaseApplication.onStopTipVoice();
        //实例化
        handler = new Handler();
        audioMngHelper = new AudioMngHelper(BaseApplication.getContext());
        //NotificationClickReceiver.setOnNotificationListener(this);
        //音频焦点监听
        AudioFocusUtils.setOnAudioFocusListener(this);
        AudioFocusUtils.initAudioFocus(BaseApplication.getContext());

        gradeid = GetBeanDate.getChooseGrade();
        //课目名字
        String subjectname = "";
        if (subjectid.get() == 1) {
            subjectname = "Chinese";
        } else {
            subjectname = "English";
        }

        lessonid = listenClassBean.get().get(0).getLessonId();
        //文件路径
        savePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/Colortu/" + subjectname + "/Version_" + versionid.get() + "/Grade_" + gradeid + "/Lesson_" + lessonid;
        //语音路径转md5列表
        for (int i = 0; i < listenClassBean.get().size(); i++) {
            audionamelist.add(StringUtil.makeMd5(BaseConstant.ListenAudioUrl + listenClassBean.get().get(i).getWordAudioUrl()));
        }
        audiourllist = new String[listenClassBean.get().size()];
        //下载语音
        downloadAudio = new DownloadAudio(this, savePath, audionamelist);
        downloadAudio.isDownloadAudio(BaseConstant.ListenAudioUrl + listenClassBean.get().get(0).getWordAudioUrl(), 0);

        //设置初始数据
        isPlay.setValue(false);
        isShowAnswer.setValue(false);
        curItemText.set((curItem + 1) + "/" + listenClassBean.get().size());
        progress.set(0);
        speedtype = 2;
        speedtime = BaseConstant.LISTEN_SPEED_NORMAL;
        speedtext.set(BaseApplication.getContext().getResources().getString(R.string.normal));
        initVolumeIcon();

        //蓝牙监听
        BlueToothReceiver.setOnBluetoothListener(this);
        //提示语音
        onStartTipVoice(R.raw.music_play_start);
        //单词播放
        handler.postDelayed(initPlay, 8500);
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
     * 开始播放提示语音
     */
    public void onStartTipVoice(int audio) {
        mediaPlayer = new MediaPlayer().create(BaseApplication.getContext(), audio);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                onStopPlayer();
            }
        });
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
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
     * 调节播放速度
     */
    public void onAdjustSpeed() {
        if (isClick) {
            if (speedtype == 1) {
                speedtype = 2;
                speedtime = BaseConstant.LISTEN_SPEED_NORMAL;
                speedtext.set(BaseApplication.getContext().getResources().getString(R.string.normal));
            } else if (speedtype == 2) {
                speedtype = 3;
                speedtime = BaseConstant.LISTEN_SPEED_QUICK;
                speedtext.set(BaseApplication.getContext().getResources().getString(R.string.quick));
            } else if (speedtype == 3) {
                speedtype = 4;
                speedtime = BaseConstant.PLAYLISTEN_SPEED_FAST;
                speedtext.set(BaseApplication.getContext().getResources().getString(R.string.fast));
            } else {
                speedtype = 1;
                speedtime = BaseConstant.LISTEN_SPEED_SLOW;
                speedtext.set(BaseApplication.getContext().getResources().getString(R.string.slow));
            }
            onStopPlayer();
            progressmax.set(speedtime);
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            onPlaying();
        }
    }

    /**
     * 跳转答案界面
     */
    public void onJumpAnswer() {
        if (isClick) {
            if (mediaPlayer != null) {
                onReleasePlayer();
            }
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            Bundle bundle = new Bundle();
            bundle.putString("savePath", savePath);
            bundle.putSerializable("wordsbean", (Serializable) listenClassBean.get());
            BaseUIKit.startActivity(UIKitName.LISTEN_PLAY, UIKitName.LISTEN_ANSWER,
                    BaseConstant.LISTEN_ANSWER, BaseUIKit.OTHER, bundle);
        }
    }

    /**
     * 上一首
     */
    public void onLast() {
        if (isClick) {
            if (curItem == 0) {
                TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.last_topic));
                return;
            }
            //隐藏答案按钮
            if (isShowAnswer.getValue()) {
                isShowAnswer.setValue(false);
            }
            onStopPlayer();
            //获取音频焦点
            if (isLoseFocus || playingFinish) {
                isLoseFocus = false;
                playingFinish = false;
                AudioFocusUtils.initAudioFocus(BaseApplication.getContext());
            }
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            curItem--;
            curItemText.set((curItem + 1) + "/" + listenClassBean.get().size());
            onPlaying();
        }
    }

    /**
     * 下一首
     */
    public void onNext() {
        if (isClick) {
            if (curItem == listenClassBean.get().size() - 1) {
                TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.next_topic));
                return;
            }
            onStopPlayer();
            //获取音频焦点
            if (isLoseFocus || playingFinish) {
                isLoseFocus = false;
                playingFinish = false;
                AudioFocusUtils.initAudioFocus(BaseApplication.getContext());
            }
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            curItem++;
            curItemText.set((curItem + 1) + "/" + listenClassBean.get().size());
            onPlaying();
        }
    }

    /**
     * 暂停/播放
     */
    public void onStartAudio() {
        if (isClick) {
            if (playing) {
                onStopWords(true);
            } else {
                onPlayWords();
            }
        }
    }

    /**
     * 初始播放数据
     */
    Runnable initPlay = new Runnable() {
        @Override
        public void run() {
            if (listenClassBean.get().size() != 0) {
                isClick = true;
                isPlay.setValue(true);
                onPlaying();
                handler.removeCallbacks(initPlay);
            }
        }
    };

    /**
     * 播放听写单词
     */
    private void onPlayWords() {
        onStopPlayer();
        isLoseFocus = false;
        //获取音频焦点
        AudioFocusUtils.initAudioFocus(BaseApplication.getContext());
        //隐藏答案按钮
        if (isShowAnswer.getValue()) {
            isShowAnswer.setValue(false);
        }
        playing = true;
        playicon.set(R.mipmap.icon_listen_play);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        onPlaying();
    }

    private void onPlaying() {
        progressmax.set(speedtime - 1000);
        curtime.set(String.valueOf((speedtime / 1000) - 1));
        handler.removeCallbacks(playingRunnable);
        handler.post(playingRunnable);
        setAudioProgress();
    }

    /**
     * 暂停播放单词
     */
    private void onStopWords(boolean isAbandon) {
        onStopPlayer();
        //解绑音频焦点
        if (isAbandon) {
            AudioFocusUtils.abandonAudioFocus();
        }
        //启动息屏app销毁
        SuicideUtils.onStartKill();
        //发送通知栏消息
        //NotificationUtil.createNotification(classname.get(), true);
        playicon.set(R.mipmap.icon_listen_stop);
        playing = false;
        curtime.set(String.valueOf((speedtime / 1000) - 1));
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        progress.set(0);
    }

    /**
     * 播放听写语音Runnable
     */
    private Runnable playingRunnable = new Runnable() {
        @Override
        public void run() {
            String path = "";
            if (audiourllist != null) {
                if (audiourllist[curItem] != null) {
                    if (!audiourllist[curItem].equals("failure") && !audiourllist[curItem].equals("")) {
                        path = audiourllist[curItem];
                    } else {
                        path = BaseConstant.ListenAudioUrl + listenClassBean.get().get(curItem).getWordAudioUrl();
                    }
                } else {
                    path = BaseConstant.ListenAudioUrl + listenClassBean.get().get(curItem).getWordAudioUrl();
                }
            } else {
                path = BaseConstant.ListenAudioUrl + listenClassBean.get().get(curItem).getWordAudioUrl();
            }
            Uri uri = Uri.parse(path);
            onStartPlayer(uri);
        }
    };

    /**
     * 播放听写语音
     *
     * @param uri
     */
    private void onStartPlayer(Uri uri) {
        //取消息屏app销毁
        SuicideUtils.onCancelKill();
        //发送通知栏消息
        //NotificationUtil.createNotification(classname.get(), true);

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(BaseApplication.getContext(), uri);
            mediaPlayer.setLooping(false);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    /**
     * 暂停开始听写语音
     */
    private void onStopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    /**
     * 释放开始听写语音
     */
    private void onReleasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 设置播放进度条
     */
    private void setAudioProgress() {
        countDownTimer = new CountDownTimer(speedtime, 1000) {
            public void onTick(long millisUntilFinished) {
                curtime.set(String.valueOf(millisUntilFinished / 1000));
                int timefinish = Integer.parseInt(String.valueOf((millisUntilFinished / 1000) * 1000));
                progress.set(Integer.valueOf(String.valueOf(speedtime - timefinish)));
            }

            public void onFinish() {
                if (curItem == listenClassBean.get().size() - 1) {
                    progress.set(0);
                    curtime.set(String.valueOf((speedtime / 1000) - 1));
                    playicon.set(R.mipmap.icon_listen_stop);

                    //播放提示语音
                    onStartTipVoice(R.raw.music_play_end);
                    //显示答案按钮
                    handler.postDelayed(showAnswerRunnable, 6000);

                    //听写完成上报数据
                    if (lessonid != 0) {
                        getListenFinish(lessonid, GetBeanDate.getUserUuid());
                    }

                    //启动息屏app销毁
                    SuicideUtils.onStartKill();
                    //发送通知栏消息
                    //NotificationUtil.createNotification(classname.get(), true);
                } else {
                    curItem++;
                    curItemText.set((curItem + 1) + "/" + listenClassBean.get().size());
                    onPlaying();
                }
            }
        }.start();

        playing = true;
        playicon.set(R.mipmap.icon_listen_play);
    }

    /**
     * 显示答案按钮
     */
    Runnable showAnswerRunnable = new Runnable() {
        @Override
        public void run() {
            playingFinish = true;
            //解绑音频焦点
            AudioFocusUtils.abandonAudioFocus();
            isShowAnswer.setValue(true);
        }
    };

    /**
     * 音频下载成功
     *
     * @param audiourl 音频路径
     * @param i        下标
     */
    @Override
    public void DownloadSuccess(String audiourl, int i) {
        if (i < listenClassBean.get().size()) {
            audiourllist[i] = audiourl;
            i++;
            if (i < listenClassBean.get().size()) {
                downloadAudio.isDownloadAudio(BaseConstant.ListenAudioUrl + listenClassBean.get().get(i).getWordAudioUrl(), i);
            }
        }
    }

    /**
     * 音频下载失败
     *
     * @param audiourl 音频路径
     * @param i        下标
     */
    @Override
    public void DownloadFailure(String audiourl, int i) {
        if (i < listenClassBean.get().size()) {
            audiourllist[i] = audiourl;
            i++;
            if (i < listenClassBean.get().size()) {
                downloadAudio.isDownloadAudio(BaseConstant.ListenAudioUrl + listenClassBean.get().get(i).getWordAudioUrl(), i);
            }
        }
    }

    /**
     * 失去焦点
     */
    @Override
    public void onLossAudioFocus() {
        isClick = true;
        if (BaseApplication.isPlaying()) {
            BaseApplication.onStopTipVoice();
        }
        if (!isPlay.getValue()) {
            isPlay.setValue(true);
            handler.removeCallbacks(initPlay);
            onStopWords(false);
        }
        if (playing) {
            isLoseFocus = true;
            onStopWords(false);
        }
    }

    /**
     * 获取焦点
     */
    @Override
    public void onGainAudioFocus() {
        if (isLoseFocus) {
            onStartAudio();
        }
    }

    /**
     * 通知栏上一首
     */
    @Override
    public void onNotificationLast() {
        //onLast();
    }

    /**
     * 通知栏播放暂停
     */
    @Override
    public void onNotificationPlay() {
        //onStartAudio();
    }

    /**
     * 通知栏下一首
     */
    @Override
    public void onNotificationNext() {
        //onNext();
    }

    /**
     * 蓝牙监听
     */
    @Override
    public void onBluetoothDisConnected() {
        if (playing) {
            onStopWords(false);
        }
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
        //取消倒计时
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        //解绑音频焦点
        AudioFocusUtils.abandonAudioFocus();
        //释放开始听写语音
        onReleasePlayer();
        //移除runnable
        handler.removeCallbacks(initPlay);
        handler.removeCallbacks(showAnswerRunnable);
        handler.removeCallbacks(playingRunnable);
    }

    /**
     * 听写完成接口请求
     *
     * @param lessonId 课id
     * @param uuid     用户uuid
     */
    public void getListenFinish(int lessonId, String uuid) {
        listenFinishBeanCall = iRequest.getListenFinish(lessonId, uuid);
        listenFinishBeanCall.enqueue(new Callback<ListenFinishBean>() {
            @Override
            public void onResponse(Call<ListenFinishBean> call, Response<ListenFinishBean> response) {//请求成功

            }

            @Override
            public void onFailure(Call<ListenFinishBean> call, Throwable t) {//请求失败

            }
        });
    }
}
