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
import com.colortu.colortu_module.colortu_base.bean.StudyMineBean;
import com.colortu.colortu_module.databinding.AdapterStudyMineBinding;

/**
 * @author : Code23
 * @time : 2021/4/19
 * @module : StudyMinePastAdapter
 * @describe :已过期的列表适配器
 */
public class StudyMinePastAdapter extends BaseRecyclerAdapter<StudyMineBean.DataBean.DeprecatedBean> {
    //上下文
    private Context context;

    public StudyMinePastAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_study_mine;
    }

    @Override
    public void bindView(ViewDataBinding binding, StudyMineBean.DataBean.DeprecatedBean item, int position) {
        AdapterStudyMineBinding adapterStudyMineBinding = (AdapterStudyMineBinding) binding;
        adapterStudyMineBinding.mineItemview.setBackground(context.getResources().getDrawable(R.drawable.base_gray8_bg));
        Glide.with(context).load(BaseConstant.HomeWorkImgUrl + item.getLevelImageUrl()).into(adapterStudyMineBinding.mineRank);
        adapterStudyMineBinding.mineTitle.setText(item.getName());
        adapterStudyMineBinding.mineTime.setText(item.getRecentStudyTime() + "min");
        adapterStudyMineBinding.mineNum.setText(item.getCurrentNumOfPeople() + "人");
        //0官方自习室,1个人自习室
        if (item.getChannel().equals("1")) {
            adapterStudyMineBinding.mineCommand.setText("口令:\n" + item.getPassword());
        }

        //点击升级自习室
        adapterStudyMineBinding.mineUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //扫描解锁
                Bundle bundle = new Bundle();
                bundle.putInt("status", 2);
                BaseUIKit.startActivity(UIKitName.STUDY_MINE, UIKitName.QRCODE_PAY,
                        BaseConstant.QRCODE_PAY, BaseUIKit.OTHER, bundle);
            }
        });
    }
}
