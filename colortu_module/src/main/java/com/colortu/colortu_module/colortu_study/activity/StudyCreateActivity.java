package com.colortu.colortu_module.colortu_study.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyCreateViewModel;
import com.colortu.colortu_module.colortu_teach.viewmodel.TeachBookViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyCreateBinding;
import com.colortu.colortu_module.databinding.ActivityTeachBookBinding;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : StudyCreateActivity
 * @describe :创建自习室界面
 */
@Route(path = BaseConstant.STUDY_CREATE)
public class StudyCreateActivity extends BaseActivity<StudyCreateViewModel, ActivityStudyCreateBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_study_create;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.createParentview);
    }
}
