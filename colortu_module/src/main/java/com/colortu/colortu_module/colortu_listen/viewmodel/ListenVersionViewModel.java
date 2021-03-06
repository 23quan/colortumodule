package com.colortu.colortu_module.colortu_listen.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.ListenVersionBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenVersionViewModel
 * @describe :听写课本版本界面ViewModel
 */
public class ListenVersionViewModel extends BaseActivityViewModel<BaseRequest> implements AudioFocusUtils.OnAudioFocusListener, BaseApplication.OnFinishTipVoiceListener {
    //获取教材版本请求
    private Call<ListenVersionBean> listenVersionBeanCall;

    //是否释放资源
    public ObservableField<Boolean> isrelease = new ObservableField<>();
    //版本列表数据
    public MutableLiveData<List<ListenVersionBean.DataBean.RecordsBean>> listenVersionBeanLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        //获取音频焦点
        AudioFocusUtils.setOnAudioFocusListener(this);
        AudioFocusUtils.initAudioFocus(BaseApplication.getContext());
        //播放提示音
        BaseApplication.setOnFinishTipVoiceListener(this);
        BaseApplication.onStartTipVoice(R.raw.music_choose_version);
        isrelease.set(false);

        getVersion("2", "1", "100", GetBeanDate.getUserUuid());
    }

    /**
     * 提示音播放结束
     */
    @Override
    public void onFinishTipVoice() {
        //解绑音频焦点
        AudioFocusUtils.abandonAudioFocus();
    }

    /**
     * 失去焦点
     */
    @Override
    public void onLossAudioFocus() {
        if (BaseApplication.isPlaying()) {
            BaseApplication.onStopTipVoice();
        }
    }

    /**
     * 获取焦点
     */
    @Override
    public void onGainAudioFocus() {

    }

    /**
     * 获取教材版本请求
     *
     * @param subjectid 科目编号，默认2 - 英语
     * @param current   当前页
     * @param size      每页显示数量
     * @param uuid      用户uuid
     */
    public void getVersion(String subjectid, String current, String size, String uuid) {
        listenVersionBeanCall = iRequest.getVersion(subjectid, current, size, uuid);
        listenVersionBeanCall.enqueue(new Callback<ListenVersionBean>() {
            @Override
            public void onResponse(Call<ListenVersionBean> call, Response<ListenVersionBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    listenVersionBeanLiveData.setValue(response.body().getData().getRecords());
                }
            }

            @Override
            public void onFailure(Call<ListenVersionBean> call, Throwable t) {//请求失败
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑音频焦点
        AudioFocusUtils.abandonAudioFocus();
        //停止播放，释放资源
        if (!isrelease.get()) {
            BaseApplication.onStopTipVoice();
        }
    }
}
