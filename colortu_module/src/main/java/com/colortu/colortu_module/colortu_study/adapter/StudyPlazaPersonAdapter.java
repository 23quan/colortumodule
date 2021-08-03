package com.colortu.colortu_module.colortu_study.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.bean.StudyPlazaBean;
import com.colortu.colortu_module.databinding.AdapterStudyPlazapersonBinding;

/**
 * @author : Code23
 * @time : 2021/4/8
 * @module : StudyPlazaPersonAdapter
 * @describe :个人自习列表适配器
 */
public class StudyPlazaPersonAdapter extends BaseRecyclerAdapter<StudyPlazaBean.DataBean.PersonalBean> {
    //上下文
    private Context context;

    public StudyPlazaPersonAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_study_plazaperson;
    }

    @Override
    public void bindView(ViewDataBinding binding, final StudyPlazaBean.DataBean.PersonalBean item, int position) {
        AdapterStudyPlazapersonBinding adapterStudyPlazapersonBinding = (AdapterStudyPlazapersonBinding) binding;
        //1为会员
        if (item.getIsVIP() == 1) {
            adapterStudyPlazapersonBinding.plazapersonItemview.setBackground(context.getResources().getDrawable(R.drawable.base_red1_bg));
        } else {
            adapterStudyPlazapersonBinding.plazapersonItemview.setBackground(context.getResources().getDrawable(R.mipmap.icon_base_btnbg));
        }
        adapterStudyPlazapersonBinding.plazapersonTitle.setText(item.getName());
        Glide.with(context).load(BaseConstant.HomeWorkImgUrl + item.getLevelImageUrl()).into(adapterStudyPlazapersonBinding.plazapersonRank);
        adapterStudyPlazapersonBinding.plazapersonNum.setText(Math.abs(item.getCurrentNumOfPeople()) + "人");
        adapterStudyPlazapersonBinding.plazapersonLikenum.setText(String.valueOf(Math.abs(item.getTotalLikeNum())));

        //item点击监听
        adapterStudyPlazapersonBinding.plazapersonItemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //0表示关闭,1表示开启
                if (item.getIsOpen() == 0) {
                    TipToast.tipToastShort(context.getResources().getString(R.string.studyroom_closed));
                } else {
                    if (item.getCurrentNumOfPeople() < item.getCurrentMaxNumOfPeople()) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", item.getId());
                        bundle.putString("channel", item.getChannel());
                        BaseUIKit.startActivity(UIKitName.STUDY_PLAZA, UIKitName.STUDY_DETAIL,
                                BaseConstant.STUDY_DETAIL, BaseUIKit.OTHER, bundle);
                    } else {
                        TipToast.tipToastShort(context.getResources().getString(R.string.studyroom_full));
                    }
                }
            }
        });
    }
}
