package com.colortu.colortu_module.colortu_qrcode.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_qrcode.viewmodel.QrcodeShopViewModel;
import com.colortu.colortu_module.databinding.ActivityQrcodeShopBinding;

/**
 * @author : Code23
 * @time : 2021/1/11
 * @module : QrcodeShopActivity
 * @describe :小兔商城界面
 */
@Route(path = BaseConstant.QRCODE_SHOP)
public class QrcodeShopActivity extends BaseActivity<QrcodeShopViewModel, ActivityQrcodeShopBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode_shop;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.shopParentview);

        Glide.with(QrcodeShopActivity.this).load(R.drawable.base_img_loading).into(binding.shopCodeimg);

        //二维码监听
        viewModel.codeimg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(QrcodeShopActivity.this)
                        .load(s)
                        .apply(new RequestOptions().placeholder(R.drawable.base_img_loading))
                        .into(binding.shopCodeimg);
            }
        });
    }
}
