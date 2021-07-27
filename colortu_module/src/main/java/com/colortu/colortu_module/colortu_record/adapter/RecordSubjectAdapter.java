package com.colortu.colortu_module.colortu_record.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectBean;
import com.colortu.colortu_module.databinding.AdapterRecordSubjectBinding;

/**
 * @author : Code23
 * @time : 2020/11/27
 * @module : RecordSubjectAdapter
 * @describe :今日作业列表适配器
 */
public class RecordSubjectAdapter extends BaseRecyclerAdapter<RecordSubjectBean.DataBean.RecordsBean> {
    //上下文
    private Context context;

    public RecordSubjectAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_record_subject;
    }

    @Override
    public void bindView(ViewDataBinding binding, final RecordSubjectBean.DataBean.RecordsBean item, int position) {
        AdapterRecordSubjectBinding adapterRecordSubjectBinding = (AdapterRecordSubjectBinding) binding;
        //科目名字+数量
        adapterRecordSubjectBinding.recordsubjectName.setText(item.getSubjectName() + "(" + item.getHomeworkCount() + ")");
        //时间
        adapterRecordSubjectBinding.recordsubjectCounttime.setText(item.getCreateTime().substring(item.getCreateTime().indexOf(" ")));

        if (position == 0) {
            adapterRecordSubjectBinding.recordsubjectBar.setVisibility(View.VISIBLE);
        } else {
            adapterRecordSubjectBinding.recordsubjectBar.setVisibility(View.GONE);
        }

        //是否完成
        if (item.getIsCompleted() == 1) {//0 未完成 1完成
            adapterRecordSubjectBinding.recordsubjectName.setTextColor(context.getResources().getColor(R.color.base_gray9));
            adapterRecordSubjectBinding.recordsubjectCounttime.setTextColor(context.getResources().getColor(R.color.base_gray9));
            adapterRecordSubjectBinding.recordsubjectChoose.setImageResource(R.mipmap.icon_square_choose);
        } else {
            adapterRecordSubjectBinding.recordsubjectName.setTextColor(context.getResources().getColor(R.color.base_white1));
            adapterRecordSubjectBinding.recordsubjectCounttime.setTextColor(context.getResources().getColor(R.color.base_white1));
            adapterRecordSubjectBinding.recordsubjectChoose.setImageResource(R.mipmap.icon_square_nochoose);
        }

        //科目item
        adapterRecordSubjectBinding.recordsubjectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转科目录音详情
                Bundle bundle = new Bundle();
                bundle.putString("subjectname", item.getSubjectName());
                bundle.putInt("subjectId", item.getSubjectId());
                BaseUIKit.startActivity(UIKitName.RECORD_SUBJECT, UIKitName.RECORD_SUBJECTDETAIL,
                        BaseConstant.RECORD_SUBJECTDETAIL, BaseUIKit.OTHER, bundle);
            }
        });

        //是否完成
        adapterRecordSubjectBinding.recordsubjectChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.OnClickIsFinish(item);
            }
        });
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private OnClickListener onClickListener;

    public interface OnClickListener {
        void OnClickIsFinish(RecordSubjectBean.DataBean.RecordsBean recordsBean);
    }
}
