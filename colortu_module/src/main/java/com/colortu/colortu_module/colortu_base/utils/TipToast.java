package com.colortu.colortu_module.colortu_base.utils;

import android.content.Context;
import android.widget.Toast;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;

/**
 * @author : Code23
 * @time : 2020/12/25
 * @module : TipToast
 * @describe :提示工具类
 */
public class TipToast {
    public static void tipToastShort(String content) {
        ToastTextThread(BaseApplication.getContext(), content);
    }

    public static void tipToastLong(String content) {
        ToastTextLongThread(BaseApplication.getContext(), content);
    }

    /**
     * 非主线程弹Toast short
     *
     * @param context
     * @param text
     */
    private static void ToastTextThread(final Context context, final String text) {
        BaseApplication.getMHandler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 非主线程弹Toast long
     *
     * @param context
     * @param text
     */
    private static void ToastTextLongThread(final Context context, final String text) {
        BaseApplication.getMHandler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
