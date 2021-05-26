package com.colortu.colortu_module.colortu_qrcode.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.QrcodeHelpBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/1/11
 * @module : QrcodeHelpViewModel
 * @describe :帮助界面ViewModel
 */
public class QrcodeHelpViewModel extends BaseActivityViewModel<BaseRequest> {
    //获取帮助二维码请求
    private Call<QrcodeHelpBean> qrcodeHelpBeanCall;

    //帮助二维码
    public MutableLiveData<String> codeimg = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();

        getHelp(GetBeanDate.getUserUuid());
    }

    /**
     * 获取帮助二维码
     *
     * @param uuid 用户uuid
     */
    public void getHelp(String uuid) {
        qrcodeHelpBeanCall = iRequest.gethelp(BaseApplication.getHeaders(), uuid);
        qrcodeHelpBeanCall.enqueue(new Callback<QrcodeHelpBean>() {
            @Override
            public void onResponse(Call<QrcodeHelpBean> call, Response<QrcodeHelpBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.stringIsEmpty(response.body().getData().getImage())) {
                        codeimg.setValue(response.body().getData().getImage());
                    }
                }
            }

            @Override
            public void onFailure(Call<QrcodeHelpBean> call, Throwable t) {//请求失败
            }
        });
    }
}
