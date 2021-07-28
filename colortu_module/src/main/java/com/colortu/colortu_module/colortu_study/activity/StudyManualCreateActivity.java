package com.colortu.colortu_module.colortu_study.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyManualCreateViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyManualcreateBinding;

/**
 * @author : Code23
 * @time : 2021/4/16
 * @module : StudyManualCreateActivity
 * @describe :手动创建自习室界面
 */
@Route(path = BaseConstant.STUDY_MANUALCREATE)
public class StudyManualCreateActivity extends BaseActivity<StudyManualCreateViewModel, ActivityStudyManualcreateBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_study_manualcreate;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.manualcreateParentview);

        //清除输入名称
        binding.manualcreateClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.manualcreateName.setText("");
            }
        });

        //创建自习室
        binding.manualcreateCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.manualcreateName.getText().toString().trim().equals("")) {
                    viewModel.getStudyCommand(GetBeanDate.getUserUuid(), binding.manualcreateName.getText().toString());
                } else {
                    TipToast.tipToastShort(getResources().getString(R.string.studyroom_namenone));
                }
            }
        });
    }
}
