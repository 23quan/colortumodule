package com.colortu.colortu_module.colortu_record.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_record.adapter.RecordChooseDateAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordChooseDateBean;
import com.colortu.colortu_module.colortu_record.viewmodel.RecordChooseDateViewModel;
import com.colortu.colortu_module.databinding.ActivityRecordChoosedateBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordChooseDateActivity
 * @describe :选择日期界面
 */
@Route(path = BaseConstant.RECORD_CHOOSEDATE)
public class RecordChooseDateActivity extends BaseActivity<RecordChooseDateViewModel, ActivityRecordChoosedateBinding> {
    //选择日期列表适配器
    private RecordChooseDateAdapter recordChooseDateAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_record_choosedate;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.choosedateParentview);

        //选择日期列表适配器实例化
        recordChooseDateAdapter = new RecordChooseDateAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.choosedateDatelist.setNestedScrollingEnabled(false);
        binding.choosedateDatelist.setLayoutManager(linearLayoutManager);
        binding.choosedateDatelist.setAdapter(recordChooseDateAdapter);

        //选择日期
        recordChooseDateAdapter.setOnClickChooseDateListener(new RecordChooseDateAdapter.OnClickChooseDateListener() {
            @Override
            public void OnClickChoose(String year, String month) {
                Intent intent = new Intent();
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                setResult(BaseConstant.REQUEDT_DATECODE, intent);
                finish();
            }
        });

        //选择日期列表数据监听
        viewModel.recordChooseDateLiveData.observe(this, new Observer<List<RecordChooseDateBean>>() {
            @Override
            public void onChanged(List<RecordChooseDateBean> recordChooseDateBeans) {
                //选择日期列表数据刷新
                recordChooseDateAdapter.clear();
                if (EmptyUtils.listIsEmpty(recordChooseDateBeans)) {
                    recordChooseDateAdapter.addAll(recordChooseDateBeans);
                }
                recordChooseDateAdapter.notifyDataSetChanged();
            }
        });
    }
}
