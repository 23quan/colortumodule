package com.colortu.colortu_module.colortu_base.utils;

import java.util.List;

/**
 * @author : Code23
 * @time : 2020/12/31
 * @module : EmptyUtils
 * @describe :判断为空工具类
 */
public class EmptyUtils {
    //判断对象是否为空或null
    public static boolean objectIsEmpty(Object o) {
        boolean objectempty;
        if (o != null) {
            objectempty = true;
        } else {
            objectempty = false;
        }
        return objectempty;
    }

    //判断字符串是否为null或空
    public static boolean stringIsEmpty(String s) {
        boolean stringempty;
        if (s != null) {
            if (!s.equals("")) {
                stringempty = true;
            } else {
                stringempty = false;
            }
        } else {
            stringempty = false;
        }
        return stringempty;
    }

    //判断数组是否为null或空
    public static boolean arrayIsEmpty(Object[] array) {
        boolean arrayempty;
        if (array != null) {
            if (array.length != 0) {
                arrayempty = true;
            } else {
                arrayempty = false;
            }
        } else {
            arrayempty = false;
        }
        return arrayempty;
    }

    //判断集合是否为null或空
    public static boolean listIsEmpty(List list) {
        boolean listempty;
        if (list != null) {
            if (list.size() > 0) {
                listempty = true;
            } else {
                listempty = false;
            }
        } else {
            listempty = false;
        }
        return listempty;
    }
}
