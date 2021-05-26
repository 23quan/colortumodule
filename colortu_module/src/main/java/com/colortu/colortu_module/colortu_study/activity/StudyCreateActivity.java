package com.colortu.colortu_module.colortu_study.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyCreateViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyCreateBinding;

/**
 * @author : Code23
 * @time : 2021/4/16
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

        /**
         * 清除输入名称
         */
        binding.createClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.createName.setText("");
            }
        });

        /**
         * 创建自习室
         */
        binding.createCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.createName.getText().toString().trim().equals("")) {
                    viewModel.getStudyCommand(GetBeanDate.getUserUuid(), binding.createName.getText().toString());
                } else {
                    TipToast.tipToastShort(getResources().getString(R.string.studyroom_namenone));
                }
            }
        });
    }
}
