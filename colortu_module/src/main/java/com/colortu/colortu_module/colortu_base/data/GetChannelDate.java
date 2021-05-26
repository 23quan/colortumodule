package com.colortu.colortu_module.colortu_base.data;

import android.content.Context;

/**
 * @author : Code23
 * @time : 2021/1/14
 * @module : GetChannelDate
 * @describe :存取渠道区分水滴屏工具类
 */
public class GetChannelDate {
    /**
     * 小米-区分水滴屏与非水滴屏的接口
     *
     * @param context
     * @return 0表示非水滴屏；1表示水滴屏
     */
    public static Boolean isWaterDrop(Context context) {
        boolean whether = false;
        int type = android.provider.Settings.Global.getInt(context.getContentResolver(), "com.xxun.is_special_screen", 0);
        if (type == 1) {
            whether = true;
        }
        return whether;
    }
}
