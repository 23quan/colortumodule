package com.colortu.colortu_module.colortu_study.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyIntegralViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyIntegralBinding;

/**
 * @author : Code23
 * @time : 2021/4/15
 * @module : StudyIntegralActivity
 * @describe :自习室积分界面
 */
@Route(path = BaseConstant.STUDY_INTEGRAL)
public class StudyIntegralActivity extends BaseActivity<StudyIntegralViewModel, ActivityStudyIntegralBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_integral;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.integralParentview);

        binding.integralContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        binding.integralContent.setText(getResources().getString(R.string.studyroom_integralrule));
    }
}
