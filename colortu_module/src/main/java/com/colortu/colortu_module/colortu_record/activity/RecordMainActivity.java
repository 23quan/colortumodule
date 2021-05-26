package com.colortu.colortu_module.colortu_record.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_record.viewmodel.RecordMainViewModel;
import com.colortu.colortu_module.databinding.ActivityRecordMainBinding;

/**
 * @author : Code23
 * @time : 2021/3/31
 * @module : RecordMainActivity
 * @describe :作业记录界面
 */
@Route(path = BaseConstant.RECORD_MAIN)
public class RecordMainActivity extends BaseActivity<RecordMainViewModel, ActivityRecordMainBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_record_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.recordmainParentview);
    }
}
