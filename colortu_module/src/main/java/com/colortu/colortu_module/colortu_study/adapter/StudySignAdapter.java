package com.colortu.colortu_module.colortu_study.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.bean.StudySignBean;
import com.colortu.colortu_module.databinding.AdapterStudySignBinding;

/**
 * @author : Code23
 * @time : 2021/4/12
 * @module : StudySignAdapter
 * @describe :个性签名列表适配器
 */
public class StudySignAdapter extends BaseRecyclerAdapter<StudySignBean.DataBean.RecordsBean> {
    //上下文
    private Context context;

    public StudySignAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_study_sign;
    }

    @Override
    public void bindView(ViewDataBinding binding, final StudySignBean.DataBean.RecordsBean item, final int position) {
        AdapterStudySignBinding adapterStudySignBinding = (AdapterStudySignBinding) binding;
        adapterStudySignBinding.signText.setText(item.getDescribe());
        Glide.with(context).load(BaseConstant.HomeWorkImgUrl + item.getImageUrl()).into(adapterStudySignBinding.signIcon);
        //修改图标
        if (item.isChoose()) {
            adapterStudySignBinding.signItemview.setBackground(context.getResources().getDrawable(R.mipmap.icon_base_btnbg));
        } else {
            adapterStudySignBinding.signItemview.setBackground(context.getResources().getDrawable(R.drawable.base_blue5_bg));
        }

        //选择个性状态
        adapterStudySignBinding.signItemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStudySignListener.OnClickStudySign(position, item.getId());
            }
        });
    }

    private OnClickStudySignListener onClickStudySignListener;

    public void setOnClickStudySignListener(OnClickStudySignListener onClickStudySignListener) {
        this.onClickStudySignListener = onClickStudySignListener;
    }

    public interface OnClickStudySignListener {
        void OnClickStudySign(int position, int id);
    }
}
