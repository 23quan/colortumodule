package com.colortu.colortu_module.colortu_record.viewmodel;

import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_record.activity.RecordInputActivity;
import com.colortu.colortu_module.colortu_base.bean.RecordAddWorkBean;
import com.colortu.colortu_module.colortu_base.bean.RecordChooseSubjectBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/2
 * @module : RecordChooseSubjectViewModel
 * @describe :选择科目界面ViewModel
 */
public class RecordChooseSubjectViewModel extends BaseActivityViewModel<BaseRequest> {
    //获取所有科目请求
    private Call<RecordChooseSubjectBean> recordChooseSubjectBeanCall;
    //添加作业请求
    private Call<RecordAddWorkBean> recordAddWorkBeanCall;
    //post请求体参数
    private PostParams postParams;

    //选择科目界面列表数据
    public MutableLiveData<List<RecordChooseSubjectBean.DataBean.RecordsBean>> recordChooseSubjectLiveData = new MutableLiveData<>();

    private Handler handler;
    //科目名字
    public ObservableField<String> subjectname = new ObservableField<>();
    //科目id
    public ObservableField<Integer> subjectId = new ObservableField<>();
    //录入url
    public ObservableField<String> audiourl = new ObservableField<>();
    //录入时长
    public ObservableField<String> duration = new ObservableField<>();
    //语音翻译
    public ObservableField<String> translate = new ObservableField<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        postParams = new PostParams();
        handler = new Handler();

        getSelectSubject(1, 10);
    }

    /**
     * 跳转到科目详情界面
     */
    public void onJumpSubjectDetail() {
        Bundle bundle = new Bundle();
        bundle.putString("subjectname", subjectname.get());
        bundle.putInt("subjectId", subjectId.get());
        BaseUIKit.startActivity(UIKitName.RECORD_CHOOSESUBJECT, UIKitName.RECORD_SUBJECTDETAIL,
                BaseConstant.RECORD_SUBJECTDETAIL, BaseUIKit.OTHER, bundle);
        BaseApplication.getInstance().finishActivity(RecordInputActivity.class);
        finish();
    }

    /**
     * 获取所有科目
     *
     * @param current
     * @param size
     */
    public void getSelectSubject(int current, int size) {
        recordChooseSubjectBeanCall = iRequest.getselectsubject(BaseApplication.getHeaders(), current, size);
        recordChooseSubjectBeanCall.enqueue(new Callback<RecordChooseSubjectBean>() {
            @Override
            public void onResponse(Call<RecordChooseSubjectBean> call, Response<RecordChooseSubjectBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    recordChooseSubjectLiveData.setValue(response.body().getData().getRecords());
                }
            }

            @Override
            public void onFailure(Call<RecordChooseSubjectBean> call, Throwable t) {//请求失败
            }
        });
    }

    /**
     * 添加作业Runnable
     */
    public void addHomeWorkRunnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                addHomeWork(GetBeanDate.getUserUuid(), subjectId.get(), translate.get(), audiourl.get(), duration.get());
            }
        });
    }

    /**
     * 添加作业
     *
     * @param uuid      用户uuid
     * @param subjectId 科目id
     * @param content   录音翻译文本
     * @param audioUrl  录音url
     * @param audioTime 录音时长
     */
    public void addHomeWork(String uuid, int subjectId, String content, String audioUrl, String audioTime) {
        HashMap hashMap = postParams.add("uuid", uuid).add("subjectId", subjectId).add("content", content)
                .add("audioUrl", audioUrl).add("audioTime", audioTime).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        recordAddWorkBeanCall = iRequest.addhomework(BaseApplication.getHeaders(), requestBody);
        recordAddWorkBeanCall.enqueue(new Callback<RecordAddWorkBean>() {
            @Override
            public void onResponse(Call<RecordAddWorkBean> call, Response<RecordAddWorkBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    onJumpSubjectDetail();
                } else {
                    TipToast.tipToastShort("保存失败");
                }
            }

            @Override
            public void onFailure(Call<RecordAddWorkBean> call, Throwable t) {//请求失败
            }
        });
    }
}
