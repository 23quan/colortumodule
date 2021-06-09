package com.colortu.colortu_module.colortu_record.viewmodel;

import android.os.Handler;
import android.text.format.DateFormat;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.RecordHistoryBean;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordHistoryViewModel
 * @describe :作业历史界面ViewModel
 */
public class RecordHistoryViewModel extends BaseActivityViewModel<BaseRequest> {
    //查询历史作业请求
    private Call<RecordHistoryBean> historyBeanCall;

    //作业历史列表数据
    public MutableLiveData<List<RecordHistoryBean.DataBean.RecordsBean>> recordHistoryBeanLiveData = new MutableLiveData<>();

    private Handler handler;
    //年份
    public ObservableField<String> yearObser = new ObservableField<>();
    //月份
    public ObservableField<String> monthObser = new ObservableField<>();
    //传参年份
    public ObservableField<String> yearparameter = new ObservableField<>();
    //传参月份
    public ObservableField<String> monthparameter = new ObservableField<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        handler = new Handler();

        String year = String.valueOf(DateFormat.format("yyyy", Calendar.getInstance(Locale.CHINA)));
        String month = String.valueOf(DateFormat.format("MM", Calendar.getInstance(Locale.CHINA)));
        yearparameter.set(year);
        monthparameter.set(month);
        yearObser.set(year + "年");
        monthObser.set(month + "月");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHistoryRunnable();
    }

    /**
     * 查询历史作业Runnable
     */
    public void getHistoryRunnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getHistory(GetBeanDate.getUserUuid(), yearparameter.get(), monthparameter.get());
            }
        });
    }

    /**
     * 查询历史作业
     *
     * @param uuid  用户uuid
     * @param year  年份
     * @param month 月份
     */
    public void getHistory(String uuid, String year, String month) {
        historyBeanCall = iRequest.gethistory(BaseApplication.getHeaders(), uuid, year, month);
        historyBeanCall.enqueue(new Callback<RecordHistoryBean>() {
            @Override
            public void onResponse(Call<RecordHistoryBean> call, Response<RecordHistoryBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    recordHistoryBeanLiveData.setValue(response.body().getData().getRecords());
                }
            }

            @Override
            public void onFailure(Call<RecordHistoryBean> call, Throwable t) {//请求失败
            }
        });
    }
}
