package com.colortu.colortu_module.colortu_teach.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_teach.viewmodel.TeachBookViewModel;
import com.colortu.colortu_module.databinding.ActivityTeachBookBinding;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachBookActivity
 * @describe :选择教辅系列界面
 */
@Route(path = BaseConstant.TEACH_BOOK)
public class TeachBookActivity extends BaseActivity<TeachBookViewModel, ActivityTeachBookBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_teach_book;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.bookParentview);

        BaseApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().finishActivity(TeachBookActivity.class);
    }
}
