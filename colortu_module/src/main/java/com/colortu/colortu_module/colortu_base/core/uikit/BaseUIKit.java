package com.colortu.colortu_module.colortu_base.core.uikit;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.Tools;
import com.colortu.colortu_module.colortu_base.bean.QrcodeAddUserBean;

/**
 * @author : Code23
 * @time : 2021/3/11
 * @module : BaseUIKit
 * @describe :作业ui接口/制定化入口
 */
public class BaseUIKit {
    //不跳转(a界面跳转b界面,b界面销毁回到a界面)
    public static final int NONE = 1;
    //跳转(a界面跳转b界面,b界面销毁跳转c界面)
    public static final int HAVE = 2;
    //跳转(a界面跳转b界面)
    public static final int OTHER = 3;

    //当前版本号
    private static String nowVersion;
    //最大有效版本号
    private static String maxVersion;
    //最小有效版本号
    private static String minVersion;
    //0版本号相同，1当前版本号大于最大版本号，-1当前版本号小于最大版本号
    private static int maxcode;
    //0版本号相同，1当前版本号大于最小版本号，-1当前版本号小于最小版本号
    private static int mincode;

    //before_enter (to_page页面显示前), after_enter（to_page页面显示后）, after_exit（from_page页面退出后）
    private static String whenshow;
    //to_page页面显示前
    public static final String BeforeEnter = "before_enter";
    //to_page页面显示后
    public static final String AfterEnter = "after_enter";
    //from_page页面退出后
    public static final String AfterExit = "after_exit";

    //是否显示二维码
    private static boolean isshow;
    //1:登录二维码,2：解锁二维码,3:广告二维码,4:广告（点击图片才打开新页面显示二维码),5：文字提示
    private static int insertPageType;
    //倒计时
    public static CountDownTimer countDownTimer;

    /**
     * 跳转界面(正常跳转)
     *
     * @param fromPage    跳转前页面类名
     * @param toPage      跳转至页面的类名
     * @param toPageRoute 跳转至页面的路由
     * @param type        1.不跳转(a界面跳转b界面,b界面销毁回到a界面),2.跳转(a界面跳转b界面,b界面销毁跳转c界面),3.跳转(a界面跳转b界面)
     * @param bundle      传送数据
     */
    public static void startActivity(String fromPage, String toPage, String toPageRoute, int type, Bundle bundle) {
        initData(fromPage, toPage, toPageRoute, "", type, bundle, null, 0, 1);
    }

    /**
     * 跳转界面(正常跳转)
     *
     * @param fromPage       跳转前页面类名
     * @param toPage         跳转至页面的类名
     * @param toPageRoute    跳转至页面的路由
     * @param afterPageRoute 销毁后跳转界面路由
     * @param type           1.不跳转(a界面跳转b界面,b界面销毁回到a界面),2.跳转(a界面跳转b界面,b界面销毁跳转c界面),3.跳转(a界面跳转b界面)
     * @param bundle         传送数据
     */
    public static void startActivity(String fromPage, String toPage, String toPageRoute, String afterPageRoute, int type, Bundle bundle) {
        initData(fromPage, toPage, toPageRoute, afterPageRoute, type, bundle, null, 0, 1);
    }

    /**
     * 跳转界面(onActivityResult参数返回)
     *
     * @param fromPage    跳转前页面类名
     * @param toPage      跳转至页面的类名
     * @param toPageRoute 跳转至页面的路由
     * @param type        1.不跳转(a界面跳转b界面,b界面销毁回到a界面),2.跳转(a界面跳转b界面,b界面销毁跳转c界面),3.跳转(a界面跳转b界面)
     * @param bundle      传送数据
     * @param activity    跳转activity
     * @param code        标识
     */
    public static void startActivity(String fromPage, String toPage, String toPageRoute, int type, Bundle bundle, Activity activity, int code) {
        initData(fromPage, toPage, toPageRoute, "", type, bundle, activity, code, 2);
    }

    /**
     * @param fromPage       跳转前页面类名
     * @param toPage         跳转至页面的类名
     * @param toPageRoute    跳转至页面的路由
     * @param afterPageRoute 销毁后跳转界面路由
     * @param type           1.不跳转(a界面跳转b界面,b界面销毁回到a界面),2.跳转(a界面跳转b界面,b界面销毁跳转c界面),3.跳转(a界面跳转b界面)
     * @param bundle         传送数据
     * @param activity       跳转activity
     * @param code           标识
     * @param sort           1none,2result,3task
     */
    private static void initData(String fromPage, String toPage, String toPageRoute, String afterPageRoute,
                                 int type, Bundle bundle, Activity activity, int code, int sort) {
        isshow = false;
        nowVersion = Tools.getVersionCode(BaseApplication.getContext());
        whenshow = "";
        BaseApplication.setPageInsertConfigBean(null);

        if (BaseApplication.getPageInsertConfigBeanList() != null) {
            for (int i = 0; i < BaseApplication.getPageInsertConfigBeanList().size(); i++) {
                if (fromPage.equals(BaseApplication.getPageInsertConfigBeanList().get(i).getFromPage())
                        && toPage.equals(BaseApplication.getPageInsertConfigBeanList().get(i).getToPage())) {

                    maxVersion = BaseApplication.getPageInsertConfigBeanList().get(i).getValidAppVersionMax() + "";
                    minVersion = BaseApplication.getPageInsertConfigBeanList().get(i).getValidAppVersionMin() + "";

                    //判断版本不能为空
                    if (EmptyUtils.stringIsEmpty(maxVersion) && EmptyUtils.stringIsEmpty(minVersion)) {
                        maxcode = Tools.compareVersion(nowVersion, maxVersion);
                        mincode = Tools.compareVersion(nowVersion, minVersion);

                        if ((maxcode == 0 || maxcode == -1) && (mincode == 0 || mincode == 1)) {
                            isshow = true;
                            whenshow = BaseApplication.getPageInsertConfigBeanList().get(i).getWhenShow();
                            insertPageType = BaseApplication.getPageInsertConfigBeanList().get(i).getInsertPageType();
                            BaseApplication.setPageInsertConfigBean(BaseApplication.getPageInsertConfigBeanList().get(i));
                        }
                    } else {
                        isshow = true;
                        whenshow = BaseApplication.getPageInsertConfigBeanList().get(i).getWhenShow();
                        insertPageType = BaseApplication.getPageInsertConfigBeanList().get(i).getInsertPageType();
                        BaseApplication.setPageInsertConfigBean(BaseApplication.getPageInsertConfigBeanList().get(i));
                    }
                    break;
                }
            }
        }

        if (isshow) {
            if (whenshow.equals(BeforeEnter)) {
                toCodeActivity(insertPageType, toPage, toPageRoute, type, bundle);
            } else {
                toOtherActivity(toPage, toPageRoute, afterPageRoute, type, bundle, activity, code, sort);
            }
        } else {
            toOtherActivity(toPage, toPageRoute, afterPageRoute, type, bundle, activity, code, sort);
        }
    }

    /**
     * 分类二维码界面
     *
     * @param insertPageType 分类：1登录,2解锁,3广告,4广告（点击图片打开新页面),5文字
     * @param toPage         跳转至页面的类名
     * @param toPageRoute    跳转至页面的路由
     * @param type           1.不跳转(a界面跳转b界面,b界面销毁回到a界面),2.跳转(a界面跳转b界面,b界面销毁跳转c界面),3.跳转(a界面跳转b界面)
     * @param bundle         传送数据
     */
    private static void toCodeActivity(int insertPageType, String toPage, String toPageRoute, int type, Bundle bundle) {
        String routeName = "";
        switch (insertPageType) {
            case 1://登录二维码
                routeName = BaseConstant.QRCODE_LOGIN;
                break;
            case 2://解锁二维码
                routeName = BaseConstant.QRCODE_PAY;
                break;
            case 3://广告二维码
            case 4://广告（点击图片才打开新页面显示二维码)
                routeName = BaseConstant.QRCODE_AD;
                break;
            case 5://文字提示
                routeName = BaseConstant.QRCODE_TEXTTIP;
                break;
        }
        codeActivity(routeName, toPage, toPageRoute, type, bundle);
    }

    /**
     * 跳转二维码界面
     *
     * @param routeName   跳转至页面的路由
     * @param toPage      跳转至页面的类名
     * @param toPageRoute 跳转至页面的路由
     * @param type        1.不跳转(a界面跳转b界面,b界面销毁回到a界面),2.跳转(a界面跳转b界面,b界面销毁跳转c界面),3.跳转(a界面跳转b界面)
     * @param bundle      传送数据
     */
    private static void codeActivity(String routeName, String toPage, String toPageRoute, int type, Bundle bundle) {
        ARouter.getInstance().build(routeName)
                .withString("toPage", toPage)
                .withString("toPageRoute", toPageRoute)
                .withInt("type", type)
                .withBundle("bundle", bundle)
                .navigation();
    }

    /**
     * 显示二维码界面(之后显示),显示二维码界面(退出后显示)
     *
     * @param pageInsertConfigBean 控制数据bean
     * @param whenshow             显示时间
     */
    public static void startAfterActivity(final QrcodeAddUserBean.DataBean.ChannelConfigBean.PageInsertConfigBean pageInsertConfigBean, String whenshow) {
        if (pageInsertConfigBean != null) {
            if (pageInsertConfigBean.getWhenShow().equals(whenshow)) {
                if (pageInsertConfigBean.getDelayTime() == 0) {
                    toCodeActivity(pageInsertConfigBean.getInsertPageType(), "", "", NONE, null);
                    BaseApplication.setPageInsertConfigBean(null);
                } else {
                    countDownTimer = new CountDownTimer(pageInsertConfigBean.getDelayTime() * 1000, 1000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            toCodeActivity(pageInsertConfigBean.getInsertPageType(), "", "", NONE, null);
                            BaseApplication.setPageInsertConfigBean(null);
                        }
                    }.start();
                }
            }
        }
    }

    /**
     * 跳转其他activity
     *
     * @param toPage         跳转至页面的类名
     * @param toPageRoute    跳转至页面的路由
     * @param afterPageRoute 销毁后跳转界面路由
     * @param type           1.不跳转(a界面跳转b界面,b界面销毁回到a界面),2.跳转(a界面跳转b界面,b界面销毁跳转c界面),3.跳转(a界面跳转b界面)
     * @param bundle         传送数据
     * @param activity       跳转activity
     * @param code           标识
     * @param sort           1none,2result
     */
    private static void toOtherActivity(String toPage, String toPageRoute, String afterPageRoute, int type, Bundle bundle, Activity activity, int code, int sort) {
        switch (sort) {
            case 1:
                noResultStartActivity(toPage, toPageRoute, afterPageRoute, type, bundle);
                break;
            case 2:
                onResultStartActivity(toPage, toPageRoute, afterPageRoute, type, bundle, activity, code);
                break;
        }
    }

    /**
     * 跳转界面(正常跳转)
     *
     * @param toPage         跳转至页面的类名
     * @param toPageRoute    跳转至页面的路由
     * @param afterPageRoute 销毁后跳转界面路由
     * @param type           1.不跳转(a界面跳转b界面,b界面销毁回到a界面),2.跳转(a界面跳转b界面,b界面销毁跳转c界面),3.跳转(a界面跳转b界面)
     * @param bundle         传送数据
     */
    private static void noResultStartActivity(String toPage, String toPageRoute, String afterPageRoute, int type, Bundle bundle) {
        ARouter.getInstance()
                .build(toPageRoute)
                .withString("toPage", toPage)
                .withString("toPageRoute", toPageRoute)
                .withString("afterPageRoute", afterPageRoute)
                .withInt("type", type)
                .withBundle("bundle", bundle)
                .navigation();
    }

    /**
     * 跳转界面(onActivityResult参数返回)
     *
     * @param toPage         跳转至页面的类名
     * @param toPageRoute    跳转至页面的路由
     * @param afterPageRoute 销毁后跳转界面路由
     * @param type           1.不跳转(a界面跳转b界面,b界面销毁回到a界面),2.跳转(a界面跳转b界面,b界面销毁跳转c界面),3.跳转(a界面跳转b界面)
     * @param bundle         传送数据
     * @param activity       跳转activity
     * @param code           标识
     */
    private static void onResultStartActivity(String toPage, String toPageRoute, String afterPageRoute, int type, Bundle bundle, Activity activity, int code) {
        ARouter.getInstance()
                .build(toPageRoute)
                .withString("toPage", toPage)
                .withString("toPageRoute", toPageRoute)
                .withString("afterPageRoute", afterPageRoute)
                .withInt("type", type)
                .withBundle("bundle", bundle)
                .navigation(activity, code);
    }
}
