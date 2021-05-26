package com.colortu.colortu_module.colortu_mine.viewmodel;

import android.os.Bundle;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.bean.MineVitalityBean;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/11/27
 * @module : MineMainViewModel
 * @describe :我的界面ViewModel
 */
public class MineMainViewModel extends BaseActivityViewModel<BaseRequest> {
    //查看当前元气值请求
    private Call<MineVitalityBean> mineVitalityBeanCall;

    //是否登录
    public MutableLiveData<Boolean> islogin = new MutableLiveData<>();
    //等级
    public ObservableField<String> level = new ObservableField<>();
    //元气值
    public ObservableField<String> vitality = new ObservableField<>();
    //勋章值
    public ObservableField<String> medalvalue = new ObservableField<>();
    //元气值
    public ObservableField<String> vitalitys = new ObservableField<>();

    @Override
    protected void onResume() {
        super.onResume();
        level.set("Lv1");

        islogin.setValue(GetBeanDate.getIsLogin());
        if (islogin.getValue()) {
            vitality.set("元气");
            medalvalue.set("勋章");
            getVitality(GetBeanDate.getUserUuid());
        } else {
            vitality.set("0元气");
            medalvalue.set("0勋章");
        }
    }

    /**
     * 跳转勋章界面
     */
    public void onJumpMedal() {
        BaseUIKit.startActivity(UIKitName.MINE_MAIN, UIKitName.MINE_MEDAL,
                BaseConstant.MINE_MEDAL, BaseUIKit.OTHER, null);
    }

    /**
     * 跳转元气界面
     */
    public void onJumpVitality() {
        Bundle bundle = new Bundle();
        bundle.putString("vitality", vitalitys.get());
        BaseUIKit.startActivity(UIKitName.MINE_MAIN, UIKitName.MINE_VITALITY,
                BaseConstant.MINE_VITALITY, BaseUIKit.OTHER, bundle);
    }

    /**
     * 跳转小兔商城界面
     */
    public void onJumpShop() {
        BaseUIKit.startActivity(UIKitName.MINE_MAIN, UIKitName.QRCODE_SHOP,
                BaseConstant.QRCODE_SHOP, BaseUIKit.OTHER, null);
    }

    /**
     * 用户协议
     */
    public void onAgreement() {
        Bundle bundle = new Bundle();
        bundle.putString("content", BaseApplication.getContext().getResources().getString(R.string.work_useragreement));
        BaseUIKit.startActivity(UIKitName.MINE_MAIN, UIKitName.BASE_AGREEMENT,
                BaseConstant.BASE_AGREEMENT, BaseUIKit.OTHER, bundle);
    }

    /**
     * 隐私政策
     */
    public void onPrivacy() {
        Bundle bundle = new Bundle();
        bundle.putString("content", BaseApplication.getContext().getResources().getString(R.string.work_privacypolicy));
        BaseUIKit.startActivity(UIKitName.MINE_MAIN, UIKitName.BASE_AGREEMENT,
                BaseConstant.BASE_AGREEMENT, BaseUIKit.OTHER, bundle);
    }

    /**
     * 查看当前元气值
     *
     * @param uuid
     */
    public void getVitality(String uuid) {
        mineVitalityBeanCall = iRequest.getvitality(BaseApplication.getHeaders(), uuid);
        mineVitalityBeanCall.enqueue(new Callback<MineVitalityBean>() {
            @Override
            public void onResponse(Call<MineVitalityBean> call, Response<MineVitalityBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.objectIsEmpty(response.body().getData().getData())) {
                        if (EmptyUtils.stringIsEmpty(response.body().getData().getData().getRemaining())) {
                            vitalitys.set(response.body().getData().getData().getRemaining());
                            vitality.set(response.body().getData().getData().getRemaining() + "元气");
                            medalvalue.set("0勋章");
                        } else {
                            vitalitys.set("0");
                            vitality.set("0元气");
                            medalvalue.set("0勋章");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MineVitalityBean> call, Throwable t) {//请求失败
            }
        });
    }
}
