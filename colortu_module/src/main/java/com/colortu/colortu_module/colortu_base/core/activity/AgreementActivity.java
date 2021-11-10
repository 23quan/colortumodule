package com.colortu.colortu_module.colortu_base.core.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.viewmodel.AgreementViewModel;
import com.colortu.colortu_module.databinding.ActivityBaseAgreementBinding;

/**
 * @author : Code23
 * @time : 2021/5/11
 * @module : AgreementActivity
 * @describe :隐私/用户协议界面
 */
@Route(path = BaseConstant.BASE_AGREEMENT)
public class AgreementActivity extends BaseActivity<AgreementViewModel, ActivityBaseAgreementBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //文本内容
    private String content;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_agreement;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.agreementParentview);

        content = bundle.getString("content");
        binding.agreementTextview.setText(Html.fromHtml(content));
        binding.agreementTextview.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
