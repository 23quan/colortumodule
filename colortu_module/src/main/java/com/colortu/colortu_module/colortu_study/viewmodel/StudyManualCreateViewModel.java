package com.colortu.colortu_module.colortu_study.viewmodel;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.bean.StudyCreateBean;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/4/16
 * @module : StudyManualCreateViewModel
 * @describe :手动创建自习室界面ViewModel
 */
public class StudyManualCreateViewModel extends BaseActivityViewModel<BaseRequest> {
    //创建自习室接口
    private Call<StudyCreateBean> studyCreateBeanCall;
    //post请求体参数
    private PostParams postParams;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        postParams = new PostParams();
    }

    /**
     * 创建自习室接口请求
     *
     * @param uuid 用户uuid
     * @param name 自习室名称
     */
    public void getStudyCommand(String uuid, String name) {
        HashMap hashMap = postParams.add("uuid", uuid).add("name", name).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        studyCreateBeanCall = iRequest.getStudyCreate(BaseApplication.getHeaders(), requestBody);
        studyCreateBeanCall.enqueue(new Callback<StudyCreateBean>() {
            @Override
            public void onResponse(Call<StudyCreateBean> call, Response<StudyCreateBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body())) {
                    if (response.body().getCode() == 200) {
                        TipToast.tipToastShort(response.body().getMsg());
                        finish();
                    } else {
                        TipToast.tipToastShort(response.body().getMsg());
                    }
                } else {
                    TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.studyroom_createfailure));
                }
            }

            @Override
            public void onFailure(Call<StudyCreateBean> call, Throwable t) {//请求失败
                TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.studyroom_createfailure));
            }
        });
    }
}
