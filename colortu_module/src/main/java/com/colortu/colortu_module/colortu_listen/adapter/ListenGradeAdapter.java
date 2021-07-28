package com.colortu.colortu_module.colortu_listen.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.databinding.AdapterListenGradeBinding;

/**
 * @author : Code23
 * @time : 2021/3/30
 * @module : ListenGradeAdapter
 * @describe :年级列表适配器
 */
public class ListenGradeAdapter extends BaseRecyclerAdapter<Integer> {
    //上下文
    private Context context;

    public ListenGradeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_listen_grade;
    }

    @Override
    public void bindView(ViewDataBinding binding, final Integer item, final int position) {
        AdapterListenGradeBinding adapterListenGradeBinding = (AdapterListenGradeBinding) binding;
        Glide.with(context).load(item).into(adapterListenGradeBinding.gradeGradeimg);

        if (GetBeanDate.getChooseGrade() == (position + 1) && GetBeanDate.getIsFirst()) {
            adapterListenGradeBinding.gradeGradeimg.setColorFilter(0x66000000);
        } else {
            adapterListenGradeBinding.gradeGradeimg.setColorFilter(0x00000000);
        }

        //选择年级
        adapterListenGradeBinding.gradeGradeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChooseGradeListener.OnClickChooseGrade(position + 1);
            }
        });
    }

    private OnClickChooseGradeListener onClickChooseGradeListener;

    public void setOnClickChooseGradeListener(OnClickChooseGradeListener onClickChooseGradeListener) {
        this.onClickChooseGradeListener = onClickChooseGradeListener;
    }

    public interface OnClickChooseGradeListener {
        void OnClickChooseGrade(int id);
    }
}
