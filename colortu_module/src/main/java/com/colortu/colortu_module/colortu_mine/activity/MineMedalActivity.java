package com.colortu.colortu_module.colortu_mine.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_mine.viewmodel.MineMedalViewModel;
import com.colortu.colortu_module.databinding.ActivityMineMedalBinding;

/**
 * @author : Code23
 * @time : 2020/12/7
 * @module : MineMedalActivity
 * @describe :勋章界面
 */
@Route(path = BaseConstant.MINE_MEDAL)
public class MineMedalActivity extends BaseActivity<MineMedalViewModel, ActivityMineMedalBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_medal;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.medalParentview);
    }
}
