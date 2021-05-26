package com.colortu.colortu_module.colortu_base.core.http;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
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
            onNetWorkTip(context);
        }
    }

    /**
     * 显示网络提示
     *
     * @param context
     */
    public void onNetWorkTip(Context context) {
        if (GetBeanDate.getAgreeNetWork()) {
            if (NetWorkUtils.isMobile(context)) {
                TipToast.tipToastLong(context.getResources().getString(R.string.networt_mobile));
            }
        } else {
            if (NetWorkUtils.isMobile(context)) {
                ARouter.getInstance().build(BaseConstant.BASE_NETWORK).navigation();
            }
        }
    }
}
