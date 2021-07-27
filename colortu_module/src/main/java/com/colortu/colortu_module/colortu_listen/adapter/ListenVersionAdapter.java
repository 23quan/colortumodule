package com.colortu.colortu_module.colortu_listen.adapter;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.NumUtils;
import com.colortu.colortu_module.colortu_base.bean.ListenVersionBean;
import com.colortu.colortu_module.databinding.AdapterListenVersionBinding;

/**
 * @author : Code23
 * @time : 2021/4/1
 * @module : ListenVersionAdapter
 * @describe :版本列表数据适配器
 */
public class ListenVersionAdapter extends BaseRecyclerAdapter<ListenVersionBean.DataBean.RecordsBean> {
    @Override
    public int getLayoutId() {
        return R.layout.adapter_listen_version;
    }

    @Override
    public void bindView(ViewDataBinding binding, final ListenVersionBean.DataBean.RecordsBean item, int position) {
        AdapterListenVersionBinding adapterListenVersionBinding = (AdapterListenVersionBinding) binding;

        if (item.getName().contains("(")) {
            adapterListenVersionBinding.versionName.setText(item.getName().substring(0, item.getName().indexOf("(")));
            if (GetBeanDate.getChooseGrade() + 1 < 3) {
                adapterListenVersionBinding.versionGrade.setVisibility(View.GONE);
            } else {
                adapterListenVersionBinding.versionGrade.setVisibility(View.VISIBLE);
                adapterListenVersionBinding.versionGrade.setText(NumUtils.numToString(item.getMinGrade()) + "年级起点");
            }
        } else {
            adapterListenVersionBinding.versionName.setText(item.getName());
        }

        if (item.getId() == GetBeanDate.getChooseVersion()) {
            adapterListenVersionBinding.versionItembg.setColorFilter(0x66000000);
        } else {
            adapterListenVersionBinding.versionItembg.setColorFilter(0x00000000);
        }

        //版本item点击
        adapterListenVersionBinding.versionItemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChooseVersionListener.OnClickChooseVersion(item.getId());
            }
        });
    }

    private OnClickChooseVersionListener onClickChooseVersionListener;

    public void setOnClickChooseVersionListener(OnClickChooseVersionListener onClickChooseVersionListener) {
        this.onClickChooseVersionListener = onClickChooseVersionListener;
    }

    public interface OnClickChooseVersionListener {
        void OnClickChooseVersion(int id);
    }
}
