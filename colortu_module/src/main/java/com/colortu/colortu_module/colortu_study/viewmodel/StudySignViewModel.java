package com.colortu.colortu_module.colortu_study.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioPlayer;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationUtil;
import com.colortu.colortu_module.colortu_base.bean.StudySignBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/4/12
 * @module : StudySignViewModel
 * @describe :个性签名界面ViewModel
 */
public class StudySignViewModel extends BaseActivityViewModel<BaseRequest> {
    //获取个性状态列表接口
    private Call<StudySignBean> studySignBeanCall;

    //个性签名列表数据
    public MutableLiveData<List<StudySignBean.DataBean.RecordsBean>> studysignLiveData = new MutableLiveData<>();

    //暂停播放icon
    public ObservableField<Integer> inputplayimg = new ObservableField<>();
    //判断是否播放完成
    public MutableLiveData<Boolean> isPlay = new MutableLiveData<>();
    //录入语音url
    public ObservableField<String> audiourl = new ObservableField<>();
    //确定按钮背景颜色
    public MutableLiveData<Boolean> sureBgColor = new MutableLiveData<>();

    //语音播放工具类
    private AudioPlayer audioPlayer;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        audioPlayer = new AudioPlayer();

        isPlay.setValue(true);
        getStudySign();

        audioPlayer.setOnPlayerListener(new AudioPlayer.OnPlayerListener() {
            @Override
            public void playerstart() {//播放
                //取消息屏app销毁
                SuicideUtils.onCancelKill();

                if (ChannelUtil.isHuaWei()) {
                    //发送通知栏消息
                    BaseApplication.onStartNotification();
                }
            }

            @Override
            public void playerstop() {//暂停
                isPlay.setValue(true);
                //启动息屏app销毁
                SuicideUtils.onStartKill();
            }

            @Override
            public void playerfinish() {//播放完成
                isPlay.setValue(true);
                //启动息屏app销毁
                SuicideUtils.onStartKill();
            }

            @Override
            public void playerfailure() {//播放失败
                isPlay.setValue(true);
            }
        });
    }

    /**
     * 控制播放与暂停
     */
    public void onPlayAudio() {
        if (isPlay.getValue()) {
            inputplayimg.set(R.mipmap.icon_play_start);
            onPlayPlayer(BaseConstant.HomeWorkAudioUrl + audiourl.get());
        } else {
            inputplayimg.set(R.mipmap.icon_play_stop);
            onStopPlayer();
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

    /**
     * 获取个性状态列表接口请求
     */
    public void getStudySign() {
        studySignBeanCall = iRequest.getStudySign(BaseApplication.getHeaders());
        studySignBeanCall.enqueue(new Callback<StudySignBean>() {
            @Override
            public void onResponse(Call<StudySignBean> call, Response<StudySignBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.listIsEmpty(response.body().getData().getRecords())) {
                        studysignLiveData.setValue(response.body().getData().getRecords());
                    }
                }
            }

            @Override
            public void onFailure(Call<StudySignBean> call, Throwable t) {//请求失败

            }
        });
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

        //暂停播放，释放资源
        onReleasePlayer();
    }
}
