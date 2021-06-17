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
        String action = intent.getAction();
        switch (action) {
            case NotificationUtil.CLICK_APP: //通知栏进入app
                if (BaseApplication.appType == 1) {
                    ARouter.getInstance().build(BaseConstant.WORK_SPLASH).navigation();
                } else {
                    ARouter.getInstance().build(BaseConstant.LISTEN_SPLASH).navigation();
                }
                break;
            case NotificationUtil.CLICK_CANCEL://通知栏取消
                BaseApplication.getInstance().exitApp();
                break;
            case NotificationUtil.CLICK_LAST://通知栏上一首
                onNotificationListener.onNotificationLast();
                break;
            case NotificationUtil.CLICK_PLAY://通知栏播放和暂停
                onNotificationListener.onNotificationPlay();
                break;
            case NotificationUtil.CLICK_NEXT://通知栏下一首
                onNotificationListener.onNotificationNext();
                break;
        }
    }

    public static void setOnNotificationListener(OnNotificationListener onNotificationListener) {
        NotificationClickReceiver.onNotificationListener = onNotificationListener;
    }

    private static OnNotificationListener onNotificationListener;

    public interface OnNotificationListener {
        //上一首
        void onNotificationLast();

        //播放暂停
        void onNotificationPlay();

        //下一首
        void onNotificationNext();
    }
}
