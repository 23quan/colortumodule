package com.colortu.colortu_module.colortu_record.activity;

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
import com.colortu.colortu_module.colortu_record.adapter.RecordSubjectAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectBean;
import com.colortu.colortu_module.colortu_record.viewmodel.RecordSubjectViewModel;
import com.colortu.colortu_module.databinding.ActivityRecordSubjectBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/11/27
 * @module : RecordSubjectActivity
 * @describe :今日作业界面
 */
@Route(path = BaseConstant.RECORD_SUBJECT)
public class RecordSubjectActivity extends BaseActivity<RecordSubjectViewModel, ActivityRecordSubjectBinding> implements DialogWhether.OnWhetherListener {
    //是否弹窗
    public DialogWhether dialogWhether;
    //今日作业列表适配器
    private RecordSubjectAdapter recordSubjectAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_record_subject;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.recordsubjectParentview);

        //今日作业列表适配器实例化
        recordSubjectAdapter = new RecordSubjectAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recordsubjectList.setLayoutManager(linearLayoutManager);
        binding.recordsubjectList.setAdapter(recordSubjectAdapter);

        //今日作业列表item事件监听
        recordSubjectAdapter.setOnClickListener(new RecordSubjectAdapter.OnClickListener() {
            @Override
            public void OnClickIsFinish(RecordSubjectBean.DataBean.RecordsBean recordsBean) {
                //是否完成弹窗
                viewModel.subjectId.set(recordsBean.getSubjectId());
                dialogWhether = new DialogWhether(RecordSubjectActivity.this);
                dialogWhether.setOnWhetherListener(RecordSubjectActivity.this);
                if (recordsBean.getIsCompleted() == 0) {//0设置已完成
                    String finish = getResources().getString(R.string.sign_sure) + recordsBean.getSubjectName() +
                            getResources().getString(R.string.sign_finish);
                    dialogWhether.setContent(finish);
                } else {//1设置未完成
                    String unfinished = getResources().getString(R.string.sign_sure2) + recordsBean.getSubjectName() +
                            getResources().getString(R.string.sign_unfinished);
                    dialogWhether.setContent(unfinished);
                }
                dialogWhether.show();
            }
        });

        //今日作业列表数据监听
        viewModel.recordSubjectBeanLiveData.observe(this, new Observer<List<RecordSubjectBean.DataBean.RecordsBean>>() {
            @Override
            public void onChanged(List<RecordSubjectBean.DataBean.RecordsBean> recordsBeans) {
                //今日作业列表数据刷新
                recordSubjectAdapter.clear();
                if (EmptyUtils.listIsEmpty(recordsBeans)) {
                    binding.recordsubjectNone.setVisibility(View.GONE);
                    recordSubjectAdapter.addAll(recordsBeans);
                } else {
                    binding.recordsubjectNone.setVisibility(View.VISIBLE);
                }
                recordSubjectAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 弹窗否监听
     */
    @Override
    public void onNo() {
        dialogWhether.dismiss();
    }

    /**
     * 弹窗是监听
     */
    @Override
    public void onYes() {
        viewModel.markFinishRunnable();
        dialogWhether.dismiss();
    }
}
