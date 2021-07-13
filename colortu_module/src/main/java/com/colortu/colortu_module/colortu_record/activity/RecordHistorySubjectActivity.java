package com.colortu.colortu_module.colortu_record.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.dialog.DialogWhether;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_record.adapter.RecordHistorySubjectAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectBean;
import com.colortu.colortu_module.colortu_record.viewmodel.RecordHistorySubjectViewModel;
import com.colortu.colortu_module.databinding.ActivityRecordHistorysubjectBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordHistorySubjectActivity
 * @describe :历史科目界面
 */
@Route(path = BaseConstant.RECORD_HISTORYSUBJECT)
public class RecordHistorySubjectActivity extends BaseActivity<RecordHistorySubjectViewModel, ActivityRecordHistorysubjectBinding> implements DialogWhether.OnWhetherListener {
    //bundle传递数据
    @Autowired
    public Bundle bundle;

    //年月
    private String datemonth;
    //日
    private String day;
    //日期
    private String date;
    //是否弹窗
    public DialogWhether dialogWhether;
    //历史科目列表适配器
    private RecordHistorySubjectAdapter recordHistorySubjectAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_record_historysubject;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.historysubjectParentview);

        datemonth = bundle.getString("datemonth");
        day = bundle.getString("day");
        date = bundle.getString("date");

        viewModel.datemonth.set(datemonth.substring(2, datemonth.length()));
        viewModel.day.set(day);
        viewModel.date.set(date);

        //历史科目列表适配器实例化
        recordHistorySubjectAdapter = new RecordHistorySubjectAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.historysubjectList.setLayoutManager(linearLayoutManager);
        binding.historysubjectList.setAdapter(recordHistorySubjectAdapter);

        /**
         * 历史课目item点击监听
         */
        recordHistorySubjectAdapter.setOnClickSubjectDetailListener(new RecordHistorySubjectAdapter.OnClickSubjectDetailListener() {
            @Override
            public void OnClickSubjectDetail(String subjectname, int subjectId) {
                //跳转历史科目详情界面
                Bundle bundle = new Bundle();
                bundle.putString("subjectname", subjectname);
                bundle.putInt("subjectId", subjectId);
                bundle.putString("date", date);
                BaseUIKit.startActivity(UIKitName.RECORD_HISTORYSUBJECT, UIKitName.RECORD_HISTORYSUBJECTDETAIL,
                        BaseConstant.RECORD_HISTORYSUBJECTDETAIL, BaseUIKit.OTHER, bundle);
            }

            @Override
            public void OnClickIsFinish(RecordSubjectBean.DataBean.RecordsBean recordsBean) {
                //是否完成弹窗
                dialogWhether = new DialogWhether(RecordHistorySubjectActivity.this);
                dialogWhether.setOnWhetherListener(RecordHistorySubjectActivity.this);
                viewModel.subjectId.set(recordsBean.getSubjectId());
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

        /**
         * 历史科目列表数据监听
         */
        viewModel.recordSubjectLiveData.observe(this, new Observer<List<RecordSubjectBean.DataBean.RecordsBean>>() {
            @Override
            public void onChanged(List<RecordSubjectBean.DataBean.RecordsBean> recordsBeans) {
                //历史科目列表数据刷新
                recordHistorySubjectAdapter.clear();
                if (EmptyUtils.listIsEmpty(recordsBeans)) {
                    recordHistorySubjectAdapter.addAll(recordsBeans);
                }
                recordHistorySubjectAdapter.notifyDataSetChanged();
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
