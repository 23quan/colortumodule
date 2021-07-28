package com.colortu.colortu_module.colortu_record.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectDetailBean;
import com.colortu.colortu_module.colortu_base.utils.Tools;
import com.colortu.colortu_module.databinding.AdapterRecordSubjectdetailBinding;

/**
 * @author : Code23
 * @time : 2020/12/2
 * @module : RecordSubjectDetailAdapter
 * @describe :录入科目详情列表适配器
 */
public class RecordSubjectDetailAdapter extends BaseRecyclerAdapter<RecordSubjectDetailBean.DataBean.RecordsBean> {
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

    public RecordSubjectDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_record_subjectdetail;
    }

    @Override
    public void bindView(ViewDataBinding binding, final RecordSubjectDetailBean.DataBean.RecordsBean item, final int position) {
        AdapterRecordSubjectdetailBinding adapterRecordSubjectdetailBinding = (AdapterRecordSubjectdetailBinding) binding;
        adapterRecordSubjectdetailBinding.subjectdetailSerialnumber.setText(String.valueOf(position + 1));
        adapterRecordSubjectdetailBinding.subjectdetailContent.setText(item.getContent());
        adapterRecordSubjectdetailBinding.subjectdetailDuration.setText(item.getAudioTime());

        //是否播放
        if (item.isIsplay()) {
            Glide.with(context).load(R.mipmap.icon_play_start).into(adapterRecordSubjectdetailBinding.subjectdetailPlay);
        } else {
            Glide.with(context).load(R.mipmap.icon_play_stop).into(adapterRecordSubjectdetailBinding.subjectdetailPlay);
        }

        //是否删除
        if (isDelete) {
            adapterRecordSubjectdetailBinding.subjectdetailItemview.setBackgroundColor(context.getResources().getColor(R.color.base_blue11));
            adapterRecordSubjectdetailBinding.subjectdetailSerialnumber.setTextColor(context.getResources().getColor(R.color.base_gray10));
            adapterRecordSubjectdetailBinding.subjectdetailContent.setTextColor(context.getResources().getColor(R.color.base_gray10));
            adapterRecordSubjectdetailBinding.subjectdetailPlay.setColorFilter(context.getResources().getColor(R.color.base_gray10));
            adapterRecordSubjectdetailBinding.subjectdetailDuration.setTextColor(context.getResources().getColor(R.color.base_gray10));
            adapterRecordSubjectdetailBinding.subjectdetailDelete.setVisibility(View.VISIBLE);
        } else {
            adapterRecordSubjectdetailBinding.subjectdetailItemview.setBackgroundColor(context.getResources().getColor(R.color.base_blue11));
            adapterRecordSubjectdetailBinding.subjectdetailSerialnumber.setTextColor(context.getResources().getColor(R.color.base_white1));
            adapterRecordSubjectdetailBinding.subjectdetailContent.setTextColor(context.getResources().getColor(R.color.base_white1));
            adapterRecordSubjectdetailBinding.subjectdetailPlay.setColorFilter(context.getResources().getColor(R.color.base_white1));
            adapterRecordSubjectdetailBinding.subjectdetailDuration.setTextColor(context.getResources().getColor(R.color.base_white1));
            adapterRecordSubjectdetailBinding.subjectdetailDelete.setVisibility(View.GONE);
        }

        //播放
        adapterRecordSubjectdetailBinding.subjectdetailPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubjectDetailListener.OnClickPlay(position, item.isIsplay(), Tools.stringIndexOf(item.getAudioUrl(), BaseConstant.HomeWorkAudioUrl));
            }
        });

        //删除
        adapterRecordSubjectdetailBinding.subjectdetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSubjectDetailListener.OnClickDelete(item.getId());
            }
        });
    }

    public void setOnClickSubjectDetailListener(OnClickSubjectDetailListener onClickSubjectDetailListener) {
        this.onClickSubjectDetailListener = onClickSubjectDetailListener;
    }

    private OnClickSubjectDetailListener onClickSubjectDetailListener;

    public interface OnClickSubjectDetailListener {
        void OnClickPlay(int position, boolean isplay, String audiourl);

        void OnClickDelete(int id);
    }
}
