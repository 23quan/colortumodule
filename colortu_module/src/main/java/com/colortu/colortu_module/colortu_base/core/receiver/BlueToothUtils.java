package com.colortu.colortu_module.colortu_base.core.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.IntentFilter;

/**
 * @author : Code23
 * @time : 2021/6/17
 * @module : BlueToothUtils
 * @describe :蓝牙监听
 */
public class BlueToothUtils {
    //蓝牙广播
    private static BlueToothReceiver blueToothReceiver;

    /**
     * 注册蓝牙广播
     */
    public static void onRegisterBlueTooth(Context context) {
        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        blueToothReceiver = new BlueToothReceiver();
        context.registerReceiver(blueToothReceiver, intentFilter);
    }

    /**
     * 注销蓝牙广播
     */
    public static void onUnRegisterBlueTooth(Context context) {
        if (blueToothReceiver != null) {
            try {
                context.unregisterReceiver(blueToothReceiver);
                blueToothReceiver = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
