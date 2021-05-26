package com.colortu.colortu_module.colortu_record.adapter;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordChooseSubjectBean;
import com.colortu.colortu_module.databinding.AdapterRecordChoosesubjectBinding;

/**
 * @author : Code23
 * @time : 2020/12/2
 * @module : RecordChooseSubjectAdapter
 * @describe :选择科目列表适配器
 */
public class RecordChooseSubjectAdapter extends BaseRecyclerAdapter<RecordChooseSubjectBean.DataBean.RecordsBean> {
    @Override
    public int getLayoutId() {
        return R.layout.adapter_record_choosesubject;
    }

    @Override
    public void bindView(ViewDataBinding binding, final RecordChooseSubjectBean.DataBean.RecordsBean item, int position) {
        AdapterRecordChoosesubjectBinding adapterRecordChoosesubjectBinding = (AdapterRecordChoosesubjectBinding) binding;
        adapterRecordChoosesubjectBinding.choosesubjectItem.setText(item.getSubjectName());

        /**
         * 选择科目
         */
        adapterRecordChoosesubjectBinding.choosesubjectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSelectListener.OnClickSelect(item.getSubjectName(), item.getId());
            }
        });
    }

    public void setOnClickSelectListener(OnClickSelectListener onClickSelectListener) {
        this.onClickSelectListener = onClickSelectListener;
    }

    private OnClickSelectListener onClickSelectListener;

    public interface OnClickSelectListener {
        void OnClickSelect(String subjectname, int id);
    }
}
