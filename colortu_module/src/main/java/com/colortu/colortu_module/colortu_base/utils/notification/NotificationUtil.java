package com.colortu.colortu_module.colortu_base.utils.notification;

import android.app.Notification;
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
    public final static String CLICK_APP = "click_app";
    public final static String CLICK_CANCEL = "click_cancel";
    public final static String CLICK_LAST = "click_last";
    public final static String CLICK_PLAY = "click_play";
    public final static String CLICK_NEXT = "click_next";

    private String CHANNEL_NAME = "colortu";
    private String CHANNEL_ID = "1020308";
    private int NOTIFY_ID = 1008800;
    public boolean isExistNotification = false;
    //上下文
    private Context context;
    //通知管理器
    private NotificationManager notificationManager;

    public NotificationUtil(Context context) {
        this.context = context;
    }

    /**
     * 创建通知栏
     */
    public void createNotification(String content) {
        if (ChannelUtil.isHuaWei()) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知框兼容 android 8 及以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.setSound(null, null);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            //显示通知栏
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            //设置小图标
            if (BaseApplication.appType == 1) {
                builder.setSmallIcon(R.mipmap.icon_work_huaweilogo);
            } else {
                builder.setSmallIcon(R.mipmap.icon_listen_logo);
            }
            //自定义通知栏
            builder.setContent(getSmallRemoteViews(content));
            //设置优先级
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            //通知栏点击
            builder.setContentIntent(getPendingIntent(CLICK_APP));
            //设置是否自动销毁
            builder.setAutoCancel(true);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(BaseApplication.getContext());
            notificationManagerCompat.notify(NOTIFY_ID, builder.build());

            Notification notification = builder.build();
            notificationManager.notify(NOTIFY_ID, notification);
            isExistNotification = true;
        }
    }

    /**
     * 取消通知栏
     */
    public void cancelNotification() {
        if (ChannelUtil.isHuaWei()) {
            if (isExistNotification) {
                if (notificationManager != null) {
                    notificationManager.cancel(NOTIFY_ID);
                    isExistNotification = false;
                }
            }
        }
    }

    /**
     * 自定义小的界面
     */
    private RemoteViews getSmallRemoteViews(String content) {
        //自定义界面
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_base_smallplayer);
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
    private RemoteViews getBigRemoteViews(String content) {
        //自定义界面
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_base_bigplayer);
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
    private PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(context, NotificationClickReceiver.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}