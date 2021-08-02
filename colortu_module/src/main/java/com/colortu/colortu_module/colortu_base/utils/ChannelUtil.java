package com.colortu.colortu_module.colortu_base.utils;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.umeng.analytics.AnalyticsConfig;

/**
 * @author : Code23
 * @time : 2020/11/25
 * @name : ChannelUtil
 * @Parameters :
 * @describe : 机型渠道
 */
public class ChannelUtil {
    public static String Channel() {
        String channel = AnalyticsConfig.getChannel(BaseApplication.getContext());
        return channel;
    }

    public static boolean isXTC() {
        return "xtc".equals(Channel());
    }

    public static boolean isHuaWei() {
        return "huawei".equals(Channel());
    }

    public static boolean isXiaoMi() {
        return "xiaomi".equals(Channel());
    }

    public static boolean isC360() {
        return "c360".equals(Channel());
    }

    public static boolean isDW() {
        return "dw".equals(Channel());
    }

    public static boolean isJY() {
        return "jy".equals(Channel());
    }

    public static boolean isSJTC() {
        return "sjtc".equals(Channel());
    }

    public static boolean isTeeMo() {
        return "teemo".equals(Channel());
    }

    public static boolean isYQ() {
        return "yq".equals(Channel());
    }

    public static boolean isZY() {
        return "zy".equals(Channel());
    }

    public static boolean isReadBoy() {
        return "readboy".equals(Channel());
    }
}
