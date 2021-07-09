package com.colortu.colortu_module.colortu_base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Code23
 * @time : 2020/11/17
 * @module : Tools
 * @describe :工具类
 */
public class Tools {
    /**
     * 返回当前程序版本号
     */
    public static String getVersionCode(Context context) {
        String versioncode = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versioncode = packageInfo.versionName + "." + packageInfo.versionCode;
        } catch (Exception e) {
        }
        return versioncode;
    }

    /**
     * 版本号比较
     *
     * @param v1
     * @param v2
     * @return 0代表相等，1代表左边大，-1代表右边大
     */
    public static int compareVersion(String v1, String v2) {
        if (v1.equals(v2)) {
            return 0;
        }
        String[] version1Array = v1.split("[._]");
        String[] version2Array = v2.split("[._]");
        int index = 0;
        int minLen = Math.min(version1Array.length, version2Array.length);
        long diff = 0;

        while (index < minLen && (diff = Long.parseLong(version1Array[index]) - Long.parseLong(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            for (int i = index; i < version1Array.length; i++) {
                if (Long.parseLong(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Long.parseLong(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    /**
     * 将时间戳转换为时间
     *
     * @param time 时间戳
     * @param type 转换时间格式，例如（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String stampToDateS(String time, String type) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        //如果它本来就是long类型的,则不用写这一步
        long lt = new Long(time);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间转换为时间戳
     *
     * @param time 时间
     * @param type 转换时间戳格式，例如（yyyy-MM-dd HH:mm:ss）
     * @return
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToStamp(String time, String type) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /***
     * 获取当前日期距离过期时间的日期差值
     * @param endTime
     * @return
     */
    public static String dateDiff(String endTime) {
        String time = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            String nowtime = formatter.format(date);
            //开始的时间
            Date date1 = formatter.parse(nowtime);
            //结束的时间
            Date date2 = formatter.parse(endTime);
            // 这样得到的差值是微秒级别
            long diff = date2.getTime() - date1.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            if (days < 4) {
                if (days != 0) {
                    time = days + "天";
                } else if (hours != 0) {
                    time = hours + "小时";
                } else if (minutes != 0) {
                    time = minutes + "分钟";
                }
                return time;
            } else {
                return "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /***
     * 获取当前日期距离过期时间的日期差值
     * @param endTime
     * @return
     */
    public static long dateDiff2(String endTime) {
        long time = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            String nowtime = formatter.format(date);
            //开始的时间
            Date date1 = formatter.parse(nowtime);
            //结束的时间
            Date date2 = formatter.parse(endTime);
            // 这样得到的差值是微秒级别
            long diff = date2.getTime() - date1.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            Long seconds = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);

            time = (days * 24 * 60 * 60) + (hours * 60 * 60) + (minutes * 60) + seconds;
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /***
     * 获取当前日期距离过期时间的日期差值
     * @param endTime
     * @return
     */
    public static String dateDiff3(String endTime) {
        String time = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            String nowtime = formatter.format(date);
            //开始的时间
            Date date1 = formatter.parse(nowtime);
            //结束的时间
            Date date2 = formatter.parse(endTime);
            // 这样得到的差值是微秒级别
            long diff = date1.getTime() - date2.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            Long seconds = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);

            if (days != 0) {
                time = time + days + "天";
            }
            if (hours != 0) {
                time = time + hours + "小时";
            }
            if (minutes != 0) {
                time = time + minutes + "分钟";
            }
            if (seconds > 0) {
                time = time + seconds + "秒";
            }
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 检查SD卡是否有足够的空间
     *
     * @return
     */
    public static int checkFreeSpace() {
        //预警值
        long warning = 100 * 1024 * 1024;
        //低于退出app值
        long minimum = 50 * 1024 * 1024;
        //内存剩余空间值
        long freespace = Environment.getExternalStorageDirectory().getFreeSpace();
        if (freespace > warning) {
            return 0;
        } else {
            if (freespace < warning && freespace > minimum) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    /**
     * 判断是否字符串包涵某段字符
     *
     * @param s1 判断字符串
     * @param s2 被判断字符串
     * @return
     */
    public static String stringIndexOf(String s1, String s2) {
        if (EmptyUtils.stringIsEmpty(s1)) {
            if (s1.indexOf(s2) != -1) {
                return s1;
            } else {
                return s2 + s1;
            }
        } else {
            return "";
        }
    }
}
