package com.colortu.colortu_module.colortu_qrcode.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_qrcode.viewmodel.QrcodeHelpViewModel;
import com.colortu.colortu_module.databinding.ActivityQrcodeHelpBinding;

/**
 * @author : Code23
 * @time : 2021/1/11
 * @module : QrcodeHelpActivity
 * @describe :帮助界面
 */
@Route(path = BaseConstant.QRCODE_HELP)
public class QrcodeHelpActivity extends BaseActivity<QrcodeHelpViewModel, ActivityQrcodeHelpBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode_help;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.teachhelpParentview);

        binding.teachhelpTip.setMovementMethod(ScrollingMovementMethod.getInstance());

        Glide.with(QrcodeHelpActivity.this).load(R.drawable.base_img_loading).into(binding.teachhelpCodeimg);

        //二维码监听
        viewModel.codeimg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(QrcodeHelpActivity.this)
                        .load(s)
                        .apply(new RequestOptions().placeholder(R.drawable.base_img_loading))
                        .into(binding.teachhelpCodeimg);
            }
        });
    }
}
