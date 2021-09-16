package com.colortu.colortu_module.colortu_base.core.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @author : Code23
 * @time : 2021/6/17
 * @module : BlueToothReceiver
 * @describe :蓝牙监听
 */
public class BlueToothReceiver extends BroadcastReceiver {
    //蓝牙广播
    private static BlueToothReceiver blueToothReceiver;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int bluetoothState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, 0);
        if (action.equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED) && bluetoothState == BluetoothAdapter.STATE_DISCONNECTED) {
            if (onBluetoothListener != null) {
                onBluetoothListener.onBluetoothDisConnected();
            }
        }
    }

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
    public static void unRegisterBlueTooth(Context context) {
        if (blueToothReceiver != null) {
            try {
                context.unregisterReceiver(blueToothReceiver);
                blueToothReceiver = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static OnBluetoothListener onBluetoothListener;

    public static void setOnBluetoothListener(OnBluetoothListener onBluetoothListener) {
        BlueToothReceiver.onBluetoothListener = onBluetoothListener;
    }

    public interface OnBluetoothListener {
        void onBluetoothDisConnected();
    }
}
