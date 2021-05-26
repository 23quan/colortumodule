package com.colortu.colortu_module.colortu_record.adapter;

import android.content.Context;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordChooseDateBean;
import com.colortu.colortu_module.databinding.AdapterRecordChoosedateBinding;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordChooseDateAdapter
 * @describe :选择日期列表适配器
 */
public class RecordChooseDateAdapter extends BaseRecyclerAdapter<RecordChooseDateBean> {
    //上下文
    private Context context;
    //月份列表适配器
    private RecordChooseMonthAdapter recordChooseMonthAdapter;

    public RecordChooseDateAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_record_choosedate;
    }

    @Override
    public void bindView(ViewDataBinding binding, final RecordChooseDateBean item, int position) {
        AdapterRecordChoosedateBinding adapterRecordChoosedateBinding = (AdapterRecordChoosedateBinding) binding;
        //年份
        adapterRecordChoosedateBinding.choosedateYear.setText(item.getYear() + "年");
        //月份列表适配器实例化
        recordChooseMonthAdapter = new RecordChooseMonthAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        adapterRecordChoosedateBinding.choosedateMonthlist.setLayoutManager(gridLayoutManager);
        adapterRecordChoosedateBinding.choosedateMonthlist.setAdapter(recordChooseMonthAdapter);
        //月份列表数据刷新
        recordChooseMonthAdapter.addAll(item.getMonths());
        recordChooseMonthAdapter.notifyDataSetChanged();

        /**
         * 选择日期事件监听
         */
        recordChooseMonthAdapter.setOnClickChooseMonthListener(new RecordChooseMonthAdapter.OnClickChooseMonthListener() {
            @Override
            public void OnClickChooseMonth(String month) {
                onClickChooseDateListener.OnClickChoose(item.getYear(), month);
            }
        });
    }

    public void setOnClickChooseDateListener(OnClickChooseDateListener onClickChooseDateListener) {
        this.onClickChooseDateListener = onClickChooseDateListener;
    }

    private OnClickChooseDateListener onClickChooseDateListener;

    public interface OnClickChooseDateListener {
        void OnClickChoose(String year, String month);
    }
}
