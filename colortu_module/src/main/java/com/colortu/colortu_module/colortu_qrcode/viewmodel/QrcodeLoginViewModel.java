package com.colortu.colortu_module.colortu_qrcode.viewmodel;

import android.os.Handler;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.QrcodeLoginBean;
import com.colortu.colortu_module.colortu_base.bean.QrcodeUserInfoBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/11/25
 * @module : QrcodeLoginViewModel
 * @describe :登录界面ViewModel
 */
public class QrcodeLoginViewModel extends BaseActivityViewModel<BaseRequest> {
    //登录二维码接口请求
    private Call<QrcodeLoginBean> loginCodeBeanCall;
    //用户信息接口请求
    private Call<QrcodeUserInfoBean> userInfoBeanCall;

    //是否有缓存图片
    public ObservableField<Boolean> iscache = new ObservableField<>();
    //登录二维码
    public MutableLiveData<String> codeimg = new MutableLiveData<>();
    //文字提示信息
    public ObservableField<String> codetip1 = new ObservableField<>();
    //文字提示信息
    public ObservableField<String> codetip2 = new ObservableField<>();
    //文字提示信息
    public ObservableField<String> codetip3 = new ObservableField<>();
    //跳过提示信息
    public ObservableField<String> skiptip = new ObservableField<>();
    //是否登录成功
    public MutableLiveData<Boolean> isLoginLiveData = new MutableLiveData<>();

    private Handler handler;
    public long requesttime = 2 * 1000;

    public void onRequestLogin() {
        //实例化
        handler = new Handler();

        if (EmptyUtils.stringIsEmpty(GetBeanDate.getLoginCode())) {
            iscache.set(true);
            codeimg.setValue(GetBeanDate.getLoginCode());
            //请求用户信息接口
            handler.postDelayed(getUserInfoRunnable, requesttime);
        } else {
            iscache.set(false);
            //请求登录二维码接口
            getLoginCodeRuunable();
        }
    }


    /**
     * 登录二维码接口Ruunable
     */
    public void getLoginCodeRuunable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getLoginCode(GetBeanDate.getUserUuid(), "1", ChannelUtil.Channel());
            }
        });
    }

    /**
     * 登录二维码接口
     *
     * @param uuid    设备id
     * @param eid     年级id
     * @param channel 渠道
     */
    public void getLoginCode(String uuid, String eid, String channel) {
        loginCodeBeanCall = iRequest.getlogincode(BaseApplication.getHeaders(), uuid, eid, channel);
        loginCodeBeanCall.enqueue(new Callback<QrcodeLoginBean>() {
            @Override
            public void onResponse(Call<QrcodeLoginBean> call, Response<QrcodeLoginBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.stringIsEmpty(response.body().getData().getImage())) {
                        //登录二维码
                        codeimg.setValue(response.body().getData().getImage());
                        //请求用户信息接口
                        handler.postDelayed(getUserInfoRunnable, requesttime);
                    }
                }
            }

            @Override
            public void onFailure(Call<QrcodeLoginBean> call, Throwable t) {//请求失败
            }
        });
    }

    /**
     * 请求用户信息接口Runnable
     */
    Runnable getUserInfoRunnable = new Runnable() {
        @Override
        public void run() {
            getUserInfo(GetBeanDate.getUserUuid());
        }
    };

    /**
     * 用户信息接口
     *
     * @param uuid
     */
    public void getUserInfo(String uuid) {
        userInfoBeanCall = iRequest.getuserinfo(BaseApplication.getHeaders(), uuid);
        userInfoBeanCall.enqueue(new Callback<QrcodeUserInfoBean>() {
            @Override
            public void onResponse(Call<QrcodeUserInfoBean> call, Response<QrcodeUserInfoBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.stringIsEmpty(response.body().getData().getUserInfo().getOpenId())) {
                        //存储用户信息
                        GetBeanDate.putUserInfo(JSONObject.toJSONString(response.body().getData()));
                        GetBeanDate.putOpenid(response.body().getData().getUserInfo().getOpenId());
                        GetBeanDate.putUserHead(response.body().getData().getUserInfo().getAvatar());
                        GetBeanDate.putIsLogin(true);

                        //跳转界面
                        if (EmptyUtils.listIsEmpty(response.body().getData().getUserList())) {
                            isLoginLiveData.setValue(true);
                        } else {
                            isLoginLiveData.setValue(false);
                        }
                    } else {
                        handler.postDelayed(getUserInfoRunnable, requesttime);
                    }
                } else {
                    handler.postDelayed(getUserInfoRunnable, requesttime);
                }
            }

            @Override
            public void onFailure(Call<QrcodeUserInfoBean> call, Throwable t) {//请求失败
                handler.postDelayed(getUserInfoRunnable, requesttime);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(getUserInfoRunnable);
        }
    }
}
