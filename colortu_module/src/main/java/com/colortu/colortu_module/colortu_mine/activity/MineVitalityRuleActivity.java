package com.colortu.colortu_module.colortu_mine.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_mine.viewmodel.MineVitalityRuleViewModel;
import com.colortu.colortu_module.databinding.ActivityMineVitalityruleBinding;

/**
 * @author : Code23
 * @time : 2020/12/7
 * @module : MineVitalityRuleActivity
 * @describe :元气规则界面
 */
@Route(path = BaseConstant.MINE_VITALITYRULE)
public class MineVitalityRuleActivity extends BaseActivity<MineVitalityRuleViewModel, ActivityMineVitalityruleBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_vitalityrule;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.vitalityruleParentview);

        binding.vitalityruleContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
