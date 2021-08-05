package com.colortu.colortu_module.colortu_teach.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.bean.TeachAnswerItemBean;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.databinding.AdapterTeachAnsweritemBinding;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : TeachAnswerItemAdapter
 * @describe :答案列表适配器
 */
public class TeachAnswerItemAdapter extends BaseRecyclerAdapter<TeachAnswerItemBean> {
    //上下文
    private Context context;

    public TeachAnswerItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_teach_answeritem;
    }

    @Override
    public void bindView(ViewDataBinding binding, final TeachAnswerItemBean item, int position) {
        AdapterTeachAnsweritemBinding adapterTeachAnsweritemBinding = (AdapterTeachAnsweritemBinding) binding;
        if (item.getType().equals("text")) {//文本答案
            adapterTeachAnsweritemBinding.answeritemImgview.setVisibility(View.GONE);
            adapterTeachAnsweritemBinding.answeritemText.setVisibility(View.VISIBLE);
            adapterTeachAnsweritemBinding.answeritemText.setText(Html.fromHtml(item.getContent()));
        } else {//图片答案
            adapterTeachAnsweritemBinding.answeritemText.setVisibility(View.GONE);
            adapterTeachAnsweritemBinding.answeritemImgview.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(BaseConstant.HomeWorkImgUrl + item.getContent())
                    .apply(new RequestOptions().placeholder(R.drawable.base_img_loading))
                    .into(adapterTeachAnsweritemBinding.answeritemImg);

            adapterTeachAnsweritemBinding.answeritemImgview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("imageurl", BaseConstant.HomeWorkImgUrl + item.getContent());
                    BaseUIKit.startActivity(UIKitName.TEACH_ANSWER, UIKitName.BASE_IMAGEDETAIL,
                            BaseConstant.BASE_IMAGEDETAIL, BaseUIKit.OTHER, bundle);
                }
            });
        }
    }
}
