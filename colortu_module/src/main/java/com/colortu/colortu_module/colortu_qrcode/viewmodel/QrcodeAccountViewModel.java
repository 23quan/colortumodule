package com.colortu.colortu_module.colortu_qrcode.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.bean.QrcodeUserInfoBean;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/6/2
 * @module : QrcodeAccountViewModel
 * @describe :账号切换界面ViewModel
 */
public class QrcodeAccountViewModel extends BaseActivityViewModel<BaseRequest> {
    //用户信息接口请求
    private Call<QrcodeUserInfoBean> userInfoBeanCall;

    //账号列表数据
    public MutableLiveData<List<QrcodeUserInfoBean.DataBean.UserListBean>> userListLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        getUserInfo(GetBeanDate.getUserUuid());
    }

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
                if (EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.listIsEmpty(response.body().getData().getUserList())) {
                        userListLiveData.setValue(response.body().getData().getUserList());
                    } else {
                        TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.no_account));
                    }
                }
            }

            @Override
            public void onFailure(Call<QrcodeUserInfoBean> call, Throwable t) {//请求失败
            }
        });
    }
}
