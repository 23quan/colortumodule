package com.colortu.colortu_module.colortu_teach.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.bean.TeachTopicAnswerBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachAnswerViewModel
 * @describe :原题答案界面ViewModel
 */
public class TeachAnswerViewModel extends BaseActivityViewModel<BaseRequest> {
    //教辅原题答案详情请求
    private Call<TeachTopicAnswerBean> teachTopicAnswerBeanCall;
    //post请求体参数
    private PostParams postParams;

    //原题答案列表数据
    public MutableLiveData<List<TeachTopicAnswerBean.DataBean.QuestionBean>> teachTopicAnswerBeanLiveData = new MutableLiveData<>();

    //课名
    public ObservableField<String> classname = new ObservableField<>();
    //课id
    public ObservableField<Integer> examid = new ObservableField<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        postParams = new PostParams();

        getAnswerDetail(examid.get());
    }

    /**
     * 获取教辅原题答案详情
     *
     * @param exam_id
     */
    public void getAnswerDetail(int exam_id) {
        //请求参数
        HashMap hashMap = postParams.add("exam_id", exam_id).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        teachTopicAnswerBeanCall = iRequest.getanswerdetail(BaseApplication.getHeaders(), requestBody);
        teachTopicAnswerBeanCall.enqueue(new Callback<TeachTopicAnswerBean>() {
            @Override
            public void onResponse(Call<TeachTopicAnswerBean> call, Response<TeachTopicAnswerBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.listIsEmpty(response.body().getData().getQuestion())) {
                        teachTopicAnswerBeanLiveData.setValue(response.body().getData().getQuestion());
                    } else {
                        TipToast.tipToastShort(BaseApplication.getContext().getString(R.string.update_content));
                    }
                } else {
                    TipToast.tipToastShort(BaseApplication.getContext().getString(R.string.update_content));
                }
            }

            @Override
            public void onFailure(Call<TeachTopicAnswerBean> call, Throwable t) {//请求失败
            }
        });
    }
}
