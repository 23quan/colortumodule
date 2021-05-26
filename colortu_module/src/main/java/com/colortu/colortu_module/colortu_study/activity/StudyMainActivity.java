package com.colortu.colortu_module.colortu_study.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyMainViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyMainBinding;

/**
 * @author : Code23
 * @time : 2021/3/31
 * @module : StudyMainActivity
 * @describe :自习室界面
 */
@Route(path = BaseConstant.STUDY_MAIN)
public class StudyMainActivity extends BaseActivity<StudyMainViewModel, ActivityStudyMainBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_study_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.studymainParentview);
    }
}
