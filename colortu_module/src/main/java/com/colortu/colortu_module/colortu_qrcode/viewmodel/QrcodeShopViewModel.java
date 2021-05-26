package com.colortu.colortu_module.colortu_qrcode.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.QrcodeShopBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/1/11
 * @module : QrcodeShopViewModel
 * @describe :小兔商城界面ViewModel
 */
public class QrcodeShopViewModel extends BaseActivityViewModel<BaseRequest> {
    //获取元气商城二维码请求
    private Call<QrcodeShopBean> qrcodeShopBeanCall;

    //商城二维码
    public MutableLiveData<String> codeimg = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();

        getShop(GetBeanDate.getUserUuid());
    }

    /**
     * 获取元气商城二维码
     *
     * @param uuid 用户uuid
     */
    public void getShop(String uuid) {
        qrcodeShopBeanCall = iRequest.getshop(BaseApplication.getHeaders(), uuid);
        qrcodeShopBeanCall.enqueue(new Callback<QrcodeShopBean>() {
            @Override
            public void onResponse(Call<QrcodeShopBean> call, Response<QrcodeShopBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.stringIsEmpty(response.body().getData().getImage())) {
                        codeimg.setValue(response.body().getData().getImage());
                    }
                }
            }

            @Override
            public void onFailure(Call<QrcodeShopBean> call, Throwable t) {//请求失败
            }
        });
    }
}
