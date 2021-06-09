package com.colortu.colortu_module.colortu_qrcode.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.bean.QrcodeUserInfoBean;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.Tools;
import com.colortu.colortu_module.colortu_qrcode.viewmodel.QrcodeAccountDetailViewModel;
import com.colortu.colortu_module.databinding.ActivityQrcodeAccountdetailBinding;

/**
 * @author : Code23
 * @time : 2021/6/2
 * @module : QrcodeAccountDetailActivity
 * @describe :账号详情界面
 */
@Route(path = BaseConstant.QRCODE_ACCOUNTDETAIL)
public class QrcodeAccountDetailActivity extends BaseActivity<QrcodeAccountDetailViewModel, ActivityQrcodeAccountdetailBinding> {
    private QrcodeUserInfoBean.DataBean userInfoBean;//用户信息bean
    private String userinfojson;//用户信息json

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode_accountdetail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.accountdetailParentview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取数据
        userinfojson = GetBeanDate.getUserInfo();
        //初始化用户信息
        if (EmptyUtils.stringIsEmpty(userinfojson)) {
            userInfoBean = JSONObject.parseObject(userinfojson, QrcodeUserInfoBean.DataBean.class);
            if (EmptyUtils.objectIsEmpty(userInfoBean)) {
                Glide.with(this).load(userInfoBean.getUserInfo().getAvatar()).into(binding.accountdetailHead);
                binding.accountdetailName.setText(userInfoBean.getUserInfo().getNickName());
            }
        }
        //uuid
        binding.accountdetailUuid.setText("uuid:" + GetBeanDate.getUserUuid());
        //openid
        binding.accountdetailOpenid.setText("openid:" + GetBeanDate.getOpenid());
        //版本号
        binding.accountdetailVersion.setText("版本号:" + Tools.getVersionCode(this));
    }
}
