package com.colortu.colortu_module.colortu_record.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectBean;
import com.colortu.colortu_module.databinding.AdapterRecordHistorysubjectBinding;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordHistorySubjectAdapter
 * @describe :历史科目列表适配器
 */
public class RecordHistorySubjectAdapter extends BaseRecyclerAdapter<RecordSubjectBean.DataBean.RecordsBean> {
    //上下文
    private Context context;

    public RecordHistorySubjectAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_record_historysubject;
    }

    @Override
    public void bindView(ViewDataBinding binding, final RecordSubjectBean.DataBean.RecordsBean item, int position) {
        AdapterRecordHistorysubjectBinding adapterRecordHistorysubjectBinding = (AdapterRecordHistorysubjectBinding) binding;
        //科目名字+数量
        adapterRecordHistorysubjectBinding.historysubjectName.setText(item.getSubjectName() + "(" + item.getHomeworkCount() + ")");
        //时间
        adapterRecordHistorysubjectBinding.historysubjectCounttime.setText(item.getCreateTime().substring(item.getCreateTime().indexOf(" ")));

        //是否完成
        if (item.getIsCompleted() == 1) {//0 未完成 1完成
            adapterRecordHistorysubjectBinding.historysubjectName.setTextColor(context.getResources().getColor(R.color.base_gray9));
            adapterRecordHistorysubjectBinding.historysubjectCounttime.setTextColor(context.getResources().getColor(R.color.base_gray9));
            adapterRecordHistorysubjectBinding.historysubjectSelect.setImageResource(R.mipmap.icon_square_choose);
        } else {
            adapterRecordHistorysubjectBinding.historysubjectName.setTextColor(context.getResources().getColor(R.color.base_white1));
            adapterRecordHistorysubjectBinding.historysubjectCounttime.setTextColor(context.getResources().getColor(R.color.base_white1));
            adapterRecordHistorysubjectBinding.historysubjectSelect.setImageResource(R.mipmap.icon_square_nochoose);
        }

        //item事件监听
        adapterRecordHistorysubjectBinding.historysubjectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubjectDetailListener.OnClickSubjectDetail(item.getSubjectName(), item.getSubjectId());
            }
        });

        //是否完成
        adapterRecordHistorysubjectBinding.historysubjectSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubjectDetailListener.OnClickIsFinish(item);
            }
        });
    }

    public void setOnClickSubjectDetailListener(OnClickSubjectDetailListener onClickSubjectDetailListener) {
        this.onClickSubjectDetailListener = onClickSubjectDetailListener;
    }

    private OnClickSubjectDetailListener onClickSubjectDetailListener;

    public interface OnClickSubjectDetailListener {
        void OnClickSubjectDetail(String subjectname, int subjectId);

        void OnClickIsFinish(RecordSubjectBean.DataBean.RecordsBean recordsBean);
    }
}
