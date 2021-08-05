package com.colortu.colortu_module.colortu_base.core.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.viewmodel.AgreementViewModel;
import com.colortu.colortu_module.colortu_base.core.viewmodel.ImageDetailViewModel;
import com.colortu.colortu_module.databinding.ActivityBaseAgreementBinding;
import com.colortu.colortu_module.databinding.ActivityBaseImagedetailBinding;

/**
 * @author : Code23
 * @time : 2021/5/11
 * @module : ImageDetailActivity
 * @describe :图片详情界面
 */
@Route(path = BaseConstant.BASE_IMAGEDETAIL)
public class ImageDetailActivity extends BaseActivity<ImageDetailViewModel, ActivityBaseImagedetailBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //图片路径
    private String imageurl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_imagedetail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.imagedetailParentview);

        imageurl = bundle.getString("imageurl");
        Glide.with(this).load(imageurl).into( binding.imagedetailPhotoview);

        binding.imagedetailPhotoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
