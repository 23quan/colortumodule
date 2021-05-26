package com.colortu.colortu_module.colortu_base.utils;

import android.os.Build;

/**
 * @author : Code23
 * @time : 2020/11/25
 * @name : OSUtil
 * @Parameters :
 * @describe :获取系统版本
 */
public class OSUtil {
    //获取系统版本
    public static final int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static final boolean isVersionM() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static final boolean isVersionN() {
        return Build.VERSION.SDK_INT >= 24;
    }

    public static final boolean isVersionO() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static final boolean isVersionP() {
        return Build.VERSION.SDK_INT >= 28;
    }

    public static final boolean isVersionQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    /**
     * 设备型号
     *
     * @return
     */
    public static final String getModel() {
        return Build.MODEL;
    }

    /**
     * 发布版本
     *
     * @return
     */
    public static final String getRelease() {
        return Build.VERSION.RELEASE;
    }
}
