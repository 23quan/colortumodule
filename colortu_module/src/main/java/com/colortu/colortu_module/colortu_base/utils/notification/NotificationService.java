package com.colortu.colortu_module.colortu_base.utils.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @author : Code23
 * @time : 2021/4/29
 * @module : NotificationService
 * @describe :通知栏service
 */
public class NotificationService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationUtil.createNotification(this);
    }

    @Override
    public void onDestroy() {
        NotificationUtil.cancelNotification(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
