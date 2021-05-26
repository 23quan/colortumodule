package com.colortu.colortu_module.colortu_qrcode.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.bean.QrcodeAddUserBean;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_qrcode.viewmodel.QrcodeADViewModel;
import com.colortu.colortu_module.databinding.ActivityQrcodeAdBinding;

/**
 * @author : Code23
 * @time : 2021/3/26
 * @module : QrcodeADActivity
 * @describe :广告界面
 */
@Route(path = BaseConstant.QRCODE_AD)
public class QrcodeADActivity extends BaseActivity<QrcodeADViewModel, ActivityQrcodeAdBinding> {
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

    //是否可切换图片
    private boolean isSwitch;
    //控制二维码数据
    private QrcodeAddUserBean.DataBean.ChannelConfigBean.PageInsertConfigBean pageInsertConfigBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode_ad;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.adParentview);

        initData();
    }

    public void initData() {
        pageInsertConfigBean = BaseApplication.getPageInsertConfigBean();

        if (pageInsertConfigBean != null) {
            //二维码
            if (EmptyUtils.stringIsEmpty(pageInsertConfigBean.getImage())) {
                isSwitch = false;
                Glide.with(this).load(pageInsertConfigBean.getImage()).into(binding.adCodeimg);
            } else if (EmptyUtils.stringIsEmpty(pageInsertConfigBean.getExtraPageImage())) {
                isSwitch = true;
                Glide.with(QrcodeADActivity.this).load(pageInsertConfigBean.getExtraPageImage()).into(binding.adCodeimg);
            }

            //文字提示
            viewModel.codetip1.set(pageInsertConfigBean.getPrompt1());
            viewModel.codetip2.set(pageInsertConfigBean.getPrompt2());
            viewModel.codetip3.set(pageInsertConfigBean.getPrompt3());

            //1显示,0不显示
            if (pageInsertConfigBean.getShowCancelButton() == 0 && pageInsertConfigBean.getShowOkButton() == 0) {
                binding.adMenuview.setVisibility(View.GONE);
            } else {
                //菜单文字提示
                viewModel.canceltip.set(pageInsertConfigBean.getCancelButtonText());
                viewModel.suretip.set(pageInsertConfigBean.getOkButtonText());

                if (pageInsertConfigBean.getShowCancelButton() == 0) {
                    binding.adCancel.setVisibility(View.GONE);
                }

                if (pageInsertConfigBean.getShowOkButton() == 0) {
                    binding.adSure.setVisibility(View.GONE);
                }
            }

            //二维码按钮
            binding.adCodeimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isSwitch) {
                        isSwitch = false;
                        if (EmptyUtils.stringIsEmpty(pageInsertConfigBean.getImage())) {
                            Glide.with(QrcodeADActivity.this).load(pageInsertConfigBean.getImage()).into(binding.adCodeimg);
                        }
                    } else {
                        isSwitch = true;
                        if (EmptyUtils.stringIsEmpty(pageInsertConfigBean.getExtraPageImage())) {
                            Glide.with(QrcodeADActivity.this).load(pageInsertConfigBean.getExtraPageImage()).into(binding.adCodeimg);
                        }
                    }
                }
            });

        } else {
            //二维码
            Glide.with(this).load(R.mipmap.icon_colortu_qrcode).into(binding.adCodeimg);
            //菜单文字提示
            viewModel.canceltip.set(getResources().getString(R.string.cancel));
            viewModel.suretip.set(getResources().getString(R.string.affirm));
        }

        //取消按钮监听
        binding.adCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onJumpOther();
            }
        });

        //确认按钮监听
        binding.adSure.setOnClickListener(new View.OnClickListener() {
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
                BaseUIKit.startActivity(UIKitName.QRCODE_AD, "", afterPageRoute, BaseUIKit.HAVE, bundle);
                break;
            case BaseUIKit.OTHER://跳转(a界面跳转b界面)
                BaseUIKit.startActivity(UIKitName.QRCODE_AD, toPage, toPageRoute, BaseUIKit.OTHER, bundle);
                break;
        }
        finish();
    }
}
