package com.colortu.colortu_module.colortu_base.utils.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;

import static android.app.Notification.CATEGORY_MESSAGE;
import static android.app.Notification.DEFAULT_ALL;
import static androidx.core.app.NotificationCompat.PRIORITY_MAX;

/**
 * @author : Code23
 * @time : 2021/4/29
 * @module : NotificationUtil
 * @describe :通知栏
 */
public class NotificationUtil {
    private static final CharSequence CHANNEL_NAME = "colortu";
    private static final String CHANNEL_ID = "1020308";
    private final static int NOTIFY_ID = 1008800;
    public static boolean isExistNotification = false;

    public final static String CLICK_APP = "click_app";
    public final static String CLICK_CANCEL = "click_cancel";
    public final static String CLICK_LAST = "click_last";
    public final static String CLICK_PLAY = "click_play";
    public final static String CLICK_NEXT = "click_next";

    public final static String HUAWEI_3PRO = "HUAWEISIM-AL00";
    public final static String HUAWEI_4X = "HUAWEINIK-AL00";

    //上下文
    private static Context context;
    //通知管理器
    private static NotificationManager notificationManager;

    public static void setContext(Context context) {
        NotificationUtil.context = context;
    }

    /**
     * 创建通知栏
     */
    public static void createNotification(String content, boolean showView) {
        if (ChannelUtil.isHuaWei()) {
            create(content, showView);
        }
    }

    @SuppressLint("NewApi")
    private static void create(String content, boolean showView) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//适配一下高版本
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            //关了通知默认提示音
            notificationChannel.setSound(null, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        String vendor = Build.BRAND + Build.MODEL;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        //设置小图标
        if (BaseApplication.appType == 1) {
            builder.setSmallIcon(R.mipmap.icon_work_huaweilogo);
        } else {
            builder.setSmallIcon(R.mipmap.icon_listen_logo);
        }
        //设置类别
        builder.setCategory(CATEGORY_MESSAGE);
        //设置默认的
        builder.setDefaults(DEFAULT_ALL);
        //设置是否正在通知
        builder.setOngoing(true);
        //点击不让消失
        builder.setAutoCancel(false);
        //设置优先级
        builder.setPriority(PRIORITY_MAX);
        //设置只提醒一次
        builder.setOnlyAlertOnce(true);
        //把自定义view放上
        if (vendor.equals(HUAWEI_3PRO)) {
            builder.setContent(getHuaWei3proRemoteViews(content, showView));
        } else if (vendor.equals(HUAWEI_4X)) {
            builder.setContent(getHuaWei4xRemoteViews(content, showView));
        } else {
            builder.setContent(getHuaWei3proRemoteViews(content, showView));
        }
        //整个点击跳转activity
        builder.setContentIntent(getPendingIntent(CLICK_APP));

        Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);
        isExistNotification = true;
    }

    /**
     * 取消通知栏
     */
    public static void cancelNotification() {
        if (ChannelUtil.isHuaWei()) {
            cancel();
        }
    }

    private static void cancel() {
        if (isExistNotification) {
            if (notificationManager != null) {
                notificationManager.cancel(NOTIFY_ID);
                isExistNotification = false;
            }
        }
    }

    /**
     * 自定义华为3pro界面
     */
    private static RemoteViews getHuaWei3proRemoteViews(String content, boolean showView) {
        //自定义界面
        RemoteViews remoteViews = new RemoteViews(BaseApplication.getContext().getPackageName(), R.layout.notification_base_huawei3pro);
        //点击取消通知栏
        remoteViews.setOnClickPendingIntent(R.id.huawei3pro_cancel, getPendingIntent(CLICK_CANCEL));
        //点击播放暂停
        remoteViews.setOnClickPendingIntent(R.id.huawei3pro_play, getPendingIntent(CLICK_PLAY));
        if (showView) {
            //点击上一首
            remoteViews.setImageViewResource(R.id.huawei3pro_lasticon, R.mipmap.icon_base_left);
            remoteViews.setOnClickPendingIntent(R.id.huawei3pro_last, getPendingIntent(CLICK_LAST));
            //点击下一首
            remoteViews.setImageViewResource(R.id.huawei3pro_nexticon, R.mipmap.icon_base_right);
            remoteViews.setOnClickPendingIntent(R.id.huawei3pro_next, getPendingIntent(CLICK_NEXT));
        }

        if (BaseApplication.appType == 1) {
            remoteViews.setImageViewResource(R.id.huawei3pro_icon, R.mipmap.icon_work_huaweilogo);
        } else {
            remoteViews.setImageViewResource(R.id.huawei3pro_icon, R.mipmap.icon_listen_logo);
        }
        remoteViews.setTextViewText(R.id.huawei3pro_title, content);

        if (BaseApplication.isListen) {
            remoteViews.setImageViewResource(R.id.huawei3pro_play, R.mipmap.icon_play_start);
        } else {
            remoteViews.setImageViewResource(R.id.huawei3pro_play, R.mipmap.icon_play_stop);
        }

        return remoteViews;
    }

    /**
     * 自定义华为4x界面
     */
    private static RemoteViews getHuaWei4xRemoteViews(String content, boolean showView) {
        //自定义界面
        RemoteViews remoteViews = new RemoteViews(BaseApplication.getContext().getPackageName(), R.layout.notification_base_huawei4x);
        //点击取消通知栏
        remoteViews.setOnClickPendingIntent(R.id.huawei4x_cancel, getPendingIntent(CLICK_CANCEL));
        //点击播放暂停
        remoteViews.setOnClickPendingIntent(R.id.huawei4x_play, getPendingIntent(CLICK_PLAY));
        if (showView) {
            //点击上一首
            remoteViews.setImageViewResource(R.id.huawei4x_last, R.mipmap.icon_base_left);
            remoteViews.setOnClickPendingIntent(R.id.huawei4x_last, getPendingIntent(CLICK_LAST));
            //点击下一首
            remoteViews.setImageViewResource(R.id.huawei4x_next, R.mipmap.icon_base_right);
            remoteViews.setOnClickPendingIntent(R.id.huawei4x_next, getPendingIntent(CLICK_NEXT));
        }

        if (BaseApplication.appType == 1) {
            remoteViews.setImageViewResource(R.id.huawei4x_icon, R.mipmap.icon_work_huaweilogo);
        } else {
            remoteViews.setImageViewResource(R.id.huawei4x_icon, R.mipmap.icon_listen_logo);
        }
        remoteViews.setTextViewText(R.id.huawei4x_title, content);

        if (BaseApplication.isListen) {
            remoteViews.setImageViewResource(R.id.huawei4x_play, R.mipmap.icon_play_start);
        } else {
            remoteViews.setImageViewResource(R.id.huawei4x_play, R.mipmap.icon_play_stop);
        }

        return remoteViews;
    }

    /**
     * 封装PendingIntent
     *
     * @param action
     * @return
     */
    private static PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(context, NotificationClickReceiver.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}