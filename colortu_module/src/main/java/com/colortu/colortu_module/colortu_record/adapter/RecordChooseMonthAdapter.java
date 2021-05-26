package com.colortu.colortu_module.colortu_record.adapter;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.databinding.AdapterRecordMonthsBinding;

/**
 * @author : Code23
 * @time : 2020/12/17
 * @module : RecordChooseMonthAdapter
 * @describe :月份列表适配器
 */
public class RecordChooseMonthAdapter extends BaseRecyclerAdapter<String> {
    @Override
    public int getLayoutId() {
        return R.layout.adapter_record_months;
    }

    @Override
    public void bindView(ViewDataBinding binding, final String item, int position) {
        AdapterRecordMonthsBinding adapterRecordMonthsBinding = (AdapterRecordMonthsBinding) binding;
        //月份
        adapterRecordMonthsBinding.choosedateMonth.setText(item + "月");

        /**
         * 选择月份
         */
        adapterRecordMonthsBinding.choosedateMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChooseMonthListener.OnClickChooseMonth(item);
            }
        });
    }

    public void setOnClickChooseMonthListener(OnClickChooseMonthListener onClickChooseMonthListener) {
        this.onClickChooseMonthListener = onClickChooseMonthListener;
    }

    private OnClickChooseMonthListener onClickChooseMonthListener;

    public interface OnClickChooseMonthListener {
        void OnClickChooseMonth(String month);
    }
}
