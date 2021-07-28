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
import com.colortu.colortu_module.databinding.AdapterRecordHistorysubjectdetailBinding;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : RecordHistorySubjectDetailAdapter
 * @describe :历史科目详情列表适配器
 */
public class RecordHistorySubjectDetailAdapter extends BaseRecyclerAdapter<RecordSubjectDetailBean.DataBean.RecordsBean> {
    //上下文
    private Context context;

    public RecordHistorySubjectDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_record_historysubjectdetail;
    }

    @Override
    public void bindView(ViewDataBinding binding, final RecordSubjectDetailBean.DataBean.RecordsBean item, final int position) {
        AdapterRecordHistorysubjectdetailBinding adapterRecordHistorysubjectdetailBinding = (AdapterRecordHistorysubjectdetailBinding) binding;
        adapterRecordHistorysubjectdetailBinding.historysubjectdetailSerialnumber.setText(String.valueOf(position + 1));
        adapterRecordHistorysubjectdetailBinding.historysubjectdetailContent.setText(item.getContent());
        adapterRecordHistorysubjectdetailBinding.historysubjectdetailDuration.setText(item.getAudioTime());

        //是否播放
        if (item.isIsplay()) {
            Glide.with(context).load(R.mipmap.icon_play_start).into(adapterRecordHistorysubjectdetailBinding.historysubjectdetailPlay);
        } else {
            Glide.with(context).load(R.mipmap.icon_play_stop).into(adapterRecordHistorysubjectdetailBinding.historysubjectdetailPlay);
        }

        //播放事件监听
        adapterRecordHistorysubjectdetailBinding.historysubjectdetailPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHistorySubjectDetailListener.OnClickPlay(position, item.isIsplay(), Tools.stringIndexOf(item.getAudioUrl(), BaseConstant.HomeWorkAudioUrl));
            }
        });
    }

    public void setOnClickHistorySubjectDetailListener(OnClickHistorySubjectDetailListener onClickHistorySubjectDetailListener) {
        this.onClickHistorySubjectDetailListener = onClickHistorySubjectDetailListener;
    }

    private OnClickHistorySubjectDetailListener onClickHistorySubjectDetailListener;

    public interface OnClickHistorySubjectDetailListener {
        void OnClickPlay(int position, boolean isplay, String audiourl);
    }
}
