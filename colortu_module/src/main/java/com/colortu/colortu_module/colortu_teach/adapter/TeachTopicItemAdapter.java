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
import com.colortu.colortu_module.colortu_base.bean.TeachTopicItemBean;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.databinding.AdapterTeachTopicitemBinding;

/**
 * @author : Code23
 * @time : 2020/12/4
 * @module : TeachTopicItemAdapter
 * @describe :原题列表适配器
 */
public class TeachTopicItemAdapter extends BaseRecyclerAdapter<TeachTopicItemBean> {
    //上下文
    private Context context;

    public TeachTopicItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_teach_topicitem;
    }

    @Override
    public void bindView(ViewDataBinding binding, final TeachTopicItemBean item, int position) {
        AdapterTeachTopicitemBinding adapterTeachTopicitemBinding = (AdapterTeachTopicitemBinding) binding;
        if (item.getType().equals("text")) {//文本原题
            adapterTeachTopicitemBinding.topicitemImgview.setVisibility(View.GONE);
            adapterTeachTopicitemBinding.topicitemText.setVisibility(View.VISIBLE);
            if (EmptyUtils.stringIsEmpty(item.getContent())) {
                adapterTeachTopicitemBinding.topicitemText.setText(Html.fromHtml(item.getContent()));
            } else {
                adapterTeachTopicitemBinding.topicitemText.setText(context.getResources().getString(R.string.omit));
            }
        } else {//图片原题
            if (EmptyUtils.stringIsEmpty(item.getContent())) {
                adapterTeachTopicitemBinding.topicitemText.setVisibility(View.GONE);
                adapterTeachTopicitemBinding.topicitemImgview.setVisibility(View.VISIBLE);
                Glide.with(context).load(BaseConstant.HomeWorkImgUrl + item.getContent())
                        .apply(new RequestOptions().placeholder(R.drawable.base_img_loading))
                        .into(adapterTeachTopicitemBinding.topicitemImg);

                adapterTeachTopicitemBinding.topicitemImgview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("imageurl", BaseConstant.HomeWorkImgUrl + item.getContent());
                        BaseUIKit.startActivity(UIKitName.TEACH_ANSWER, UIKitName.BASE_IMAGEDETAIL,
                                BaseConstant.BASE_IMAGEDETAIL, BaseUIKit.OTHER, bundle);
                    }
                });
            } else {
                adapterTeachTopicitemBinding.topicitemImgview.setVisibility(View.GONE);
                adapterTeachTopicitemBinding.topicitemText.setVisibility(View.VISIBLE);
                adapterTeachTopicitemBinding.topicitemText.setText(context.getResources().getString(R.string.omit));
            }
        }
    }
}
