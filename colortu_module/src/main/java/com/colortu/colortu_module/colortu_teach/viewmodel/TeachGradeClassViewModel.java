package com.colortu.colortu_module.colortu_teach.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.TeachGradeClassBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : GradeClassViewModel
 * @describe :年级课目界面ViewModel
 */
public class TeachGradeClassViewModel extends BaseActivityViewModel<BaseRequest> {
    //获取教辅原题答案列表请求
    private Call<TeachGradeClassBean> teachGradeClassBeanCall;
    //post请求体参数
    private PostParams postParams;

    //年级课目列表数据
    public MutableLiveData<List<TeachGradeClassBean.DataBean.ListBeanX>> teachGradeClassBeanLiveData = new MutableLiveData<>();

    //年级名字
    public ObservableField<String> gradename = new ObservableField<>();
    //我的教辅id
    public ObservableField<Integer> bookId = new ObservableField<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        postParams = new PostParams();

        getGradeClass(bookId.get());
    }

    /**
     * 获取教辅原题答案列表
     */
    public void getGradeClass(int bookId) {
        //请求参数
        HashMap hashMap = postParams.add("bookId", bookId).getHashMap();
        final RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        teachGradeClassBeanCall = iRequest.getgradeclass(BaseApplication.getHeaders(), requestBody);
        teachGradeClassBeanCall.enqueue(new Callback<TeachGradeClassBean>() {
            @Override
            public void onResponse(Call<TeachGradeClassBean> call, Response<TeachGradeClassBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.listIsEmpty(response.body().getData().getList())) {
                        teachGradeClassBeanLiveData.setValue(response.body().getData().getList());
                    }
                }
            }

            @Override
            public void onFailure(Call<TeachGradeClassBean> call, Throwable t) {//请求失败
            }
        });
    }
}
