package com.colortu.colortu_module.colortu_qrcode.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.bean.QrcodeAddUserBean;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_qrcode.viewmodel.QrcodeTextTipViewModel;
import com.colortu.colortu_module.databinding.ActivityQrcodeTexttipBinding;

/**
 * @author : Code23
 * @time : 2021/3/26
 * @module : QrcodeTextTipActivity
 * @describe :文字提示界面
 */
@Route(path = BaseConstant.QRCODE_TEXTTIP)
public class QrcodeTextTipActivity extends BaseActivity<QrcodeTextTipViewModel, ActivityQrcodeTexttipBinding> {
    //跳转至页面的类名
    @Autowired
    public String toPage;
    //跳转至页面的路由
    @Autowired
    public String toPageRoute;
    //销毁后跳转界面路由
    @Autowired
    public String afterPageRoute;
    //1.不跳转(a界面跳转b界面,b界面销毁回到a界面),2.跳转(a界面跳转b界面,b界面销毁跳转c界面),3.跳转(a界面跳转b界面)
    @Autowired
    public int type;
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //控制二维码数据
    private QrcodeAddUserBean.DataBean.ChannelConfigBean.PageInsertConfigBean pageInsertConfigBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode_texttip;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.texttipParentview);

        initData();
    }

    public void initData() {
        pageInsertConfigBean = BaseApplication.getPageInsertConfigBean();

        if (pageInsertConfigBean != null) {
            //文字提示信息
            viewModel.codetip1.set(pageInsertConfigBean.getPrompt1());
            viewModel.codetip2.set(pageInsertConfigBean.getText());
            viewModel.codetip3.set(pageInsertConfigBean.getPrompt2());
            //跳过提示信息
            viewModel.skiptip.set(pageInsertConfigBean.getOkButtonText());
            //1显示,0不显示
            if (pageInsertConfigBean.getShowOkButton() == 0) {
                binding.texttipSkip.setVisibility(View.GONE);
            }
        } else {
            //跳过提示信息
            viewModel.skiptip.set(getResources().getString(R.string.skiptip));
        }

        //跳过按钮事件监听
        binding.texttipSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onJumpOther();
            }
        });
    }

    /**
     * 跳转其他界面
     */
    public void onJumpOther() {
        switch (type) {
            case BaseUIKit.HAVE://跳转(a界面跳转b界面,b界面销毁跳转c界面)
                BaseUIKit.startActivity(UIKitName.QRCODE_TEXTTIP, "", afterPageRoute, BaseUIKit.HAVE, bundle);
                break;
            case BaseUIKit.OTHER://跳转(a界面跳转b界面)
                BaseUIKit.startActivity(UIKitName.QRCODE_TEXTTIP, toPage, toPageRoute, BaseUIKit.OTHER, bundle);
                break;
        }
        finish();
    }
}
