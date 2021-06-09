package com.colortu.colortu_module.colortu_record.viewmodel;

import android.os.Handler;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.bean.RecordMarkFinishBean;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordHistorySubjectViewModel
 * @describe :历史科目界面ViewModel
 */
public class RecordHistorySubjectViewModel extends BaseActivityViewModel<BaseRequest> {
    //所有作业列表请求
    private Call<RecordSubjectBean> recordSubjectBeanCall;
    //标为已完成/未完成请求
    private Call<RecordMarkFinishBean> recordMarkFinishBeanCall;
    //post请求体参数
    private PostParams postParams;

    //科目列表数据
    public MutableLiveData<List<RecordSubjectBean.DataBean.RecordsBean>> recordSubjectLiveData = new MutableLiveData<>();

    private Handler handler;
    //年月
    public ObservableField<String> datemonth = new ObservableField<>();
    //日
    public ObservableField<String> day = new ObservableField<>();
    //日期
    public ObservableField<String> date = new ObservableField<>();
    //科目id
    public ObservableField<Integer> subjectId = new ObservableField<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        handler = new Handler();
        postParams = new PostParams();

        getHomeWorkRunnable();
    }

    /**
     * 所有作业列表Runnable
     */
    public void getHomeWorkRunnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getHomeWork(GetBeanDate.getUserUuid(), date.get());
            }
        });
    }

    /**
     * 所有作业列表
     *
     * @param uuid 用户uuid
     * @param date 查询时间
     */
    public void getHomeWork(String uuid, String date) {
        recordSubjectBeanCall = iRequest.gethomework(BaseApplication.getHeaders(), uuid, date);
        recordSubjectBeanCall.enqueue(new Callback<RecordSubjectBean>() {
            @Override
            public void onResponse(Call<RecordSubjectBean> call, Response<RecordSubjectBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    recordSubjectLiveData.setValue(response.body().getData().getRecords());
                }
            }

            @Override
            public void onFailure(Call<RecordSubjectBean> call, Throwable t) {//请求失败
            }
        });
    }

    /**
     * 标为已完成/未完成Runnable
     */
    public void markFinishRunnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                markFinish(GetBeanDate.getUserUuid(), subjectId.get(), date.get());
            }
        });
    }

    /**
     * 标为已完成/未完成
     *
     * @param uuid        用户uuid
     * @param subjectId   科目id
     * @param currentDate 时间
     */
    public void markFinish(String uuid, int subjectId, String currentDate) {
        //请求参数
        HashMap hashMap = postParams.add("uuid", uuid).add("subjectId", subjectId).add("currentDate", currentDate).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        recordMarkFinishBeanCall = iRequest.markfinish(BaseApplication.getHeaders(), requestBody);
        recordMarkFinishBeanCall.enqueue(new Callback<RecordMarkFinishBean>() {
            @Override
            public void onResponse(Call<RecordMarkFinishBean> call, Response<RecordMarkFinishBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    getHomeWorkRunnable();
                } else {
                    TipToast.tipToastShort("标记失败");
                }
            }

            @Override
            public void onFailure(Call<RecordMarkFinishBean> call, Throwable t) {//请求失败
            }
        });
    }
}
