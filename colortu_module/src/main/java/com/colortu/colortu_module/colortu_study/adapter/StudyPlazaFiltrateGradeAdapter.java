package com.colortu.colortu_module.colortu_study.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.databinding.AdapterStudyPlazafiltrateBinding;

/**
 * @author : Code23
 * @time : 2021/4/8
 * @module : StudyPlazaFiltrateGradeAdapter
 * @describe :年级数据列表适配器
 */
public class StudyPlazaFiltrateGradeAdapter extends BaseRecyclerAdapter<String> {
    //上下文
    private Context context;
    //筛选关键字
    private String filtrate;

    public StudyPlazaFiltrateGradeAdapter(Context context, String filtrate) {
        this.context = context;
        this.filtrate = filtrate;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_study_plazafiltrate;
    }

    @Override
    public void bindView(ViewDataBinding binding, final String item, final int position) {
        final AdapterStudyPlazafiltrateBinding adapterStudyPlazafiltrateBinding = (AdapterStudyPlazafiltrateBinding) binding;
        if (filtrate != null && filtrate.equals(item)) {
            adapterStudyPlazafiltrateBinding.plazafiltrateItemview.setBackground(context.getResources().getDrawable(R.mipmap.icon_base_btnbg));
        } else {
            adapterStudyPlazafiltrateBinding.plazafiltrateItemview.setBackground(context.getResources().getDrawable(R.drawable.base_blue5_bg));
        }
        adapterStudyPlazafiltrateBinding.plazafiltrateText.setText(item);

        //item点击监听
        adapterStudyPlazafiltrateBinding.plazafiltrateItemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filtrate != null && filtrate.equals(item)) {
                    filtrate = "";
                    notifyItemChanged(position);
                    onClickFiltrateGradeListener.OnClickFiltrateGrade("");
                } else {
                    filtrate = item;
                    notifyItemChanged(position);
                    onClickFiltrateGradeListener.OnClickFiltrateGrade(item);
                }
            }
        });
    }

    private OnClickFiltrateGradeListener onClickFiltrateGradeListener;

    public void setOnClickFiltrateGradeListener(OnClickFiltrateGradeListener onClickFiltrateGradeListener) {
        this.onClickFiltrateGradeListener = onClickFiltrateGradeListener;
    }

    public interface OnClickFiltrateGradeListener {
        void OnClickFiltrateGrade(String item);
    }
}
