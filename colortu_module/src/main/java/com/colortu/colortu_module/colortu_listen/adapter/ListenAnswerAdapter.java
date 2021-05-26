package com.colortu.colortu_module.colortu_listen.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.bean.ListenClassBean;
import com.colortu.colortu_module.databinding.AdapterListenAnswerBinding;

/**
 * @author : Code23
 * @time : 2021/4/6
 * @module : ListenAnswerAdapter
 * @describe :听写答案列表适配器
 */
public class ListenAnswerAdapter extends BaseRecyclerAdapter<ListenClassBean.DataBean.PoetryVOSBean.WordsBean> {
    //上下文
    private Context context;

    public ListenAnswerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_listen_answer;
    }

    @Override
    public void bindView(ViewDataBinding binding, final ListenClassBean.DataBean.PoetryVOSBean.WordsBean item, final int position) {
        AdapterListenAnswerBinding adapterListenAnswerBinding = (AdapterListenAnswerBinding) binding;
        adapterListenAnswerBinding.answerText.setText(item.getWord());
        //是否播放
        if (item.isPlaying()) {
            adapterListenAnswerBinding.answerPlay.setColorFilter(0x66000000);
            Glide.with(context).load(R.mipmap.icon_listen_play).into(adapterListenAnswerBinding.answerPlay);
        } else {
            adapterListenAnswerBinding.answerPlay.setColorFilter(0x00000000);
            Glide.with(context).load(R.mipmap.icon_listen_stop).into(adapterListenAnswerBinding.answerPlay);
        }

        /**
         * 播放按钮
         */
        adapterListenAnswerBinding.answerPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAnswerListener.OnClickAnswer(item, position);
            }
        });
    }

    private OnClickAnswerListener onClickAnswerListener;

    public void setOnClickAnswerListener(OnClickAnswerListener onClickAnswerListener) {
        this.onClickAnswerListener = onClickAnswerListener;
    }

    public interface OnClickAnswerListener {
        void OnClickAnswer(ListenClassBean.DataBean.PoetryVOSBean.WordsBean wordsBean, int position);
    }
}
