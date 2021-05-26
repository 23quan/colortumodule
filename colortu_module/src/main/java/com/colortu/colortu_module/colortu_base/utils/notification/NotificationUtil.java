package com.colortu.colortu_module.colortu_base.utils.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.utils.OSUtil;

/**
 * @author : Code23
 * @time : 2021/4/29
 * @module : NotificationUtil
 * @describe :通知栏
 */
public class NotificationUtil {
    private static final CharSequence CHANNEL_NAME = "COLORTU";
    private static final String CHANNEL_ID = "colortu_play";
    private final static int SERVICE_ID = 1001;
    public static boolean isExistNotification = false;

    public static void createNotification(Service service) {
        if (isExistNotification) {
            return;
        }
        Notification notification = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            notification = createNotification(service, "");
        } else {
            //API 18以上，发送Notification并将其置为前台后，启动InnerService
            if (Build.VERSION.SDK_INT < 26) {
                notification = createNotification(service, "");
            } else {
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                NotificationManager notificationManager = (NotificationManager) service.getSystemService(Activity.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);

                notification = createNotification(service, CHANNEL_ID);
            }
        }
        service.startForeground(SERVICE_ID, notification);
        isExistNotification = true;
    }

    private static Notification createNotification(Context context, String channelId) {
        Intent intent = new Intent(context, NotificationClickReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        NotificationCompat.Builder builder;
        if (!channelId.isEmpty()) {
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        Notification notification;
        if (BaseApplication.appType == 1) {
            notification = builder
                    .setSmallIcon(R.mipmap.icon_work_logo1)
                    .setContentTitle("小兔作业")
                    .setContentText("音频播放中...")
                    .setWhen(System.currentTimeMillis())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
        } else {
            notification = builder
                    .setSmallIcon(R.mipmap.icon_listen_logo)
                    .setContentTitle("小兔听写")
                    .setContentText("音频播放中...")
                    .setWhen(System.currentTimeMillis())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
        }
        return notification;
    }

    public static void cancelNotification(Service service) {
        isExistNotification = false;
        NotificationManager notificationManager = (NotificationManager) service.getSystemService(Activity.NOTIFICATION_SERVICE);
        if (OSUtil.isVersionO()) {
            notificationManager.deleteNotificationChannel(CHANNEL_ID);
        } else {
            service.stopForeground(true);
        }
    }
}
