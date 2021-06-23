package com.colortu.colortu_module.colortu_base.core.http;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.activity.NetWorkActivity;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.TipToast;

/**
 * @author : Code23
 * @time : 2021/1/4
 * @module : NetWorkChangReceiver
 * @describe :监听网络状态变化广播
 */
public class NetWorkChangReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (ChannelUtil.isHuaWei()) {
                onHuaWeiNetWorkTip(context);
            } else {
                onOtherNetWorkTip(context);
            }
        }
    }

    /**
     * 华为显示网络提示
     *
     * @param context
     */
    public void onHuaWeiNetWorkTip(Context context) {
        if (GetBeanDate.getAgreeNetWork()) {
            if (NetWorkUtils.isConnected(context)) {
                if (NetWorkUtils.isMobile(context)) {
                    TipToast.tipToastLong(context.getResources().getString(R.string.networt_mobile));
                } else {
                    TipToast.tipToastLong(context.getResources().getString(R.string.networt_wifi));
                }
            } else {
                TipToast.tipToastLong(context.getResources().getString(R.string.no_networt));
            }
        } else {
            if (NetWorkUtils.isConnected(context)) {
                if (NetWorkUtils.isMobile(context)) {
                    ARouter.getInstance().build(BaseConstant.BASE_NETWORK).navigation();
                }
            } else {
                TipToast.tipToastLong(context.getResources().getString(R.string.no_networt));
            }
        }
    }

    /**
     * 其他显示网络提示
     */
    public void onOtherNetWorkTip(Context context) {
        if (NetWorkUtils.isConnected(context)) {
            if (NetWorkUtils.isMobile(context)) {
                TipToast.tipToastLong(context.getResources().getString(R.string.networt_mobile));
            } else {
                TipToast.tipToastLong(context.getResources().getString(R.string.networt_wifi));
            }
        } else {
            TipToast.tipToastLong(context.getResources().getString(R.string.no_networt));
        }
    }
}
