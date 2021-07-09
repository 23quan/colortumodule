package com.colortu.colortu_module.colortu_listen.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.ListenGradeBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenGradeViewModel
 * @describe :听写年级界面ViewModel
 */
public class ListenGradeViewModel extends BaseActivityViewModel<BaseRequest> implements AudioFocusUtils.OnAudioFocusListener, BaseApplication.OnFinishTipVoiceListener {
    //获取年级列表数据请求
    private Call<ListenGradeBean> listenGradeBeanCall;

    //年级列表数据
    public MutableLiveData<List<ListenGradeBean.DataBean.ListBean>> listenGradeBeanLiveData = new MutableLiveData<>();

    private List<Integer> integerList = new ArrayList<>();
    public MutableLiveData<List<Integer>> integerLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        //获取音频焦点
        AudioFocusUtils.setOnAudioFocusListener(this);
        AudioFocusUtils.initAudioFocus(BaseApplication.getContext());
        //播放提示音
        BaseApplication.setOnFinishTipVoiceListener(this);
        BaseApplication.onStartTipVoice(R.raw.music_choose_grade);

        integerList.add(R.mipmap.icon_listen_grade1);
        integerList.add(R.mipmap.icon_listen_grade2);
        integerList.add(R.mipmap.icon_listen_grade3);
        integerList.add(R.mipmap.icon_listen_grade4);
        integerList.add(R.mipmap.icon_listen_grade5);
        integerList.add(R.mipmap.icon_listen_grade6);
        integerLiveData.setValue(integerList);
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
     * 获取年级列表数据请求
     */
    public void getGrade() {
        listenGradeBeanCall = iRequest.getGrade();
        listenGradeBeanCall.enqueue(new Callback<ListenGradeBean>() {
            @Override
            public void onResponse(Call<ListenGradeBean> call, Response<ListenGradeBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    listenGradeBeanLiveData.setValue(response.body().getData().getList());
                }
            }

            @Override
            public void onFailure(Call<ListenGradeBean> call, Throwable t) {//请求失败
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑音频焦点
        AudioFocusUtils.abandonAudioFocus();
        //停止播放，释放资源
        BaseApplication.onStopTipVoice();
    }
}
