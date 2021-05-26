package com.colortu.colortu_module.colortu_record.viewmodel;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;

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
import com.colortu.colortu_module.colortu_base.bean.RecordMarkFinishBean;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectBean;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/11/27
 * @module : RecordSubjectViewModel
 * @describe :今日作业界面ViewModel
 */
public class RecordSubjectViewModel extends BaseActivityViewModel<BaseRequest> {
    //所有作业列表请求
    private Call<RecordSubjectBean> recordSubjectBeanCall;
    //标为已完成/未完成请求
    private Call<RecordMarkFinishBean> recordMarkFinishBeanCall;
    //post请求体参数
    private PostParams postParams;

    //今日作业列表数据
    public MutableLiveData<List<RecordSubjectBean.DataBean.RecordsBean>> recordSubjectBeanLiveData = new MutableLiveData<>();

    private Handler handler;
    //当天日期
    public ObservableField<String> datetime = new ObservableField<>();
    //作业口令
    public ObservableField<String> command = new ObservableField<>();
    //查询时间
    public String checktime;
    //科目id
    public ObservableField<Integer> subjectId = new ObservableField<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        handler = new Handler();
        postParams = new PostParams();

        //获取当前时间
        datetime.set(String.valueOf(DateFormat.format("yy/MM/dd", Calendar.getInstance(Locale.CHINA))));
        checktime = String.valueOf(DateFormat.format("yyyy-MM-dd", Calendar.getInstance(Locale.CHINA)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHomeWorkRunnable();
    }

    /**
     * 跳转录音界面
     */
    public void onJumpInput() {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        BaseUIKit.startActivity(UIKitName.RECORD_SUBJECT, UIKitName.RECORD_INPUT,
                BaseConstant.RECORD_INPUT, BaseUIKit.OTHER, bundle);
    }

    /**
     * 所有作业列表Runnable
     */
    public void getHomeWorkRunnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getHomeWork(GetBeanDate.getUserUuid(), checktime);
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
                    if (EmptyUtils.listIsEmpty(response.body().getData().getRecords())) {
                        //作业口令
                        command.set(response.body().getData().getRecords().get(0).getPassword());
                        recordSubjectBeanLiveData.setValue(response.body().getData().getRecords());
                    } else {
                        command.set("");
                        recordSubjectBeanLiveData.setValue(null);
                    }
                } else {
                    command.set("");
                }
            }

            @Override
            public void onFailure(Call<RecordSubjectBean> call, Throwable t) {//请求失败
                command.set("");
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
                markFinish(GetBeanDate.getUserUuid(), subjectId.get(), checktime);
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
