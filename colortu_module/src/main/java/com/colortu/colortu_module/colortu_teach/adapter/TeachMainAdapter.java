package com.colortu.colortu_module.colortu_teach.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.bean.TeachMainBean;
import com.colortu.colortu_module.databinding.AdapterTeachMainBinding;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachMainAdapter
 * @describe :我的教辅列表
 */
public class TeachMainAdapter extends BaseRecyclerAdapter<TeachMainBean.DataBean.RecordsBean> {
    //上下文
    private Context context;
    //是否显示删除
    private boolean isDelete = false;

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public TeachMainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_teach_main;
    }

    @Override
    public void bindView(ViewDataBinding binding, final TeachMainBean.DataBean.RecordsBean item, final int position) {
        AdapterTeachMainBinding adapterTeachMainBinding = (AdapterTeachMainBinding) binding;
        adapterTeachMainBinding.teachBookname.setText(item.getTitle());
        adapterTeachMainBinding.teachGrade.setText(item.getSubjectName() + item.getGrade() + getSemester(item.getSemesterId()));

        //是否删除
        if (isDelete) {
            adapterTeachMainBinding.teachBookname.setTextColor(context.getResources().getColor(R.color.base_blue8));
            adapterTeachMainBinding.teachGrade.setTextColor(context.getResources().getColor(R.color.base_blue8));
            adapterTeachMainBinding.teachDelete.setVisibility(View.VISIBLE);
        } else {
            adapterTeachMainBinding.teachBookname.setTextColor(context.getResources().getColor(R.color.base_white1));
            adapterTeachMainBinding.teachGrade.setTextColor(context.getResources().getColor(R.color.base_white1));
            adapterTeachMainBinding.teachDelete.setVisibility(View.GONE);
        }

        /**
         * 删除
         */
        adapterTeachMainBinding.teachDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubjectDetailListener.OnClickDelete(item.getId());
            }
        });

        /**
         * item点击
         */
        adapterTeachMainBinding.teachView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grade = item.getSubjectName() + item.getGrade() + getSemester(item.getSemesterId());
                onClickSubjectDetailListener.OnClickItem(item, grade);
            }
        });
    }

    /**
     * 判断学期
     *
     * @return
     */
    public String getSemester(int semester) {
        String content = "";
        switch (semester) {
            case 1:
                content = context.getResources().getString(R.string.semester_last);
                break;
            case 2:
                content = context.getResources().getString(R.string.semester_next);
                break;
        }
        return content;
    }

    public void setOnClickSubjectDetailListener(OnClickSubjectDetailListener onClickSubjectDetailListener) {
        this.onClickSubjectDetailListener = onClickSubjectDetailListener;
    }

    private OnClickSubjectDetailListener onClickSubjectDetailListener;

    public interface OnClickSubjectDetailListener {
        void OnClickDelete(int ids);

        void OnClickItem(TeachMainBean.DataBean.RecordsBean recordsBean, String grade);
    }
}
