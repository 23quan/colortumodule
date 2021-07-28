package com.colortu.colortu_module.colortu_mine.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_mine.viewmodel.MineMainViewModel;
import com.colortu.colortu_module.databinding.ActivityMineMainBinding;

/**
 * @author : Code23
 * @time : 2020/12/7
 * @module : MineMainActivity
 * @describe :我的界面
 */
@Route(path = BaseConstant.MINE_MAIN)
public class MineMainActivity extends BaseActivity<MineMainViewModel, ActivityMineMainBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.minemainParentview);

        //判断是小兔作业or作业助手
        if (ChannelUtil.isSJTC()) {
            binding.minemainMedalvalue.setVisibility(View.GONE);
            binding.minemainVitalityshop.setVisibility(View.GONE);
        }

        //华为显示协议与政策
        if (ChannelUtil.isHuaWei()) {
            binding.minemainUseragreement.setVisibility(View.VISIBLE);
            binding.minemainPrivacypolicy.setVisibility(View.VISIBLE);
        }

        //是否登录监听
        viewModel.islogin.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//已登陆
                    Glide.with(MineMainActivity.this).load(GetBeanDate.getUserHead()).into(binding.minemainHeadimg);
                    binding.minemainLogin.setVisibility(View.INVISIBLE);
                } else {//未登录
                    Glide.with(MineMainActivity.this).load(R.mipmap.icon_work_logo1).into(binding.minemainHeadimg);
                    binding.minemainLogin.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}