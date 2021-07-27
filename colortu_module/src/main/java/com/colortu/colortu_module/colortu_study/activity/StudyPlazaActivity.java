package com.colortu.colortu_module.colortu_study.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_study.adapter.StudyPlazaOfficialAdapter;
import com.colortu.colortu_module.colortu_study.adapter.StudyPlazaPersonAdapter;
import com.colortu.colortu_module.colortu_base.bean.StudyPlazaBean;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyPlazaViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyPlazaBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/4/8
 * @module : StudyPlazaActivity
 * @describe :自习广场列表界面
 */
@Route(path = BaseConstant.STUDY_PLAZA)
public class StudyPlazaActivity extends BaseActivity<StudyPlazaViewModel, ActivityStudyPlazaBinding> {
    //官方自习列表适配器
    private StudyPlazaOfficialAdapter studyPlazaOfficialAdapter;
    //个人自习列表适配器
    private StudyPlazaPersonAdapter studyPlazaPersonAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_plaza;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.plazaParentview);

        //官方自习列表适配器实例化
        studyPlazaOfficialAdapter = new StudyPlazaOfficialAdapter(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.plazaOfficiallist.setLayoutManager(linearLayoutManager1);
        binding.plazaOfficiallist.setAdapter(studyPlazaOfficialAdapter);

        //官方自习列表数据监听
        viewModel.officialLiveData.observe(this, new Observer<List<StudyPlazaBean.DataBean.OfficialBean>>() {
            @Override
            public void onChanged(List<StudyPlazaBean.DataBean.OfficialBean> officialBeans) {
                //官方自习列表数据刷新
                studyPlazaOfficialAdapter.clear();
                if (EmptyUtils.listIsEmpty(officialBeans)) {
                    binding.plazaOfficial.setVisibility(View.VISIBLE);
                    binding.plazaOfficiallist.setVisibility(View.VISIBLE);
                    studyPlazaOfficialAdapter.addAll(officialBeans);
                } else {
                    binding.plazaOfficial.setVisibility(View.GONE);
                    binding.plazaOfficiallist.setVisibility(View.GONE);
                }
                studyPlazaOfficialAdapter.notifyDataSetChanged();
            }
        });

        //个人自习列表适配器实例化
        studyPlazaPersonAdapter = new StudyPlazaPersonAdapter(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.plazaPersonlist.setLayoutManager(linearLayoutManager2);
        binding.plazaPersonlist.setAdapter(studyPlazaPersonAdapter);

       //个人自习列表数据监听
        viewModel.personLiveData.observe(this, new Observer<List<StudyPlazaBean.DataBean.PersonalBean>>() {
            @Override
            public void onChanged(List<StudyPlazaBean.DataBean.PersonalBean> personalBeans) {
                //个人自习列表数据刷新
                studyPlazaPersonAdapter.clear();
                if (EmptyUtils.listIsEmpty(personalBeans)) {
                    binding.plazaPerson.setVisibility(View.VISIBLE);
                    binding.plazaPersonlist.setVisibility(View.VISIBLE);
                    studyPlazaPersonAdapter.addAll(personalBeans);
                } else {
                    binding.plazaPerson.setVisibility(View.GONE);
                    binding.plazaPersonlist.setVisibility(View.GONE);
                }
                studyPlazaPersonAdapter.notifyDataSetChanged();
            }
        });

        //跳转自习广场列表筛选界面
        binding.plazaFiltrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseUIKit.startActivity(UIKitName.STUDY_PLAZA, UIKitName.STUDY_PLAZAFILTRATE, BaseConstant.STUDY_PLAZAFILTRATE,
                        BaseUIKit.OTHER, null, StudyPlazaActivity.this, BaseConstant.REQUEDT_STUDYFILTRATE);
            }
        });

        viewModel.initPlazaData();
    }

    /**
     * 数据回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BaseConstant.REQUEDT_STUDYFILTRATE) {
            viewModel.initPlazaData();
        }
    }
}
