package com.colortu.colortu_module.colortu_record.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioPlayer;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationUtil;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectDetailBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordHistorySubjectDetailViewModel
 * @describe :历史科目详情界面ViewModel
 */
public class RecordHistorySubjectDetailViewModel extends BaseActivityViewModel<BaseRequest> {
    //作业列表详情请求
    private Call<RecordSubjectDetailBean> recordSubjectDetailBeanCall;

    //历史科目详情列表数据
    public MutableLiveData<List<RecordSubjectDetailBean.DataBean.RecordsBean>> recordSubjectDetailBeanLiveData = new MutableLiveData<>();

    //判断是否播放完成
    public MutableLiveData<Boolean> isPlay = new MutableLiveData<>();
    //科目名
    public ObservableField<String> subjectname = new ObservableField<>();
    //科目id
    public ObservableField<Integer> subjectId = new ObservableField<>();
    //日期
    public ObservableField<String> date = new ObservableField<>();
    //播放器工具类
    private AudioPlayer audioPlayer;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        audioPlayer = new AudioPlayer();

        getSubjectDetail(GetBeanDate.getUserUuid(), date.get(), subjectId.get());
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
     * 作业列表详情
     *
     * @param uuid
     * @param date
     * @param subjectId
     */
    public void getSubjectDetail(String uuid, String date, int subjectId) {
        recordSubjectDetailBeanCall = iRequest.getsubjectdetail(BaseApplication.getHeaders(), uuid, date, subjectId);
        recordSubjectDetailBeanCall.enqueue(new Callback<RecordSubjectDetailBean>() {
            @Override
            public void onResponse(Call<RecordSubjectDetailBean> call, Response<RecordSubjectDetailBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.listIsEmpty(response.body().getData().getRecords())) {
                        recordSubjectDetailBeanLiveData.setValue(response.body().getData().getRecords());
                    }
                }
            }

            @Override
            public void onFailure(Call<RecordSubjectDetailBean> call, Throwable t) {//请求失败
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
