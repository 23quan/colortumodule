package com.colortu.colortu_module.colortu_study.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_study.adapter.StudyPlazaFiltrateAreaAdapter;
import com.colortu.colortu_module.colortu_study.adapter.StudyPlazaFiltrateGradeAdapter;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyPlazaFiltrateViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyPlazafiltrateBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/4/8
 * @module : StudyPlazaFiltrateActivity
 * @describe :自习广场列表筛选界面
 */
@Route(path = BaseConstant.STUDY_PLAZAFILTRATE)
public class StudyPlazaFiltrateActivity extends BaseActivity<StudyPlazaFiltrateViewModel, ActivityStudyPlazafiltrateBinding> {
    //筛选关键字
    private String filtrate;
    //区域数据列表适配器
    private StudyPlazaFiltrateAreaAdapter studyPlazaFiltrateAreaAdapter;
    //年级数据列表适配器
    private StudyPlazaFiltrateGradeAdapter studyPlazaFiltrateGradeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_plazafiltrate;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.plazafiltrateParentview);

        //获取储存筛选关键字
        filtrate = GetBeanDate.getStudyFiltrateName();

        //区域数据列表适配器实例化
        studyPlazaFiltrateAreaAdapter = new StudyPlazaFiltrateAreaAdapter(this, filtrate);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2);
        gridLayoutManager1.setOrientation(GridLayoutManager.VERTICAL);
        binding.plazafiltrateAreaist.setLayoutManager(gridLayoutManager1);
        binding.plazafiltrateAreaist.setAdapter(studyPlazaFiltrateAreaAdapter);

        /**
         * 区域界面显示监听
         */
        viewModel.areaShow.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//显示
                    binding.plazafiltrateArea.setVisibility(View.VISIBLE);
                    binding.plazafiltrateAreaist.setVisibility(View.VISIBLE);
                } else {//隐藏
                    binding.plazafiltrateArea.setVisibility(View.GONE);
                    binding.plazafiltrateAreaist.setVisibility(View.GONE);
                }
            }
        });

        /**
         * 区域item选择监听
         */
        studyPlazaFiltrateAreaAdapter.setOnClickFiltrateAreaListener(new StudyPlazaFiltrateAreaAdapter.OnClickFiltrateAreaListener() {
            @Override
            public void OnClickFiltrateArea(String item) {
                GetBeanDate.putStudyFiltrateName(item);
                setResult(BaseConstant.REQUEDT_STUDYFILTRATE);
                finish();
            }
        });

        /**
         * 区域数据列表监听
         */
        viewModel.areaLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                //区域数据列表刷新
                studyPlazaFiltrateAreaAdapter.clear();
                studyPlazaFiltrateAreaAdapter.addAll(strings);
                studyPlazaFiltrateAreaAdapter.notifyDataSetChanged();
            }
        });

        //年级数据列表适配器实例化
        studyPlazaFiltrateGradeAdapter = new StudyPlazaFiltrateGradeAdapter(this, filtrate);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 2);
        gridLayoutManager2.setOrientation(GridLayoutManager.VERTICAL);
        binding.plazafiltrateGradelist.setLayoutManager(gridLayoutManager2);
        binding.plazafiltrateGradelist.setAdapter(studyPlazaFiltrateGradeAdapter);

        /**
         * 年级界面显示监听
         */
        viewModel.gradeShow.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {//显示
                    binding.plazafiltrateGrade.setVisibility(View.VISIBLE);
                    binding.plazafiltrateGradelist.setVisibility(View.VISIBLE);
                } else {//隐藏
                    binding.plazafiltrateGrade.setVisibility(View.GONE);
                    binding.plazafiltrateGradelist.setVisibility(View.GONE);
                }
            }
        });

        /**
         * 年级item选择监听
         */
        studyPlazaFiltrateGradeAdapter.setOnClickFiltrateGradeListener(new StudyPlazaFiltrateGradeAdapter.OnClickFiltrateGradeListener() {
            @Override
            public void OnClickFiltrateGrade(String item) {
                GetBeanDate.putStudyFiltrateName(item);
                setResult(BaseConstant.REQUEDT_STUDYFILTRATE);
                finish();
            }
        });

        /**
         * 年级数据列表监听
         */
        viewModel.gradeLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                //年级数据列表刷新
                studyPlazaFiltrateGradeAdapter.clear();
                studyPlazaFiltrateGradeAdapter.addAll(strings);
                studyPlazaFiltrateGradeAdapter.notifyDataSetChanged();
            }
        });
    }
}
