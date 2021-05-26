package com.colortu.colortu_module.colortu_mine.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_mine.viewmodel.MineVitalityViewModel;
import com.colortu.colortu_module.databinding.ActivityMineVitalityBinding;

/**
 * @author : Code23
 * @time : 2020/12/7
 * @module : MineVitalityActivity
 * @describe :元气界面
 */
@Route(path = BaseConstant.MINE_VITALITY)
public class MineVitalityActivity extends BaseActivity<MineVitalityViewModel, ActivityMineVitalityBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //元气值
    private String vitality;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_vitality;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.vitalityParentview);

        vitality = bundle.getString("vitality");

        if (EmptyUtils.stringIsEmpty(vitality)) {
            viewModel.vitality.set(vitality);
        } else {
            viewModel.vitality.set("0");
        }
    }
}
