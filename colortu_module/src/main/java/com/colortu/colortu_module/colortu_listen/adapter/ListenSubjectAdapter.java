package com.colortu.colortu_module.colortu_listen.adapter;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.bean.ListenSubjectBean;
import com.colortu.colortu_module.databinding.AdapterListenSubjectBinding;

/**
 * @author : Code23
 * @time : 2021/3/30
 * @module : ListenSubjectAdapter
 * @describe :科目列表适配器
 */
public class ListenSubjectAdapter extends BaseRecyclerAdapter<ListenSubjectBean> {
    @Override
    public int getLayoutId() {
        return R.layout.adapter_listen_subject;
    }

    @Override
    public void bindView(ViewDataBinding binding, final ListenSubjectBean item, int position) {
        AdapterListenSubjectBinding adapterListenSubjectBinding = (AdapterListenSubjectBinding) binding;
        adapterListenSubjectBinding.subjectText.setText(item.getName());

        //科目点击监听
        adapterListenSubjectBinding.subjectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChooseSubjectListener.OnClickChooseSubject(item.getId());
            }
        });
    }

    private OnClickChooseSubjectListener onClickChooseSubjectListener;

    public void setOnClickChooseSubjectListener(OnClickChooseSubjectListener onClickChooseSubjectListener) {
        this.onClickChooseSubjectListener = onClickChooseSubjectListener;
    }

    public interface OnClickChooseSubjectListener {
        void OnClickChooseSubject(int id);
    }
}
