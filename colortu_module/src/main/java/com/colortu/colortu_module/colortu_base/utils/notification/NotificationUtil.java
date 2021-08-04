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
    public static void createNotification(String content,boolean showView) {
        if (ChannelUtil.isHuaWei()) {
            create(content,showView);
        }
    }

    @SuppressLint("NewApi")
    private static void create(String content,boolean showView) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//适配一下高版本
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            //关了通知默认提示音
            notificationChannel.setSound(null, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }

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
        //把自定义小的view放上
        builder.setContent(getSmallRemoteViews(content,showView));
        //把自定义大的view放上
        builder.setCustomBigContentView(getBigRemoteViews(content,showView));
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
     * 自定义小的界面
     */
    private static RemoteViews getSmallRemoteViews(String content, boolean showView) {
        //自定义界面
        RemoteViews remoteViews = new RemoteViews(BaseApplication.getContext().getPackageName(), R.layout.notification_base_smallplayer);
        //点击取消通知栏
        remoteViews.setOnClickPendingIntent(R.id.smallplayer_cancel, getPendingIntent(CLICK_CANCEL));
        //点击播放暂停
        remoteViews.setOnClickPendingIntent(R.id.smallplayer_play, getPendingIntent(CLICK_PLAY));
        if (showView) {
            //点击上一首
            remoteViews.setImageViewResource(R.id.smallplayer_last, R.mipmap.icon_base_left);
            remoteViews.setOnClickPendingIntent(R.id.smallplayer_last, getPendingIntent(CLICK_LAST));
            //点击下一首
            remoteViews.setImageViewResource(R.id.smallplayer_next, R.mipmap.icon_base_right);
            remoteViews.setOnClickPendingIntent(R.id.smallplayer_next, getPendingIntent(CLICK_NEXT));
        }

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
    private static RemoteViews getBigRemoteViews(String content, boolean showView) {
        //自定义界面
        RemoteViews remoteViews = new RemoteViews(BaseApplication.getContext().getPackageName(), R.layout.notification_base_bigplayer);
        //点击取消通知栏
        remoteViews.setOnClickPendingIntent(R.id.bigplayer_cancel, getPendingIntent(CLICK_CANCEL));
        //点击播放暂停
        remoteViews.setOnClickPendingIntent(R.id.bigplayer_play, getPendingIntent(CLICK_PLAY));
        if (showView) {
            //点击上一首
            remoteViews.setImageViewResource(R.id.bigplayer_last, R.mipmap.icon_base_left);
            remoteViews.setOnClickPendingIntent(R.id.bigplayer_last, getPendingIntent(CLICK_LAST));
            //点击下一首
            remoteViews.setImageViewResource(R.id.bigplayer_next, R.mipmap.icon_base_right);
            remoteViews.setOnClickPendingIntent(R.id.bigplayer_next, getPendingIntent(CLICK_NEXT));
        }

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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}