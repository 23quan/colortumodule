package com.colortu.colortu_module.colortu_base.utils.notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;

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
    public static void createNotification(String content) {
        if (ChannelUtil.isHuaWei()) {
            create(content);
        }
    }

    @SuppressLint("NewApi")
    private static void create(String content) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 通知框兼容 android 8 及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setSound(null, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        if (BaseApplication.appType == 1) {
            builder.setSmallIcon(R.mipmap.icon_work_huaweilogo);
        } else {
            builder.setSmallIcon(R.mipmap.icon_listen_logo);
        }
        //设置优先级
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        //点击不让消失
        builder.setAutoCancel(true);
        //自定义view
        builder.setContent(getSmallRemoteViews(content));
        //把自定义小的view放上
        builder.setCustomContentView(getSmallRemoteViews(content));
        //把自定义大的view放上
        builder.setCustomBigContentView(getBigRemoteViews(content));
        //设置全屏意图
        builder.setFullScreenIntent(getPendingIntent(CLICK_APP), true);
        //整个点击跳转activity
        builder.setContentIntent(getPendingIntent(CLICK_APP));
        //显示通知
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFY_ID, builder.build());
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
                notificationManager.cancelAll();
                isExistNotification = false;
            }
        }
    }

    /**
     * 自定义小的界面
     */
    private static RemoteViews getSmallRemoteViews(String content) {
        //自定义界面
        RemoteViews remoteViews = new RemoteViews(BaseApplication.getContext().getPackageName(), R.layout.notification_base_smallplayer);
        //点击取消通知栏
        remoteViews.setOnClickPendingIntent(R.id.smallplayer_cancel, getPendingIntent(CLICK_CANCEL));
        //点击播放暂停
        remoteViews.setOnClickPendingIntent(R.id.smallplayer_play, getPendingIntent(CLICK_PLAY));
        //点击上一首
        remoteViews.setImageViewResource(R.id.smallplayer_last, R.mipmap.icon_base_left);
        remoteViews.setOnClickPendingIntent(R.id.smallplayer_last, getPendingIntent(CLICK_LAST));
        //点击下一首
        remoteViews.setImageViewResource(R.id.smallplayer_next, R.mipmap.icon_base_right);
        remoteViews.setOnClickPendingIntent(R.id.smallplayer_next, getPendingIntent(CLICK_NEXT));

        if (BaseApplication.appType == 1) {
            remoteViews.setImageViewResource(R.id.smallplayer_icon, R.mipmap.icon_work_huaweilogo);
        } else {
            remoteViews.setImageViewResource(R.id.smallplayer_icon, R.mipmap.icon_listen_logo);
        }
        remoteViews.setTextViewText(R.id.smallplayer_title, content);

        if (BaseApplication.isListen) {
            remoteViews.setImageViewResource(R.id.smallplayer_play, R.mipmap.icon_play_start);
        } else {
            remoteViews.setImageViewResource(R.id.smallplayer_play, R.mipmap.icon_play_stop);
        }

        return remoteViews;
    }

    /**
     * 自定义大的界面
     */
    private static RemoteViews getBigRemoteViews(String content) {
        //自定义界面
        RemoteViews remoteViews = new RemoteViews(BaseApplication.getContext().getPackageName(), R.layout.notification_base_bigplayer);
        //点击取消通知栏
        remoteViews.setOnClickPendingIntent(R.id.bigplayer_cancel, getPendingIntent(CLICK_CANCEL));
        //点击播放暂停
        remoteViews.setOnClickPendingIntent(R.id.bigplayer_play, getPendingIntent(CLICK_PLAY));
        //点击上一首
        remoteViews.setImageViewResource(R.id.bigplayer_last, R.mipmap.icon_base_left);
        remoteViews.setOnClickPendingIntent(R.id.bigplayer_last, getPendingIntent(CLICK_LAST));
        //点击下一首
        remoteViews.setImageViewResource(R.id.bigplayer_next, R.mipmap.icon_base_right);
        remoteViews.setOnClickPendingIntent(R.id.bigplayer_next, getPendingIntent(CLICK_NEXT));

        if (BaseApplication.appType == 1) {
            remoteViews.setImageViewResource(R.id.bigplayer_icon, R.mipmap.icon_work_huaweilogo);
        } else {
            remoteViews.setImageViewResource(R.id.bigplayer_icon, R.mipmap.icon_listen_logo);
        }
        remoteViews.setTextViewText(R.id.bigplayer_title, content);

        if (BaseApplication.isListen) {
            remoteViews.setImageViewResource(R.id.bigplayer_play, R.mipmap.icon_play_start);
        } else {
            remoteViews.setImageViewResource(R.id.bigplayer_play, R.mipmap.icon_play_stop);
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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        return pendingIntent;
    }
}
