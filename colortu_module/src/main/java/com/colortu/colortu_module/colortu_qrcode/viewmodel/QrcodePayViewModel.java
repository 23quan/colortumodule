package com.colortu.colortu_module.colortu_qrcode.viewmodel;

import android.os.Handler;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.bean.QrcodePayBean;
import com.colortu.colortu_module.colortu_base.bean.QrcodePayStatusBean;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : QrcodePayViewModel
 * @describe :支付界面ViewModel
 */
public class QrcodePayViewModel extends BaseActivityViewModel<BaseRequest> {
    //获取支付二维码请求
    private Call<QrcodePayBean> qrcodePayBeanCall;
    //判断是否支付成功请求
    private Call<QrcodePayStatusBean> qrcodePayStatusBeanCall;

    //文字提示信息
    public ObservableField<String> codetip1 = new ObservableField<>();
    //文字提示信息
    public ObservableField<String> codetip2 = new ObservableField<>();
    //文字提示信息
    public ObservableField<String> codetip3 = new ObservableField<>();
    //是否有缓存图片
    public ObservableField<Boolean> iscache = new ObservableField<>();
    //二维码url
    public MutableLiveData<String> codeimg = new MutableLiveData<>();
    //支付成功监听
    public MutableLiveData<Boolean> paysuccess = new MutableLiveData<>();

    private Handler handler;
    public long requesttime = 2 * 1000;

    public void onRequestPay() {
        //实例化
        handler = new Handler();

        if (EmptyUtils.stringIsEmpty(GetBeanDate.getPayCode())) {
            iscache.set(true);
            codeimg.setValue(GetBeanDate.getPayCode());
            handler.postDelayed(getPayStatusRunnable, requesttime);
        } else {
            iscache.set(false);
            getPay(GetBeanDate.getUserUuid());
        }
    }

    /**
     * 获取解锁二维码
     *
     * @param uuid 用户uuid
     */
    public void getPay(String uuid) {
        qrcodePayBeanCall = iRequest.getpaycode(BaseApplication.getHeaders(), uuid);
        qrcodePayBeanCall.enqueue(new Callback<QrcodePayBean>() {
            @Override
            public void onResponse(Call<QrcodePayBean> call, Response<QrcodePayBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.stringIsEmpty(response.body().getData().getImage())) {
                        codeimg.setValue(response.body().getData().getImage());
                        handler.postDelayed(getPayStatusRunnable, requesttime);
                    }
                }
            }

            @Override
            public void onFailure(Call<QrcodePayBean> call, Throwable t) {//请求失败
            }
        });
    }

    /**
     * 判断是否支付成功Runnable
     */
    Runnable getPayStatusRunnable = new Runnable() {
        @Override
        public void run() {
            getPayStatus(GetBeanDate.getUserUuid());
        }
    };

    /**
     * 判断是否支付成功
     *
     * @param uuid
     */
    public void getPayStatus(String uuid) {
        qrcodePayStatusBeanCall = iRequest.getpaystatus(BaseApplication.getHeaders(), uuid);
        qrcodePayStatusBeanCall.enqueue(new Callback<QrcodePayStatusBean>() {
            @Override
            public void onResponse(Call<QrcodePayStatusBean> call, Response<QrcodePayStatusBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    GetBeanDate.putIsTranslateVIP(1);
                    GetBeanDate.putIsBookVIP(1);
                    TipToast.tipToastShort(BaseApplication.getContext().getString(R.string.pay_success));
                    paysuccess.setValue(true);
                } else {
                    handler.postDelayed(getPayStatusRunnable, requesttime);
                }
            }

            @Override
            public void onFailure(Call<QrcodePayStatusBean> call, Throwable t) {//请求失败
                handler.postDelayed(getPayStatusRunnable, requesttime);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(getPayStatusRunnable);
        }
    }
}
