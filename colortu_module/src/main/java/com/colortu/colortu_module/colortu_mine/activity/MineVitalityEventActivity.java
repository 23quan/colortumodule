package com.colortu.colortu_module.colortu_mine.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseActivity;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_mine.adapter.MineVitalityEventAdapter;
import com.colortu.colortu_module.colortu_base.bean.MineVitalityEventBean;
import com.colortu.colortu_module.colortu_mine.viewmodel.MineVitalityEventViewModel;
import com.colortu.colortu_module.databinding.ActivityMineVitalityeventBinding;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/7
 * @module : MineVitalityEventActivity
 * @describe :元气活动界面
 */
@Route(path = BaseConstant.MINE_VITALITYEVENT)
public class MineVitalityEventActivity extends BaseActivity<MineVitalityEventViewModel, ActivityMineVitalityeventBinding> {
    //元气活动列表适配器
    private MineVitalityEventAdapter mineVitalityEventAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_vitalityevent;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //适配圆角水滴屏或刘海屏
        viewModel.setAdapteScreen(binding.vitalityeventParentview);

        //元气活动列表适配器实例化
        mineVitalityEventAdapter = new MineVitalityEventAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.vitalityeventList.setLayoutManager(linearLayoutManager);
        binding.vitalityeventList.setAdapter(mineVitalityEventAdapter);

        /**
         * 元气活动列表数据监听
         */
        viewModel.vitalityEventLiveData.observe(this, new Observer<List<MineVitalityEventBean.DataBeanX.DataBean>>() {
            @Override
            public void onChanged(List<MineVitalityEventBean.DataBeanX.DataBean> dataBeans) {
                //元气活动列表数据刷新
                mineVitalityEventAdapter.clear();
                if (EmptyUtils.listIsEmpty(dataBeans)) {
                    mineVitalityEventAdapter.addAll(dataBeans);
                }
                mineVitalityEventAdapter.notifyDataSetChanged();
            }
        });
    }
}
