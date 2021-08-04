package com.colortu.colortu_module.colortu_base.core.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.NetWorkViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.databinding.ActivityBaseNetworkBinding;

/**
 * @author : Code23
 * @time : 2021/5/20
 * @module : NetWorkActivity
 * @describe :WiFi切换移动流量界面
 */
@Route(path = BaseConstant.BASE_NETWORK)
public class NetWorkActivity extends BaseActivity<NetWorkViewModel, ActivityBaseNetworkBinding> {
    private int status = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_network;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.networkParentview);

        //不同意
        binding.networkDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 1;
                GetBeanDate.putAgreeNetWork(false);
                finishAffinity();
                BaseApplication.getInstance().exitApp();
            }
        });

        //同意
        binding.networkAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 2;
                GetBeanDate.putAgreeNetWork(true);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (status == 0) {
            GetBeanDate.putAgreeNetWork(false);
            finishAffinity();
            BaseApplication.getInstance().exitApp();
        }
    }
}
