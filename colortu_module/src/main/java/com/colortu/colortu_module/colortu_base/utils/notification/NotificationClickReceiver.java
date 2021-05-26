package com.colortu.colortu_module.colortu_base.utils.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;

/**
 * @author : Code23
 * @time : 2021/4/29
 * @module : NotificationClickReceiver
 * @describe :通知栏跳转广播监听
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (BaseApplication.appType == 1) {
            ARouter.getInstance().build(BaseConstant.WORK_SPLASH).navigation();
        } else {
            ARouter.getInstance().build(BaseConstant.LISTEN_SPLASH).navigation();
        }
    }
}
