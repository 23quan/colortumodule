package com.colortu.colortu_module.colortu_base.utils;

import android.os.CountDownTimer;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;

/**
 * @author : Code23
 * @time : 2021/4/29
 * @module : SuicideUtils
 * @describe :app自杀工具类
 */
public class SuicideUtils {
    /**
     * 启动自杀倒计时
     */
    public static void onStartKill() {
        if (ChannelUtil.isXTC() || ChannelUtil.isHuaWei()) {
            BaseApplication.setIsListen(false);
            if (!BaseApplication.isScreenOn) {
                if (ChannelUtil.isXTC()) {
                    startKillApp(BaseConstant.XTC_TIMES);
                }

                if (ChannelUtil.isHuaWei()) {
                    startKillApp(BaseConstant.HUAWEI_TIMES);
                }
            }
        }
    }

    /**
     * 取消自杀倒计时
     */
    public static void onCancelKill() {
        if (ChannelUtil.isXTC() || ChannelUtil.isHuaWei()) {
            BaseApplication.setIsListen(true);
            cancelKillApp();
        }
    }

    /**
     * 启动自杀倒计时
     *
     * @param times 时间
     */
    public static void startKillApp(long times) {
        if (ChannelUtil.isXTC() || ChannelUtil.isHuaWei()) {
            if (!BaseApplication.isListen) {
                onCountDownTimer(times);
            }
        }
    }

    /**
     * 取消自杀倒计时
     */
    public static void cancelKillApp() {
        if (ChannelUtil.isXTC() || ChannelUtil.isHuaWei()) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        }
    }

    /**
     * 倒计时
     */
    private static CountDownTimer countDownTimer;

    private static void onCountDownTimer(long times) {
        countDownTimer = new CountDownTimer(times, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                BaseApplication.getInstance().exitApp();
            }
        }.start();
    }
}
