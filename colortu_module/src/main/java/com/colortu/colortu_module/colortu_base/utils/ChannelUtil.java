package com.colortu.colortu_module.colortu_base.utils;

import com.colortu.colortu_module.BuildConfig;

/**
 * @author : Code23
 * @time : 2020/11/25
 * @name : ChannelUtil
 * @Parameters :
 * @describe : 机型渠道
 */
public class ChannelUtil {
    public static String Channel() {
        String channel = BuildConfig.FLAVOR + "";
        return channel;
    }

    public static boolean isXTC() {
        return "xtc".equals(BuildConfig.FLAVOR);
    }

    public static boolean isHuaWei() {
        return "huawei".equals(BuildConfig.FLAVOR);
    }

    public static boolean isXiaoMi() {
        return "xiaomi".equals(BuildConfig.FLAVOR);
    }

    public static boolean isC360() {
        return "c360".equals(BuildConfig.FLAVOR);
    }

    public static boolean isDW() {
        return "dw".equals(BuildConfig.FLAVOR);
    }

    public static boolean isJY() {
        return "jy".equals(BuildConfig.FLAVOR);
    }

    public static boolean isSJTC() {
        return "sjtc".equals(BuildConfig.FLAVOR);
    }

    public static boolean isTeeMo() {
        return "teemo".equals(BuildConfig.FLAVOR);
    }

    public static boolean isYQ() {
        return "yq".equals(BuildConfig.FLAVOR);
    }

    public static boolean isZY() {
        return "zy".equals(BuildConfig.FLAVOR);
    }
}
