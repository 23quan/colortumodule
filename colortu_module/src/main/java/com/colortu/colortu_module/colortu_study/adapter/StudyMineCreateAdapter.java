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
import com.colortu.colortu_module.colortu_base.bean.StudyMineBean;
import com.colortu.colortu_module.databinding.AdapterStudyMineBinding;

/**
 * @author : Code23
 * @time : 2021/4/19
 * @module : StudyMineCreateAdapter
 * @describe :我创建的列表适配器
 */
public class StudyMineCreateAdapter extends BaseRecyclerAdapter<StudyMineBean.DataBean.MyCreateBean> {
    //上下文
    private Context context;

    public StudyMineCreateAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_study_mine;
    }

    @Override
    public void bindView(ViewDataBinding binding, final StudyMineBean.DataBean.MyCreateBean item, int position) {
        AdapterStudyMineBinding adapterStudyMineBinding = (AdapterStudyMineBinding) binding;
        adapterStudyMineBinding.mineSwitch.setVisibility(View.VISIBLE);
        adapterStudyMineBinding.mineTitle.setText(item.getName());
        Glide.with(context).load(BaseConstant.HomeWorkImgUrl + item.getLevelImageUrl()).into(adapterStudyMineBinding.mineRank);
        adapterStudyMineBinding.mineTime.setText(item.getRecentStudyTime() + "min");
        adapterStudyMineBinding.mineNum.setText(item.getCurrentNumOfPeople() + "人");
        adapterStudyMineBinding.mineCommand.setText("口令:\n" + item.getPassword());
        //0关闭,1开启
        if (item.getIsOpen() == 0) {
            Glide.with(context).load(R.mipmap.icon_study_close).into(adapterStudyMineBinding.mineSwitch);
        } else {
            Glide.with(context).load(R.mipmap.icon_study_open).into(adapterStudyMineBinding.mineSwitch);
        }

        /**
         * 自习室开关
         */
        adapterStudyMineBinding.mineSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSwitchListener.OnClickSwitch(item.getIsOpen(), item.getId());
            }
        });

        /**
         * 点击升级自习室
         */
        adapterStudyMineBinding.mineUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //扫描解锁
                Bundle bundle = new Bundle();
                bundle.putInt("status", 2);
                BaseUIKit.startActivity(UIKitName.STUDY_MINE, UIKitName.QRCODE_PAY,
                        BaseConstant.QRCODE_PAY, BaseUIKit.NONE, bundle);
            }
        });

        /**
         * 点击item跳转详情
         */
        adapterStudyMineBinding.mineItemview.setOnClickListener(new View.OnClickListener() {
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
                        BaseUIKit.startActivity(UIKitName.STUDY_MINE, UIKitName.STUDY_DETAIL,
                                BaseConstant.STUDY_DETAIL, BaseUIKit.OTHER, bundle);
                    } else {
                        TipToast.tipToastShort(context.getResources().getString(R.string.studyroom_full));
                    }
                }
            }
        });
    }

    private OnClickSwitchListener onClickSwitchListener;

    public void setOnClickSwitchListener(OnClickSwitchListener onClickSwitchListener) {
        this.onClickSwitchListener = onClickSwitchListener;
    }

    public interface OnClickSwitchListener {
        void OnClickSwitch(int isOpen, int id);
    }
}
