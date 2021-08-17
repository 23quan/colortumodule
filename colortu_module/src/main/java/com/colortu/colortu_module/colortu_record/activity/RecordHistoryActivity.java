package com.colortu.colortu_module.colortu_record.activity;

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
import com.colortu.colortu_module.colortu_base.utils.QuantizeUtils;
import com.colortu.colortu_module.colortu_record.adapter.RecordHistoryAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordHistoryBean;
import com.colortu.colortu_module.colortu_record.viewmodel.RecordHistoryViewModel;
import com.colortu.colortu_module.databinding.ActivityRecordHistoryBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordHistoryActivity
 * @describe :作业历史界面
 */
@Route(path = BaseConstant.RECORD_HISTORY)
public class RecordHistoryActivity extends BaseActivity<RecordHistoryViewModel, ActivityRecordHistoryBinding> {
    //作业历史列表适配器
    private RecordHistoryAdapter recordHistoryAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_record_history;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.historyParentview);

        onChooseMonth();

        //作业历史列表适配器实例化
        recordHistoryAdapter = new RecordHistoryAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.historyList.setLayoutManager(linearLayoutManager);
        binding.historyList.setAdapter(recordHistoryAdapter);

        //作业历史列表item事件监听
        recordHistoryAdapter.setOnClickHistoryListener(new RecordHistoryAdapter.OnClickHistoryListener() {
            @Override
            public void OnClickHistory(String day, String date) {
                //跳转历史科目界面
                Bundle bundle = new Bundle();
                bundle.putString("datemonth", viewModel.yearObser.get() + viewModel.monthObser.get());
                bundle.putString("day", day);
                bundle.putString("date", date);
                BaseUIKit.startActivity(UIKitName.RECORD_HISTORY, UIKitName.RECORD_HISTORYSUBJECT,
                        BaseConstant.RECORD_HISTORYSUBJECT, BaseUIKit.OTHER, bundle);
            }
        });

        //作业历史列表数据监听
        viewModel.recordHistoryBeanLiveData.observe(this, new Observer<List<RecordHistoryBean.DataBean.RecordsBean>>() {
            @Override
            public void onChanged(List<RecordHistoryBean.DataBean.RecordsBean> recordsBeans) {
                //作业历史列表数据刷新
                recordHistoryAdapter.clear();
                if (EmptyUtils.listIsEmpty(recordsBeans)) {
                    binding.historyNohistorytip.setVisibility(View.GONE);
                    binding.historyList.setVisibility(View.VISIBLE);
                    recordHistoryAdapter.addAll(recordsBeans);
                } else {
                    binding.historyList.setVisibility(View.GONE);
                    binding.historyNohistorytip.setVisibility(View.VISIBLE);
                }
                recordHistoryAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BaseConstant.REQUEDT_DATECODE) {
            if (data != null) {
                String year = data.getStringExtra("year");
                String month = String.valueOf(QuantizeUtils.ChineseToNumber(data.getStringExtra("month")));

                viewModel.yearObser.set(year + "年");
                viewModel.monthObser.set(month + "月");
                viewModel.yearparameter.set(year);
                viewModel.monthparameter.set(month);
            }
        }
    }

    /**
     * 选择月份
     */
    public void onChooseMonth() {
        binding.historyChoosemonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转选择日期界面
                BaseUIKit.startActivity(UIKitName.RECORD_HISTORY, UIKitName.RECORD_CHOOSEDATE, BaseConstant.RECORD_CHOOSEDATE,
                        BaseUIKit.OTHER, null, RecordHistoryActivity.this, BaseConstant.REQUEDT_DATECODE);
            }
        });
    }
}
