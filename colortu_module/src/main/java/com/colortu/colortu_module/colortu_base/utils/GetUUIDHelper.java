package com.colortu.colortu_module.colortu_base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.utils.string.Md5Util;

/**
 * @author : Code23
 * @time : 2020/12/17
 * @name : GetUUIDHelper
 * @Parameters :
 * @describe : 生成设备唯一id
 */
public class GetUUIDHelper {
    private static String imeiid, deviceuuid, androidid;

    public static synchronized String getUUID() {
        String uuid = createUUID();
        return uuid;
    }

    public static String createUUID() {
        StringBuffer uuid = new StringBuffer();
        String md5Uuid = "";

        imeiid = getImeiId();
        androidid = getAndroidId();
        deviceuuid = getDeviceUUID();

        if (EmptyUtils.stringIsEmpty(imeiid)) {
            uuid.append(imeiid);
        } else {
            if (EmptyUtils.stringIsEmpty(androidid)) {
                uuid.append(androidid);
            }
        }

        if (EmptyUtils.stringIsEmpty(deviceuuid)) {
            uuid.append(deviceuuid);
        }
        md5Uuid = Md5Util.encodeByMD5(uuid.toString());
        return md5Uuid;
    }

    /**
     * 获取IMEI号
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getImeiId() {
        String imeid = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) BaseApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            imeid = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imeid;
    }

    /**
     * 获得设备的AndroidId
     *
     * @return 设备的AndroidId
     */
    private static String getAndroidId() {
        String androidid = "";
        try {
            androidid = Settings.Secure.getString(BaseApplication.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return androidid;
    }

    /**
     * 获得设备硬件uuid
     * 使用硬件信息，计算出一个随机数
     *
     * @return 设备硬件uuid
     */
    private static String getDeviceUUID() {
        StringBuffer stringBuffer = new StringBuffer();

        //主板
        if (EmptyUtils.stringIsEmpty(Build.BOARD)) {
            stringBuffer.append(stringBuffer);
        }

        //系统定制商
        if (EmptyUtils.stringIsEmpty(Build.BRAND)) {
            stringBuffer.append(Build.BRAND);
        }

        //cpu指令集
        if (EmptyUtils.stringIsEmpty(Build.CPU_ABI)) {
            stringBuffer.append(Build.CPU_ABI);
        }

        //设置参数
        if (EmptyUtils.stringIsEmpty(Build.DEVICE)) {
            stringBuffer.append(Build.DEVICE);
        }

        //硬件识别码
        if (EmptyUtils.stringIsEmpty(Build.FINGERPRINT)) {
            stringBuffer.append(Build.FINGERPRINT);
        }

        //硬件名称
        if (EmptyUtils.stringIsEmpty(Build.HARDWARE)) {
            stringBuffer.append(Build.HARDWARE);
        }

        //硬件序列号
        if (EmptyUtils.stringIsEmpty(Build.SERIAL)) {
            stringBuffer.append(Build.SERIAL);
        }

        //手机制造商
        if (EmptyUtils.stringIsEmpty(Build.PRODUCT)) {
            stringBuffer.append(Build.PRODUCT);
        }

        //硬件名称
        if (EmptyUtils.stringIsEmpty(Build.HARDWARE)) {
            stringBuffer.append(Build.HARDWARE);
        }

        return stringBuffer.toString();
    }
}
