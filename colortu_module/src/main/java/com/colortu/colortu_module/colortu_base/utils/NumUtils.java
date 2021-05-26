package com.colortu.colortu_module.colortu_base.utils;


/**
 * 数字转换
 */
public class NumUtils {
    public static String numToString(String num) {
        String strNum = "";
        if (num.equals("1")) {
            strNum = "一";
        } else if (num.equals("2")) {
            strNum = "二";
        } else if (num.equals("3")) {
            strNum = "三";
        } else if (num.equals("4")) {
            strNum = "四";
        } else if (num.equals("5")) {
            strNum = "五";
        } else if (num.equals("6")) {
            strNum = "六";
        } else {
            strNum = "";
        }
        return strNum;
    }
}
