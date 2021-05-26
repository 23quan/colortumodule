package com.colortu.colortu_module.colortu_base.utils;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Code23
 * @time : 2020/11/25
 * @name : UmengUtil
 * @Parameters :
 * @describe : 友盟配置
 */
public class UmengUtil {
    public static void init(Application application) {
        UMConfigure.setLogEnabled(false);
        UMConfigure.setProcessEvent(true);
        // 支持在子进程中统计自定义事件
        UMConfigure.init(application, UMConfigure.DEVICE_TYPE_PHONE, null);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }

    /**
     * 订单量统计
     *
     * @param context
     * @param money
     */

    public static void order(Context context, int money) {
        Map<String, Object> order = new HashMap<String, Object>();
        order.put("money", money);
        MobclickAgent.onEventObject(context, "order", order);
    }

    public static void orderNum(Context context, int money) {
        Map<String, Object> order = new HashMap<String, Object>();
        order.put("money", money);
        MobclickAgent.onEventObject(context, "order_num", order);

    }

    public static void orderMoney(Context context, int money) {
        Map<String, Object> order = new HashMap<String, Object>();
        order.put("money", money);
        MobclickAgent.onEventObject(context, "order_money", order);
    }
}
