package com.colortu.colortu_module.colortu_base.core.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.viewmodel.RestTimesViewModel;
import com.colortu.colortu_module.databinding.ActivityBaseResttimesBinding;

/**
 * @author : Code23
 * @time : 2021/1/5
 * @module : RestTimeActvity
 * @describe :休息提示界面
 */
@Route(path = BaseConstant.BASE_RESTTIMES)
public class RestTimesActivity extends BaseActivity<RestTimesViewModel, ActivityBaseResttimesBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_base_resttimes;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.resttimesParentview);

        //退出
        binding.resttimesExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

        //继续
        binding.resttimesKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
