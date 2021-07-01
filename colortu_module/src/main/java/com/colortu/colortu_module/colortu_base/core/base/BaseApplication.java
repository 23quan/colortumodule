package com.colortu.colortu_module.colortu_base.core.base;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bytedance.boost_multidex.BoostMultiDex;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.http.NetWorkChangReceiver;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;
import com.colortu.colortu_module.colortu_base.utils.UmengUtil;
import com.colortu.colortu_module.colortu_base.bean.QrcodeAddUserBean;
import com.colortu.colortu_module.colortu_base.utils.notification.NotificationUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * @author : Code23
 * @time : 2020/11/24
 * @module : BaseApplication
 * @describe :applocation
 */
public class BaseApplication extends Application {
    //主线程ID
    private static int mThreadId = -1;
    //主线程
    private static Thread mThread;
    //主线程Handler
    private static Handler mHandler;
    //主线程Looper
    private static Looper mLooper;
    //全局唯一的上下文Context
    private static Context mContext;
    //全局BaseApplication
    private static BaseApplication instance;
    //是否是debug apk
    private boolean isDebug = false;
    //监听网络状态变化广播
    public static NetWorkChangReceiver netWorkChangReceiver;
    //弱引用activity
    private Stack<WeakReference<Activity>> mActivityStack;
    //1.作业app 2.听写app
    public static int appType;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //BoostMultiDex初始化
        BoostMultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mThreadId = android.os.Process.myTid();
        mThread = Thread.currentThread();
        mHandler = new Handler();
        mLooper = getMainLooper();
        mContext = this;
        instance = this;

        //友盟初始化
        UmengUtil.init(this);
        //阿里路由初始化
        if (isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        //网络变化广播监听
        netWorkChangReceiver = new NetWorkChangReceiver();
        //亮屏息屏广播监听
        if (ChannelUtil.isXTC() || ChannelUtil.isHuaWei()) {
            registSreenStatusReceiver();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //阿里路由销毁
        ARouter.getInstance().destroy();
        //亮屏息屏广播监听注销
        if (ChannelUtil.isXTC() || ChannelUtil.isHuaWei()) {
            if (screenStatusReceiver != null) {
                try {
                    unregisterReceiver(screenStatusReceiver);
                    screenStatusReceiver = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //获取主线程id
    public static int getMThreadId() {
        return mThreadId;
    }

    //获取主线程
    public static Thread getMThread() {
        return mThread;
    }

    //获取主线程的handler
    public static Handler getMHandler() {
        return mHandler;
    }

    //获取主线程的looper
    public static Looper getMLooper() {
        return mLooper;
    }

    //全局唯一的上下文context
    public static Context getContext() {
        return mContext;
    }

    //全局BaseApplication
    public static BaseApplication getInstance() {
        return instance;
    }

    //请求头信息
    private static HashMap<String, String> headers;

    public static HashMap<String, String> getHeaders() {
        return headers;
    }

    public static void setHeaders(HashMap<String, String> headers) {
        BaseApplication.headers = headers;
    }

    //控制二维码数据
    private static QrcodeAddUserBean.DataBean.ChannelConfigBean.PageInsertConfigBean pageInsertConfigBean;

    public static QrcodeAddUserBean.DataBean.ChannelConfigBean.PageInsertConfigBean getPageInsertConfigBean() {
        return pageInsertConfigBean;
    }

    public static void setPageInsertConfigBean(QrcodeAddUserBean.DataBean.ChannelConfigBean.PageInsertConfigBean pageInsertConfigBean) {
        BaseApplication.pageInsertConfigBean = pageInsertConfigBean;
    }

    //控制二维码listbean
    private static List<QrcodeAddUserBean.DataBean.ChannelConfigBean.PageInsertConfigBean> pageInsertConfigBeanList;

    public static List<QrcodeAddUserBean.DataBean.ChannelConfigBean.PageInsertConfigBean> getPageInsertConfigBeanList() {
        return pageInsertConfigBeanList;
    }

    public static void setPageInsertConfigBeanList(List<QrcodeAddUserBean.DataBean.ChannelConfigBean.PageInsertConfigBean> pageInsertConfigBeanList) {
        BaseApplication.pageInsertConfigBeanList = pageInsertConfigBeanList;
    }

    //二维码显示
    public void startAfterActivity(String whenshow) {
        BaseUIKit.startAfterActivity(pageInsertConfigBean, whenshow);
    }

    //是否在听写，true正在听写，false不在听写或听写结束
    public static boolean isListen = false;

    public static void setIsListen(boolean isListen) {
        BaseApplication.isListen = isListen;
    }

    /**
     * ---------------------------------------mediaPlayer播放提示音--------------------------------------------
     */
    //媒体播放器
    public static MediaPlayer mediaPlayer;

    /**
     * 开始播放提示语音
     */
    public static void onStartTipVoice(int audio) {
        onStopTipVoice();
        mediaPlayer = new MediaPlayer().create(BaseApplication.getContext(), audio);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                onStopTipVoice();
                if (onFinishTipVoiceListener != null) {
                    onFinishTipVoiceListener.onFinishTipVoice();
                }
            }
        });
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
    }

    /**
     * 停止播放提示语音
     */
    public static void onStopTipVoice() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private static OnFinishTipVoiceListener onFinishTipVoiceListener;

    public static void setOnFinishTipVoiceListener(OnFinishTipVoiceListener onFinishTipVoiceListener) {
        BaseApplication.onFinishTipVoiceListener = onFinishTipVoiceListener;
    }

    public interface OnFinishTipVoiceListener {
        void onFinishTipVoice();
    }

    /**
     * ---------------------------------------activity管理--------------------------------------------
     */

    /**
     * 添加Activity到栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(new WeakReference<>(activity));
    }

    /**
     * 获取当前Activity（栈中最后一个压入的）
     *
     * @return
     */
    public Activity currentActivity() {
        checkWeakReference();
        if (mActivityStack != null && !mActivityStack.isEmpty()) {
            return mActivityStack.lastElement().get();
        }
        return null;
    }

    /**
     * 检查弱引用是否释放，若释放，则从栈中清理掉该元素
     */
    public void checkWeakReference() {
        if (mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                if (temp == null) {
                    it.remove();
                }
            }
        }
    }

    /**
     * 关闭当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = currentActivity();
        if (activity != null) {
            finishActivity(activity);
        }
    }

    /**
     * 关闭指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                // 清理掉已经释放的activity
                if (temp == null) {
                    it.remove();
                    continue;
                }
                if (temp == activity) {
                    it.remove();
                }
            }
            activity.finish();
        }
    }

    /**
     * 关闭指定类名的所有Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        if (mActivityStack != null) {
            // 使用迭代器进行安全删除
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity activity = activityReference.get();
                // 清理掉已经释放的activity
                if (activity == null) {
                    it.remove();
                    continue;
                }
                if (activity.getClass().equals(cls)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mActivityStack != null) {
            for (WeakReference<Activity> activityReference : mActivityStack) {
                Activity activity = activityReference.get();
                if (activity != null) {
                    activity.finish();
                }
            }
            mActivityStack.clear();
        }
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            //关闭通知栏
            NotificationUtil.cancelNotification();
            //结束所有activity
            finishAllActivity();
            //从系统中kill掉应用程序
            android.os.Process.killProcess(android.os.Process.myPid());
            //退出JVM,释放所占内存资源,0表示正常退出
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ------------------------------------监听亮屏息屏广播--------------------------------------
     */
    public static boolean isScreenOn = true;
    private ScreenStatusReceiver screenStatusReceiver;

    private void registSreenStatusReceiver() {
        screenStatusReceiver = new ScreenStatusReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenStatusReceiver, intentFilter);
    }

    class ScreenStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_ON)) {//亮屏
                isScreenOn = true;
                //小天才,华为
                if (ChannelUtil.isXTC() || ChannelUtil.isHuaWei()) {
                    SuicideUtils.cancelKillApp();
                }
            } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {//息屏
                isScreenOn = false;
                //小天才
                if (ChannelUtil.isXTC()) {
                    SuicideUtils.startKillApp(BaseConstant.XTC_TIMES);
                }
                //华为
                if (ChannelUtil.isHuaWei()) {
                    SuicideUtils.startKillApp(BaseConstant.HUAWEI_TIMES);
                }
            }
        }
    }
}
