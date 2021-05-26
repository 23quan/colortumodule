package com.colortu.colortu_module.colortu_base.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;

/**
 * @author : Code23
 * @time : 2021/1/5
 * @module : RestTimeService
 * @describe :小天才休息倒计时
 */
public class RestTimesService extends Service {
    private long testtime = 40 * 60 * 1000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        countDownTimer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private CountDownTimer countDownTimer = new CountDownTimer(testtime, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            BaseUIKit.startActivity(UIKitName.WORK_MAIN, UIKitName.BASE_RESTTIMES,
                    BaseConstant.BASE_RESTTIMES, BaseUIKit.OTHER, null);
            countDownTimer.start();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 停止服务
        stopSelf();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
