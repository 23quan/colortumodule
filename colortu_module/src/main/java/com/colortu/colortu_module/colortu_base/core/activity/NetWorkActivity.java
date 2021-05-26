package com.colortu.colortu_module.colortu_base.core.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.viewmodel.NetWorkViewModel;
import com.colortu.colortu_module.databinding.ActivityBaseNetworkBinding;

/**
 * @author : Code23
 * @time : 2021/5/20
 * @module : NetWorkActivity
 * @describe :WiFi切换移动流量界面
 */
@Route(path = BaseConstant.BASE_NETWORK)
public class NetWorkActivity extends BaseActivity<NetWorkViewModel, ActivityBaseNetworkBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_base_network;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.networkParentview);
    }
}
