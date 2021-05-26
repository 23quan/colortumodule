package com.colortu.colortu_module.colortu_study.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.Tools;
import com.colortu.colortu_module.colortu_base.bean.StudyDetailBean;
import com.colortu.colortu_module.databinding.AdapterStudyDetailBinding;

/**
 * @author : Code23
 * @time : 2021/4/9
 * @module : StudyDetailAdapter
 * @describe :自习列表详情列表适配器
 */
public class StudyDetailAdapter extends BaseRecyclerAdapter<StudyDetailBean.DataBean.UserDetailsBean> {
    //上下文
    private Context context;

    public StudyDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_study_detail;
    }

    @Override
    public void bindView(ViewDataBinding binding, final StudyDetailBean.DataBean.UserDetailsBean item, final int position) {
        AdapterStudyDetailBinding adapterStudyDetailBinding = (AdapterStudyDetailBinding) binding;
        //头像
        if (EmptyUtils.stringIsEmpty(item.getAvatar())) {
            Glide.with(context).load(item.getAvatar()).into(adapterStudyDetailBinding.studydetailHeadimg);
        } else {
            Glide.with(context).load(R.mipmap.icon_work_logo1).into(adapterStudyDetailBinding.studydetailHeadimg);
        }
        //昵称
        if (EmptyUtils.stringIsEmpty(item.getNickName())) {
            adapterStudyDetailBinding.studydetailName.setText(item.getNickName());
        } else {
            adapterStudyDetailBinding.studydetailName.setText(context.getResources().getString(R.string.not_login2));
        }
        //加入计时
        String times = Tools.dateDiff3(item.getLastJoinTime());
        adapterStudyDetailBinding.studydetailTime.setText(times);
        //设置状态
        adapterStudyDetailBinding.studydetailStatetip.setText(item.getDescribe());
        Glide.with(context).load(BaseConstant.HomeWorkImgUrl + item.getStatusImageUrl()).into(adapterStudyDetailBinding.studydetailStateicon);
        //点赞图标显示
        if (item.getIsClikeLike() == 1) {//已点赞
            Glide.with(context).load(R.mipmap.icon_study_like).into(adapterStudyDetailBinding.studydetailLikeicon);
        } else {//未点赞
            Glide.with(context).load(R.mipmap.icon_study_unlike).into(adapterStudyDetailBinding.studydetailLikeicon);
        }
        //点赞数
        adapterStudyDetailBinding.studydetailLikenum.setText(String.valueOf(Math.abs(item.getUserLikeNum())));

        //是否播放
        if (item.isIsplay()) {
            Glide.with(context).load(R.mipmap.icon_play_start).into(adapterStudyDetailBinding.studydetailPlay);
        } else {
            Glide.with(context).load(R.mipmap.icon_play_stop).into(adapterStudyDetailBinding.studydetailPlay);
        }


        /**
         * 点赞
         */
        adapterStudyDetailBinding.studydetailLikeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLikeListener.OnClickLike(item.getUuid());
            }
        });

        /**
         * 播放语音签名
         */
        adapterStudyDetailBinding.studydetailPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPlayListener.OnClickPlay(position, item.isIsplay(), BaseConstant.HomeWorkAudioUrl + item.getUserRecordURL());
            }
        });
    }

    private OnClickLikeListener onClickLikeListener;

    public void setOnClickLikeListener(OnClickLikeListener onClickLikeListener) {
        this.onClickLikeListener = onClickLikeListener;
    }

    public interface OnClickLikeListener {
        void OnClickLike(String uuid);
    }

    private OnClickPlayListener onClickPlayListener;

    public void setOnClickPlayListener(OnClickPlayListener onClickPlayListener) {
        this.onClickPlayListener = onClickPlayListener;
    }

    public interface OnClickPlayListener {
        void OnClickPlay(int position, boolean isplay, String audiourl);
    }
}
