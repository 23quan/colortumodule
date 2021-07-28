package com.colortu.colortu_module.colortu_record.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordHistoryBean;
import com.colortu.colortu_module.databinding.AdapterRecordHistoryBinding;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordHistoryAdapter
 * @describe :作业历史列表适配器
 */
public class RecordHistoryAdapter extends BaseRecyclerAdapter<RecordHistoryBean.DataBean.RecordsBean> {
    //上下文
    private Context context;

    public RecordHistoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_record_history;
    }

    @Override
    public void bindView(ViewDataBinding binding, final RecordHistoryBean.DataBean.RecordsBean item, int position) {
        AdapterRecordHistoryBinding adapterRecordHistoryBinding = (AdapterRecordHistoryBinding) binding;
        int startindex = item.getHistoryDate().lastIndexOf("-") + 1;
        int endindex = item.getHistoryDate().indexOf(" ");
        final String day = item.getHistoryDate().substring(startindex, endindex);
        //日期
        adapterRecordHistoryBinding.historyDate.setText(day + "日");
        //科目
        adapterRecordHistoryBinding.historyName.setText(item.getSubjects());

        //是否完成
        if (item.getIsCompleted() == 1) {//0 未完成 1完成
            adapterRecordHistoryBinding.historyIsfinish.setTextColor(context.getResources().getColor(R.color.base_green1));
            adapterRecordHistoryBinding.historyIsfinish.setText(context.getString(R.string.finished));
        } else {
            adapterRecordHistoryBinding.historyIsfinish.setTextColor(context.getResources().getColor(R.color.base_black1));
            adapterRecordHistoryBinding.historyIsfinish.setText(context.getString(R.string.unfinished));
        }

        //科目列表item事件监听
        adapterRecordHistoryBinding.historyItemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHistoryListener.OnClickHistory(day, item.getHistoryDate().substring(0, item.getHistoryDate().indexOf(" ")));
            }
        });
    }

    public void setOnClickHistoryListener(OnClickHistoryListener onClickHistoryListener) {
        this.onClickHistoryListener = onClickHistoryListener;
    }

    private OnClickHistoryListener onClickHistoryListener;

    public interface OnClickHistoryListener {
        void OnClickHistory(String day, String date);
    }
}
