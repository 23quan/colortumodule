package com.colortu.colortu_module.colortu_teach.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.TeachChooseGradeBean;
import com.colortu.colortu_module.colortu_base.bean.TeachMainBean;
import com.colortu.colortu_module.databinding.AdapterTeachChoosegradeclassBinding;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachChooseGradeClassAdapter
 * @describe :选择教辅年级适配器
 */
public class TeachChooseGradeClassAdapter extends BaseRecyclerAdapter<TeachChooseGradeBean.DataBean.ListBean> {
    //上下文
    private Context context;
    //我的教辅列表数据
    private TeachMainBean.DataBean dataBean;

    public TeachMainBean.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(TeachMainBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public TeachChooseGradeClassAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_teach_choosegradeclass;
    }

    @Override
    public void bindView(ViewDataBinding binding, final TeachChooseGradeBean.DataBean.ListBean item, final int position) {
        AdapterTeachChoosegradeclassBinding adapterTeachChoosegradeclassBinding = (AdapterTeachChoosegradeclassBinding) binding;
        adapterTeachChoosegradeclassBinding.choosegradeName.setText(getSubject(item.getSubject_id()) + getGrade(item.getGrade_id()) + getSemester(item.getSemester_id()));

        if (EmptyUtils.objectIsEmpty(dataBean)) {
            for (int i = 0; i < dataBean.getRecords().size(); i++) {
                if (item.getId() == dataBean.getRecords().get(i).getBookId()) {
                    item.setSelected(true);
                }
            }
        }

        //是否选择
        if (item.isIschoose() || item.isSelected()) {
            adapterTeachChoosegradeclassBinding.choosegradeChoose.setImageResource(R.mipmap.icon_circle_choose);
        } else {
            adapterTeachChoosegradeclassBinding.choosegradeChoose.setImageResource(R.mipmap.icon_circle_nochoose);
        }

        //选择年级教辅
        adapterTeachChoosegradeclassBinding.choosegradeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!item.isSelected()) {
                    onClickChooseGeadeListener.OnClickChooseGeade(position, item);
                }
            }
        });
    }

    /**
     * 判断科目
     *
     * @return
     */
    public String getSubject(int subject) {
        String content = "";
        switch (subject) {
            case 7:
                content = context.getResources().getString(R.string.subject_chinese);
                break;
            case 8:
                content = context.getResources().getString(R.string.subject_math);
                break;
            case 11:
                content = context.getResources().getString(R.string.subject_english);
                break;
            case 12:
                content = context.getResources().getString(R.string.subject_science);
                break;
            case 13:
                content = context.getResources().getString(R.string.subject_music);
                break;
            case 14:
                content = context.getResources().getString(R.string.subject_art);
                break;
            case 15:
                content = context.getResources().getString(R.string.subject_morality);
                break;
            case 16:
                content = context.getResources().getString(R.string.subject_sport);
                break;
            case 17:
                content = context.getResources().getString(R.string.subject_other);
                break;
        }
        return content;
    }

    /**
     * 判断年级
     *
     * @return
     */
    public String getGrade(int grade) {
        String content = "";
        switch (grade) {
            case 1:
                content = context.getResources().getString(R.string.grade1);
                break;
            case 2:
                content = context.getResources().getString(R.string.grade2);
                break;
            case 3:
                content = context.getResources().getString(R.string.grade3);
                break;
            case 4:
                content = context.getResources().getString(R.string.grade4);
                break;
            case 5:
                content = context.getResources().getString(R.string.grade5);
                break;
            case 6:
                content = context.getResources().getString(R.string.grade6);
                break;
        }
        return content;
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

    public void setOnClickChooseGeadeListener(OnClickChooseGeadeListener onClickChooseGeadeListener) {
        this.onClickChooseGeadeListener = onClickChooseGeadeListener;
    }

    private OnClickChooseGeadeListener onClickChooseGeadeListener;

    public interface OnClickChooseGeadeListener {
        void OnClickChooseGeade(int position, TeachChooseGradeBean.DataBean.ListBean listBean);
    }
}
