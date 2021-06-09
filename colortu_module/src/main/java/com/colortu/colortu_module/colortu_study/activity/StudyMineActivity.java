package com.colortu.colortu_module.colortu_study.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.dialog.DialogWhether;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_study.adapter.StudyMineCreateAdapter;
import com.colortu.colortu_module.colortu_study.adapter.StudyMineJoinAdapter;
import com.colortu.colortu_module.colortu_study.adapter.StudyMinePastAdapter;
import com.colortu.colortu_module.colortu_base.bean.StudyMineBean;
import com.colortu.colortu_module.colortu_study.viewmodel.StudyMineViewModel;
import com.colortu.colortu_module.databinding.ActivityStudyMineBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2021/4/8
 * @module : StudyMineActivity
 * @describe :我的自习室界面
 */
@Route(path = BaseConstant.STUDY_MINE)
public class StudyMineActivity extends BaseActivity<StudyMineViewModel, ActivityStudyMineBinding> implements DialogWhether.OnWhetherListener {
    //0关闭,1开启
    private int open;
    //自习室id
    private int roomid;
    //是否弹窗
    private DialogWhether dialogWhether;
    //我创建的列表适配器
    private StudyMineCreateAdapter studyMineCreateAdapter;
    //我加入的列表适配器
    private StudyMineJoinAdapter studyMineJoinAdapter;
    //已过期的列表适配器
    private StudyMinePastAdapter studyMinePastAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_mine;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.mineParentview);

        /**
         * false无数据,true有数据
         */
        viewModel.isshow.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.mineNoneview.setVisibility(View.GONE);
                    binding.mineHaveview.setVisibility(View.VISIBLE);
                } else {
                    binding.mineHaveview.setVisibility(View.GONE);
                    binding.mineNoneview.setVisibility(View.VISIBLE);
                }
            }
        });

        //我创建的列表适配器实例化
        studyMineCreateAdapter = new StudyMineCreateAdapter(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.mineCreatelist.setLayoutManager(linearLayoutManager1);
        binding.mineCreatelist.setAdapter(studyMineCreateAdapter);

        /**
         * 我创建的列表数据监听
         */
        viewModel.myCreateBeanLiveData.observe(this, new Observer<List<StudyMineBean.DataBean.MyCreateBean>>() {
            @Override
            public void onChanged(List<StudyMineBean.DataBean.MyCreateBean> myCreateBeans) {
                //我创建的列表数据刷新
                studyMineCreateAdapter.clear();
                if (EmptyUtils.listIsEmpty(myCreateBeans)) {
                    binding.mineCreatetext.setVisibility(View.VISIBLE);
                    binding.mineCreatelist.setVisibility(View.VISIBLE);
                    studyMineCreateAdapter.addAll(myCreateBeans);
                } else {
                    binding.mineCreatetext.setVisibility(View.GONE);
                    binding.mineCreatelist.setVisibility(View.GONE);
                }
                studyMineCreateAdapter.notifyDataSetChanged();
            }
        });

        /**
         * 自习室开关监听
         */
        studyMineCreateAdapter.setOnClickSwitchListener(new StudyMineCreateAdapter.OnClickSwitchListener() {
            @Override
            public void OnClickSwitch(int isOpen, int id) {
                //0关闭,1开启
                open = isOpen;
                roomid = id;
                dialogWhether = new DialogWhether(StudyMineActivity.this);
                dialogWhether.setOnWhetherListener(StudyMineActivity.this);
                if (isOpen == 0) {
                    dialogWhether.setContent(getResources().getString(R.string.studyroom_openmessage));
                    dialogWhether.show();
                } else {
                    dialogWhether.setContent(getResources().getString(R.string.studyroom_closedmessage));
                    dialogWhether.show();
                }
            }
        });

        //我加入的列表适配器实例化
        studyMineJoinAdapter = new StudyMineJoinAdapter(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.mineJoinlist.setLayoutManager(linearLayoutManager2);
        binding.mineJoinlist.setAdapter(studyMineJoinAdapter);

        /**
         * 我加入的列表数据监听
         */
        viewModel.myInJoinBeanLiveData.observe(this, new Observer<List<StudyMineBean.DataBean.MyInJoinBean>>() {
            @Override
            public void onChanged(List<StudyMineBean.DataBean.MyInJoinBean> myInJoinBeans) {
                //我加入的列表数据刷新
                studyMineJoinAdapter.clear();
                if (EmptyUtils.listIsEmpty(myInJoinBeans)) {
                    binding.mineJointext.setVisibility(View.VISIBLE);
                    binding.mineJoinlist.setVisibility(View.VISIBLE);
                    studyMineJoinAdapter.addAll(myInJoinBeans);
                } else {
                    binding.mineJointext.setVisibility(View.GONE);
                    binding.mineJoinlist.setVisibility(View.GONE);
                }
                studyMineJoinAdapter.notifyDataSetChanged();
            }
        });

        //已过期的列表适配器实例化
        studyMinePastAdapter = new StudyMinePastAdapter(this);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.minePastlist.setLayoutManager(linearLayoutManager3);
        binding.minePastlist.setAdapter(studyMinePastAdapter);

        /**
         * 已过期的列表数据监听
         */
        viewModel.deprecatedBeaLiveData.observe(this, new Observer<List<StudyMineBean.DataBean.DeprecatedBean>>() {
            @Override
            public void onChanged(List<StudyMineBean.DataBean.DeprecatedBean> deprecatedBeans) {
                //已过期的列表数据刷新
                studyMinePastAdapter.clear();
                if (EmptyUtils.listIsEmpty(deprecatedBeans)) {
                    binding.minePasttext.setVisibility(View.VISIBLE);
                    binding.minePastlist.setVisibility(View.VISIBLE);
                    studyMinePastAdapter.addAll(deprecatedBeans);
                } else {
                    binding.minePasttext.setVisibility(View.GONE);
                    binding.minePastlist.setVisibility(View.GONE);
                }
                studyMinePastAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 弹窗否监听
     */
    @Override
    public void onNo() {
        dialogWhether.cancel();
    }

    /**
     * 弹窗是监听
     */
    @Override
    public void onYes() {
        //0关闭,1开启
        if (open == 0) {
            viewModel.getStudyOpen(roomid);
        } else {
            viewModel.getStudyClose(roomid);
        }
        dialogWhether.cancel();
    }
}
