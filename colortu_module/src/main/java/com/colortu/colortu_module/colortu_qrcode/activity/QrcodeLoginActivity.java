package com.colortu.colortu_module.colortu_qrcode.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.QrcodeAddUserBean;
import com.colortu.colortu_module.colortu_qrcode.viewmodel.QrcodeLoginViewModel;
import com.colortu.colortu_module.databinding.ActivityQrcodeLoginBinding;

import java.io.File;
import java.security.PublicKey;

/**
 * @author : Code23
 * @time : 2020/11/17
 * @name : QrcodeLoginActivity
 * @Parameters :
 * @describe : 登录界面
 */
@Route(path = BaseConstant.QRCODE_LOGIN)
public class QrcodeLoginActivity extends BaseActivity<QrcodeLoginViewModel, ActivityQrcodeLoginBinding> {
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
        return R.layout.activity_qrcode_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.loginParentview);

        initData();
    }

    public void initData() {
        pageInsertConfigBean = BaseApplication.getPageInsertConfigBean();

        if (pageInsertConfigBean != null) {
            //文字提示信息
            if (EmptyUtils.stringIsEmpty(pageInsertConfigBean.getPrompt1())) {
                viewModel.codetip1.set(pageInsertConfigBean.getPrompt1());
                binding.loginTip1.setVisibility(View.VISIBLE);
            }

            if (EmptyUtils.stringIsEmpty(pageInsertConfigBean.getPrompt2())) {
                viewModel.codetip2.set(pageInsertConfigBean.getPrompt2());
                binding.loginTip2.setVisibility(View.VISIBLE);
            }

            if (EmptyUtils.stringIsEmpty(pageInsertConfigBean.getPrompt3())) {
                viewModel.codetip3.set(pageInsertConfigBean.getPrompt3());
                binding.loginTip3.setVisibility(View.VISIBLE);
            }

            //跳过提示信息
            viewModel.skiptip.set(pageInsertConfigBean.getOkButtonText());
            //1显示,0不显示
            if (pageInsertConfigBean.getShowOkButton() == 0) {
                binding.loginSkip.setVisibility(View.GONE);
            }

            if (!EmptyUtils.stringIsEmpty(GetBeanDate.getLoginCode())) {
                Glide.with(QrcodeLoginActivity.this).load(R.drawable.base_img_loading).into(binding.loginCodeimg);
            }
            viewModel.onRequestLogin();
        } else {
            //文字提示信息
            viewModel.codetip1.set(BaseApplication.getInstance().getString(R.string.login_scancode));
            binding.loginTip1.setVisibility(View.VISIBLE);
            viewModel.skiptip.set(BaseApplication.getInstance().getString(R.string.skiptip));
            viewModel.onRequestLogin();
        }

        //跳过按钮事件监听
        binding.loginSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onJumpOther();
            }
        });

        /**
         * 登录二维码监听
         */
        viewModel.codeimg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (viewModel.iscache.get()) {
                    Glide.with(QrcodeLoginActivity.this)
                            .load(new File(s))
                            .apply(new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                            .into(binding.loginCodeimg);
                } else {
                    Glide.with(QrcodeLoginActivity.this)
                            .load(s)
                            .apply(new RequestOptions().placeholder(R.drawable.base_img_loading))
                            .into(binding.loginCodeimg);
                }
            }
        });

        /**
         * 监听登录成功
         */
        viewModel.isLoginLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    ARouter.getInstance()
                            .build(BaseConstant.QRCODE_ACCOUNT)
                            .withString("toPage", toPage)
                            .withString("toPageRoute", toPageRoute)
                            .withString("afterPageRoute", afterPageRoute)
                            .withInt("type", type)
                            .withBundle("bundle", bundle)
                            .navigation();
                    finish();
                } else {
                    onJumpOther();
                }
            }
        });
    }

    /**
     * 跳转其他界面
     */
    public void onJumpOther() {
        switch (type) {
            case BaseUIKit.HAVE://跳转(a界面跳转b界面,b界面销毁跳转c界面)
                BaseUIKit.startActivity(UIKitName.QRCODE_LOGIN, "", afterPageRoute, BaseUIKit.HAVE, bundle);
                break;
            case BaseUIKit.OTHER://跳转(a界面跳转b界面)
                BaseUIKit.startActivity(UIKitName.QRCODE_LOGIN, toPage, toPageRoute, BaseUIKit.OTHER, bundle);
                break;
        }
        finish();
    }
}