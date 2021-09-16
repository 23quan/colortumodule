package com.colortu.colortu_module.colortu_study.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.databinding.AdapterStudyPlazafiltrategradeBinding;

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
        return R.layout.adapter_study_plazafiltrategrade;
    }

    @Override
    public void bindView(ViewDataBinding binding, final String item, final int position) {
        final AdapterStudyPlazafiltrategradeBinding adapterStudyPlazafiltrategradeBinding = (AdapterStudyPlazafiltrategradeBinding) binding;
        if (filtrate != null && filtrate.equals(item)) {
            adapterStudyPlazafiltrategradeBinding.plazafiltrategradeItemview.setBackground(context.getResources().getDrawable(R.mipmap.icon_base_btnbg));
        } else {
            adapterStudyPlazafiltrategradeBinding.plazafiltrategradeItemview.setBackground(context.getResources().getDrawable(R.drawable.base_blue5_bg));
        }
        adapterStudyPlazafiltrategradeBinding.plazafiltrategradeText.setText(item);

        //item点击监听
        adapterStudyPlazafiltrategradeBinding.plazafiltrategradeItemview.setOnClickListener(new View.OnClickListener() {
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
