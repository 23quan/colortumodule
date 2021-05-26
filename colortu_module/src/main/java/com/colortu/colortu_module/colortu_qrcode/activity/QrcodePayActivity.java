package com.colortu.colortu_module.colortu_qrcode.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.bean.QrcodeAddUserBean;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_qrcode.viewmodel.QrcodePayViewModel;
import com.colortu.colortu_module.databinding.ActivityQrcodePayBinding;

import java.io.File;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : QrcodePayActivity
 * @describe :支付界面
 */
@Route(path = BaseConstant.QRCODE_PAY)
public class QrcodePayActivity extends BaseActivity<QrcodePayViewModel, ActivityQrcodePayBinding> {
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

    //1其他,2我的自习室,3自习室时间
    private int status;
    //控制二维码数据
    private QrcodeAddUserBean.DataBean.ChannelConfigBean.PageInsertConfigBean pageInsertConfigBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode_pay;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.payParentview);

        initData();
    }

    public void initData() {
        status = bundle.getInt("status");
        pageInsertConfigBean = BaseApplication.getPageInsertConfigBean();

        if (pageInsertConfigBean != null) {
            //文字提示信息
            viewModel.codetip1.set(pageInsertConfigBean.getPrompt1());
            viewModel.codetip2.set(pageInsertConfigBean.getPrompt2());
            viewModel.codetip3.set(pageInsertConfigBean.getPrompt3());

            if (!EmptyUtils.stringIsEmpty(GetBeanDate.getPayCode())) {
                Glide.with(QrcodePayActivity.this).load(R.drawable.base_img_loading).into(binding.payCodeimg);
            }
            viewModel.onRequestPay();
        } else {
            //文字提示信息
            switch (status) {
                case 1://1其他
                    viewModel.codetip1.set(BaseApplication.getInstance().getString(R.string.pay_scancode));
                    break;
                case 2://2我的自习室
                    viewModel.codetip1.set(BaseApplication.getInstance().getString(R.string.update_study));
                    break;
            }
            viewModel.onRequestPay();
        }

        /**
         * 二维码监听
         */
        viewModel.codeimg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (viewModel.iscache.get()) {
                    Glide.with(QrcodePayActivity.this)
                            .load(new File(s))
                            .apply(new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                            .into(binding.payCodeimg);
                } else {
                    Glide.with(QrcodePayActivity.this)
                            .load(s)
                            .apply(new RequestOptions().placeholder(R.drawable.base_img_loading))
                            .into(binding.payCodeimg);
                }
            }
        });

        /**
         * 支付成功监听
         */
        viewModel.paysuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    onJumpOther(aBoolean);
                }
            }
        });
    }

    /**
     * 跳转其他界面
     */
    public void onJumpOther(boolean ispay) {
        switch (type) {
            case BaseUIKit.HAVE://跳转(a界面跳转b界面,b界面销毁跳转c界面)
                BaseUIKit.startActivity(UIKitName.QRCODE_PAY, "", afterPageRoute, BaseUIKit.HAVE, bundle);
                break;
            case BaseUIKit.OTHER://跳转(a界面跳转b界面)
                BaseUIKit.startActivity(UIKitName.QRCODE_PAY, toPage, toPageRoute, BaseUIKit.OTHER, bundle);
                break;
        }
        if (status == 3) {
            Intent intent = new Intent();
            intent.putExtra("ispay", ispay);
            setResult(BaseConstant.REQUEDT_STUDYTIME, intent);
        }
        finish();
    }
}
