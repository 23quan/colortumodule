package com.colortu.colortu_module.colortu_listen.adapter;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.bean.ListenClassBean;
import com.colortu.colortu_module.databinding.AdapterListenClassBinding;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Code23
 * @time : 2021/4/2
 * @module : ListenClassAdapter
 * @describe :课列表适配器
 */
public class ListenClassAdapter extends BaseRecyclerAdapter<ListenClassBean.DataBean.PoetryVOSBean> {
    //免费课数
    private int freecount;
    //0 不是会员 1是会员
    private int isVip;
    //科目1.语文,2.英语
    private int type;
    //版本id
    private int publisherid;

    public ListenClassAdapter(int freecount, int isVip, int type, int publisherid) {
        this.freecount = freecount;
        this.isVip = isVip;
        this.type = type;
        this.publisherid = publisherid;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_listen_class;
    }

    @Override
    public void bindView(ViewDataBinding binding, final ListenClassBean.DataBean.PoetryVOSBean item, final int position) {
        AdapterListenClassBinding adapterListenClassBinding = (AdapterListenClassBinding) binding;
        adapterListenClassBinding.className.setText(item.getName());

        if (isVip == 1) {
            adapterListenClassBinding.classLock.setVisibility(View.GONE);
        } else {
            if (position < freecount) {
                adapterListenClassBinding.classLock.setVisibility(View.GONE);
            } else {
                adapterListenClassBinding.classLock.setVisibility(View.VISIBLE);
            }
        }

        //item点击监听
        adapterListenClassBinding.className.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVip == 1) {
                    //跳转播放
                    onItentListenPlayActivity(item.getName(), item.getWords());
                } else {
                    if (position < freecount) {
                        //跳转播放
                        onItentListenPlayActivity(item.getName(), item.getWords());
                    } else {
                        //扫描解锁
                        Bundle bundle = new Bundle();
                        bundle.putInt("status", 1);
                        BaseUIKit.startActivity(UIKitName.LISTEN_CLASS, UIKitName.QRCODE_PAY,
                                BaseConstant.QRCODE_PAY, BaseUIKit.NONE, bundle);
                    }
                }
            }
        });
    }

    /**
     * 跳转播放
     *
     * @param classname
     * @param wordsBeanList
     */
    public void onItentListenPlayActivity(String classname, List<ListenClassBean.DataBean.PoetryVOSBean.WordsBean> wordsBeanList) {
        Bundle bundle = new Bundle();
        bundle.putString("classname", classname);
        bundle.putInt("subjectid", type);
        bundle.putInt("versionid", publisherid);
        bundle.putSerializable("wordsbean", (Serializable) wordsBeanList);
        BaseUIKit.startActivity(UIKitName.LISTEN_CLASS, UIKitName.LISTEN_PLAY,
                BaseConstant.LISTEN_PLAY, BaseUIKit.OTHER, bundle);
    }
}
