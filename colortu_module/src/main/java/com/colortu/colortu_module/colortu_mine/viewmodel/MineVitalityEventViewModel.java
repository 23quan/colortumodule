package com.colortu.colortu_module.colortu_mine.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.MineVitalityEventBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/7
 * @module : MineVitalityEventViewModel
 * @describe :元气活动界面ViewModel
 */
public class MineVitalityEventViewModel extends BaseActivityViewModel<BaseRequest> {
    //查看元气活动请求
    private Call<MineVitalityEventBean> mineVitalityEventBeanCall;

    //元气活动列表数据
    public MutableLiveData<List<MineVitalityEventBean.DataBeanX.DataBean>> vitalityEventLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();

        getVitalityEvent(GetBeanDate.getUserUuid());
    }

    /**
     * 查看元气活动
     *
     * @param uuid 用户uuid
     */
    public void getVitalityEvent(String uuid) {
        mineVitalityEventBeanCall = iRequest.getvitalityevent(BaseApplication.getHeaders(), uuid);
        mineVitalityEventBeanCall.enqueue(new Callback<MineVitalityEventBean>() {
            @Override
            public void onResponse(Call<MineVitalityEventBean> call, Response<MineVitalityEventBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    vitalityEventLiveData.setValue(response.body().getData().getData());
                }
            }

            @Override
            public void onFailure(Call<MineVitalityEventBean> call, Throwable t) {//请求失败
            }
        });
    }
}
