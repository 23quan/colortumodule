package com.colortu.colortu_module.colortu_mine.adapter;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.utils.Tools;
import com.colortu.colortu_module.colortu_base.bean.MineVitalityEventBean;
import com.colortu.colortu_module.databinding.AdapterMineVitalityeventBinding;

/**
 * @author : Code23
 * @time : 2020/12/7
 * @module : MineVitalityEventAdapter
 * @describe :元气活动列表适配器
 */
public class MineVitalityEventAdapter extends BaseRecyclerAdapter<MineVitalityEventBean.DataBeanX.DataBean> {
    @Override
    public int getLayoutId() {
        return R.layout.adapter_mine_vitalityevent;
    }

    @Override
    public void bindView(ViewDataBinding binding, MineVitalityEventBean.DataBeanX.DataBean item, int position) {
        AdapterMineVitalityeventBinding adapterMineVitalityeventBinding = (AdapterMineVitalityeventBinding) binding;
        adapterMineVitalityeventBinding.vitalityeventTime.setText(Tools.stampToDateS(String.valueOf(item.getCreateTime() * 1000L), "yyyy-MM-dd"));
        adapterMineVitalityeventBinding.vitalityeventContent.setText(item.getRemarks());
    }
}
