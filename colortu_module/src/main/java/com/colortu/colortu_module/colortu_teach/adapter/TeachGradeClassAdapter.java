package com.colortu.colortu_module.colortu_teach.adapter;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.bean.TeachGradeClassBean;
import com.colortu.colortu_module.databinding.AdapterTeachGradeclassBinding;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachGradeClassAdapter
 * @describe :年级课目列表适配器
 */
public class TeachGradeClassAdapter extends BaseRecyclerAdapter<TeachGradeClassBean.DataBean.ListBeanX> {
    //免费课数
    private int freecount;
    //0 不是会员 1是会员
    private int isVip;

    public TeachGradeClassAdapter(int freecount, int isVip) {
        this.freecount = freecount;
        this.isVip = isVip;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_teach_gradeclass;
    }

    @Override
    public void bindView(ViewDataBinding binding, final TeachGradeClassBean.DataBean.ListBeanX item, final int position) {
        final AdapterTeachGradeclassBinding adapterTeachGradeclassBinding = (AdapterTeachGradeclassBinding) binding;
        //课名
        adapterTeachGradeclassBinding.gradeclassName.setText(item.getTitle());

        if (item.getSubject_id() != 11) {
            if (isVip == 1) {
                adapterTeachGradeclassBinding.gradeclassLock.setVisibility(View.GONE);
            } else {
                if (position < freecount) {
                    adapterTeachGradeclassBinding.gradeclassLock.setVisibility(View.GONE);
                } else {
                    adapterTeachGradeclassBinding.gradeclassLock.setVisibility(View.VISIBLE);
                }
            }
        }

        //跳转答案or听写
        adapterTeachGradeclassBinding.gradeclassName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getSubject_id() == 11) {
                    //跳转听写
                    Bundle bundle = new Bundle();
                    bundle.putString("classname", item.getTitle());
                    bundle.putInt("examid", item.getId());
                    bundle.putString("audiourl", item.getPath());
                    bundle.putInt("position", position);
                    BaseUIKit.startActivity(UIKitName.TEACH_GRADECLASS, UIKitName.TEACH_PLAY,
                            BaseConstant.TEACH_PLAY, BaseUIKit.OTHER, bundle);
                } else {
                    if (isVip == 1) {
                        //跳转答案
                        Bundle bundle = new Bundle();
                        bundle.putString("classname", item.getTitle());
                        bundle.putInt("examid", item.getId());
                        BaseUIKit.startActivity(UIKitName.TEACH_GRADECLASS, UIKitName.TEACH_ANSWER,
                                BaseConstant.TEACH_ANSWER, BaseUIKit.OTHER, bundle);
                    } else {
                        if (position < freecount) {
                            //跳转答案
                            Bundle bundle = new Bundle();
                            bundle.putString("classname", item.getTitle());
                            bundle.putInt("examid", item.getId());
                            BaseUIKit.startActivity(UIKitName.TEACH_GRADECLASS, UIKitName.TEACH_ANSWER,
                                    BaseConstant.TEACH_ANSWER, BaseUIKit.OTHER, bundle);
                        } else {
                            //扫描解锁
                            Bundle bundle = new Bundle();
                            bundle.putInt("status", 1);
                            BaseUIKit.startActivity(UIKitName.TEACH_GRADECLASS, UIKitName.QRCODE_PAY,
                                    BaseConstant.QRCODE_PAY, BaseUIKit.NONE, bundle);
                        }
                    }
                }
            }
        });
    }
}
