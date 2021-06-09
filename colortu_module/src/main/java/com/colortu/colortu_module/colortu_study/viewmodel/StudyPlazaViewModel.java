package com.colortu.colortu_module.colortu_study.viewmodel;

import android.os.Bundle;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.StudyPlazaBean;
import com.colortu.colortu_module.colortu_base.bean.StudyRecommendBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/4/8
 * @module : StudyPlazaViewModel
 * @describe :自习广场列表界面ViewModel
 */
public class StudyPlazaViewModel extends BaseActivityViewModel<BaseRequest> {
    //获得自习室广场列表接口/获得自习室广场筛选列表接口
    private Call<StudyPlazaBean> studyPlazaBeanCall;
    //自习室随机推荐接口
    private Call<StudyRecommendBean> studyRecommendBeanCall;
    //post请求体参数
    private PostParams postParams;

    //官方自习列表数据
    public MutableLiveData<List<StudyPlazaBean.DataBean.OfficialBean>> officialLiveData = new MutableLiveData<>();
    //个人自习列表数据
    public MutableLiveData<List<StudyPlazaBean.DataBean.PersonalBean>> personLiveData = new MutableLiveData<>();

    //筛选tip
    public ObservableField<String> filtratename = new ObservableField<>();

    /**
     * 初始化数据
     */
    public void initPlazaData() {
        //实例化
        postParams = new PostParams();

        String filtrate = GetBeanDate.getStudyFiltrateName();
        if (EmptyUtils.stringIsEmpty(filtrate)) {
            filtratename.set(GetBeanDate.getStudyFiltrateName());
            getStudyPlazaFiltrate(filtrate);
        } else {
            filtratename.set(BaseApplication.getContext().getResources().getString(R.string.filtrate));
            getStudyPlaza();
        }
    }

    /**
     * 自习室随机推荐
     */
    public void onRecommend() {
        getRecommend(GetBeanDate.getUserUuid());
    }

    /**
     * 获得自习室广场列表接口请求
     */
    public void getStudyPlaza() {
        studyPlazaBeanCall = iRequest.getStudyPlaza(BaseApplication.getHeaders());
        studyPlazaBeanCall.enqueue(new Callback<StudyPlazaBean>() {
            @Override
            public void onResponse(Call<StudyPlazaBean> call, Response<StudyPlazaBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    //官方自习
                    officialLiveData.setValue(response.body().getData().getOfficial());

                    //个人自习
                    personLiveData.setValue(response.body().getData().getPersonal());
                }
            }

            @Override
            public void onFailure(Call<StudyPlazaBean> call, Throwable t) {//请求失败

            }
        });
    }

    /**
     * 获得自习室广场筛选列表接口请求
     */
    public void getStudyPlazaFiltrate(String filtratename) {
        studyPlazaBeanCall = iRequest.getStudyPlazaFiltrate(BaseApplication.getHeaders(), filtratename);
        studyPlazaBeanCall.enqueue(new Callback<StudyPlazaBean>() {
            @Override
            public void onResponse(Call<StudyPlazaBean> call, Response<StudyPlazaBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    //官方自习
                    officialLiveData.setValue(response.body().getData().getOfficial());

                    //个人自习
                    personLiveData.setValue(response.body().getData().getPersonal());
                }
            }

            @Override
            public void onFailure(Call<StudyPlazaBean> call, Throwable t) {//请求失败

            }
        });
    }

    /**
     * 自习室随机推荐接口请求
     *
     * @param uuid 用户uuid
     */
    public void getRecommend(String uuid) {
        HashMap hashMap = postParams.add("uuid", uuid).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        studyRecommendBeanCall = iRequest.getRecommend(BaseApplication.getHeaders(), requestBody);
        studyRecommendBeanCall.enqueue(new Callback<StudyRecommendBean>() {
            @Override
            public void onResponse(Call<StudyRecommendBean> call, Response<StudyRecommendBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.listIsEmpty(response.body().getData().getStudyRoom())) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", response.body().getData().getStudyRoom().get(0).getId());
                        bundle.putString("channel", response.body().getData().getStudyRoom().get(0).getChannel());
                        BaseUIKit.startActivity(UIKitName.STUDY_PLAZA, UIKitName.STUDY_DETAIL,
                                BaseConstant.STUDY_DETAIL, BaseUIKit.OTHER, bundle);
                    }
                }
            }

            @Override
            public void onFailure(Call<StudyRecommendBean> call, Throwable t) {//请求失败

            }
        });
    }
}
