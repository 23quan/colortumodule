package com.colortu.colortu_module.colortu_teach.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_teach.adapter.TeachGradeClassAdapter;
import com.colortu.colortu_module.colortu_base.bean.TeachGradeClassBean;
import com.colortu.colortu_module.colortu_teach.viewmodel.TeachGradeClassViewModel;
import com.colortu.colortu_module.databinding.ActivityTeachGradeclassBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachGradeClassActivity
 * @describe :年级课目界面
 */
@Route(path = BaseConstant.TEACH_GRADECLASS)
public class TeachGradeClassActivity extends BaseActivity<TeachGradeClassViewModel, ActivityTeachGradeclassBinding> {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //年级课名
    private String grade;
    //我的教辅id
    private int bookId;
    //年级课目列表适配器
    private TeachGradeClassAdapter teachGradeClassAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_teach_gradeclass;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.gradeclassParentview);

        grade = bundle.getString("grade");
        bookId = bundle.getInt("bookId");

        viewModel.gradename.set(grade);
        viewModel.bookId.set(bookId);

        int freecount = GetBeanDate.getFreeExamCount();//免费课数
        int isVip = GetBeanDate.getIsBookVIP();//是否会员

        //年级课目列表适配器实例化
        teachGradeClassAdapter = new TeachGradeClassAdapter(freecount, isVip);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.gradeclassList.setLayoutManager(linearLayoutManager);
        binding.gradeclassList.setAdapter(teachGradeClassAdapter);

        /**
         * 年级课列表数据监听
         */
        viewModel.teachGradeClassBeanLiveData.observe(this, new Observer<List<TeachGradeClassBean.DataBean.ListBeanX>>() {
            @Override
            public void onChanged(List<TeachGradeClassBean.DataBean.ListBeanX> listBeanXES) {
                //年级课目列表数据刷新
                teachGradeClassAdapter.clear();
                if (EmptyUtils.listIsEmpty(listBeanXES)) {
                    teachGradeClassAdapter.addAll(listBeanXES);
                }
                teachGradeClassAdapter.notifyDataSetChanged();
            }
        });
    }
}
