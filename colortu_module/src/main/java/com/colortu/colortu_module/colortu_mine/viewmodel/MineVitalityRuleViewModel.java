package com.colortu.colortu_module.colortu_mine.viewmodel;

import androidx.databinding.ObservableField;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.MineVitalityRuleBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/7
 * @module : MineVitalityRuleViewModel
 * @describe :元气规则界面ViewModel
 */
public class MineVitalityRuleViewModel extends BaseActivityViewModel<BaseRequest> {
    //查看元气规则请求
    private Call<MineVitalityRuleBean> mineVitalityRuleBeanCall;

    //元气规则
    public ObservableField<String> vitalityrule = new ObservableField<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        getVitalityRule();
    }

    /**
     * 查看元气规则
     */
    public void getVitalityRule() {
        mineVitalityRuleBeanCall = iRequest.getvitalityrule(BaseApplication.getHeaders());
        mineVitalityRuleBeanCall.enqueue(new Callback<MineVitalityRuleBean>() {
            @Override
            public void onResponse(Call<MineVitalityRuleBean> call, Response<MineVitalityRuleBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.stringIsEmpty(response.body().getData())) {
                    vitalityrule.set(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<MineVitalityRuleBean> call, Throwable t) {//请求失败
            }
        });
    }
}
