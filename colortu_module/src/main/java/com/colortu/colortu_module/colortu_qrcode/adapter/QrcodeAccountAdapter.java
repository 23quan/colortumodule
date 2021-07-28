package com.colortu.colortu_module.colortu_qrcode.adapter;

import android.content.Context;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.bean.QrcodeUserInfoBean;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.databinding.AdapterQrcodeAccountBinding;

/**
 * @author : Code23
 * @time : 2021/6/4
 * @module : QrcodeAccountAdapter
 * @describe :uuid账号列表适配器
 */
public class QrcodeAccountAdapter extends BaseRecyclerAdapter<QrcodeUserInfoBean.DataBean.UserListBean> {
    private Context context;//上下文
    private String content = "";//vip内容

    public QrcodeAccountAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_qrcode_account;
    }

    @Override
    public void bindView(ViewDataBinding binding, final QrcodeUserInfoBean.DataBean.UserListBean item, int position) {
        AdapterQrcodeAccountBinding adapterQrcodeAccountBinding = (AdapterQrcodeAccountBinding) binding;
        adapterQrcodeAccountBinding.accountNumber.setText(context.getString(R.string.account) + (position + 1));
        adapterQrcodeAccountBinding.accountTime.setText(item.getCreateDate() + context.getString(R.string.create));
        //判断是否vip账号
        content = "";
        if (item.getIsVIP() != 1) {
            adapterQrcodeAccountBinding.accountContent.setText(context.getString(R.string.no_vip));
        } else {
            if (item.getVipTypes().indexOf("2") != -1) {
                content = context.getString(R.string.listen) + "、" + context.getString(R.string.answer) + "、" + context.getString(R.string.translate);
            } else {
                if (item.getVipTypes().indexOf("1") != -1) {
                    content = context.getString(R.string.listen) + "、";
                }

                if (item.getVipTypes().equals("3")) {
                    content = content + context.getString(R.string.answer) + "、";
                }

                if (item.getVipTypes().equals("4")) {
                    content = content + context.getString(R.string.translate) + "、";
                }
            }
            adapterQrcodeAccountBinding.accountContent.setText(content + context.getString(R.string.vip));
        }

        //选择账号
        adapterQrcodeAccountBinding.accountItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChooseAccountListener.OnClickChooseAccount(item);
            }
        });
    }

    private OnClickChooseAccountListener onClickChooseAccountListener;

    public void setOnClickChooseAccountListener(OnClickChooseAccountListener onClickChooseAccountListener) {
        this.onClickChooseAccountListener = onClickChooseAccountListener;
    }

    public interface OnClickChooseAccountListener {
        void OnClickChooseAccount(QrcodeUserInfoBean.DataBean.UserListBean userListBean);
    }
}
