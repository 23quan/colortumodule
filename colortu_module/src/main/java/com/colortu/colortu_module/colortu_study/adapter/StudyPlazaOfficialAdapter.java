package com.colortu.colortu_module.colortu_study.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.bean.StudyPlazaBean;
import com.colortu.colortu_module.databinding.AdapterStudyPlazaofficialBinding;

/**
 * @author : Code23
 * @time : 2021/4/8
 * @module : StudyPlazaOfficialAdapter
 * @describe :官方自习列表适配器
 */
public class StudyPlazaOfficialAdapter extends BaseRecyclerAdapter<StudyPlazaBean.DataBean.OfficialBean> {
    //上下文
    private Context context;

    public StudyPlazaOfficialAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_study_plazaofficial;
    }

    @Override
    public void bindView(ViewDataBinding binding, final StudyPlazaBean.DataBean.OfficialBean item, int position) {
        AdapterStudyPlazaofficialBinding adapterStudyPlazaofficialBinding = (AdapterStudyPlazaofficialBinding) binding;
        adapterStudyPlazaofficialBinding.plazaofficialTitle.setText(item.getName());
        adapterStudyPlazaofficialBinding.plazaofficialArea.setText(item.getRegion() + "/" + item.getGrade());
        adapterStudyPlazaofficialBinding.plazaofficialNum.setText(Math.abs(item.getCurrentNumOfPeople()) + "人");
        adapterStudyPlazaofficialBinding.plazaofficialLikenum.setText(String.valueOf(Math.abs(item.getTotalLikeNum())));

        //item点击监听
        adapterStudyPlazaofficialBinding.plazaofficialItemview.setOnClickListener(new View.OnClickListener() {
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
