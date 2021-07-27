package com.colortu.colortu_module.colortu_base.data;

import android.text.TextUtils;

import com.colortu.colortu_module.colortu_base.utils.SharedPreferencesUtils;

/**
 * @author : Code23
 * @time : 2020/12/28
 * @module : GetBeanDate
 * @describe :存取信息工具类
 */
public class GetBeanDate {
    //用户信息
    private static String USERINFOJSON = "userInfoJson";
    //用户id
    private static String USERUUID = "useruuid";
    //openid
    private static String OPENID = "openid";
    //用户头像
    private static String USERHEAD = "userhead";
    //是否登录
    private static String ISLOGIN = "islogin";
    //登录二维码
    private static String LOGINCODE = "logincode";
    //支付二维码
    private static String PAYCODE = "paycode";
    //是否解锁录音翻译会员
    private static String ISTRANSLATEVIP = "istranslatevip";
    //录音翻译会员时间
    private static String TRANSLATEVIPTIME = "translateviptime";
    //是否解锁教辅会员
    private static String ISBOOKVIP = "isbookvip";
    //免费课数
    private static String FREEEXAMCOUNT = "freeExamCount";
    //我的教辅数据
    private static String MINETEACHBOOK = "mineteachbook";
    //是否接受协议
    private static String ACCEPT = "accept";
    //自习广场筛选名字
    private static String STUDYFILTRATENAME = "studyfiltratename";
    //是否同意使用流量
    private static String AGREENETWORK = "agreenetwork";
    //是否退出自习室
    private static String ISEXITROOM = "isexitroom";
    //自习室id
    private static String STUDYROOMID = "studyroomid";
    //自习室类别
    private static String STUDYROOMIDCHANNEL = "studyroomchannel";

    /**
     * 存储用户所有信息
     *
     * @param userinfo
     */
    public static void putUserInfo(String userinfo) {
        SharedPreferencesUtils.getInstance().putString(USERINFOJSON, userinfo);
    }

    /**
     * 获取用户所有信息
     *
     * @return
     */
    public static String getUserInfo() {
        String userinfo = "";
        userinfo = SharedPreferencesUtils.getInstance().getString(USERINFOJSON);
        if (TextUtils.isEmpty(userinfo)) {
            return "";
        } else {
            return userinfo;
        }
    }

    /**
     * 存储用户uuid
     *
     * @param uuid
     */
    public static void putUserUuid(String uuid) {
        SharedPreferencesUtils.getInstance().putString(USERUUID, uuid);
    }

    /**
     * 获取用户uuid
     *
     * @return
     */
    public static String getUserUuid() {
        String uuid = "";
        uuid = SharedPreferencesUtils.getInstance().getString(USERUUID);
        if (TextUtils.isEmpty(uuid)) {
            return "";
        } else {
            return uuid;
        }
    }

    /**
     * 存储用户openid
     *
     * @param openid
     */
    public static void putOpenid(String openid) {
        SharedPreferencesUtils.getInstance().putString(OPENID, openid);
    }

    /**
     * 获取用户openid
     *
     * @return
     */
    public static String getOpenid() {
        String openid = "";
        openid = SharedPreferencesUtils.getInstance().getString(OPENID);
        if (TextUtils.isEmpty(openid)) {
            return "";
        } else {
            return openid;
        }
    }

    /**
     * 存储用户头像
     *
     * @param head
     */
    public static void putUserHead(String head) {
        SharedPreferencesUtils.getInstance().putString(USERHEAD, head);
    }

    /**
     * 获取用户头像
     *
     * @return
     */
    public static String getUserHead() {
        String head = "";
        head = SharedPreferencesUtils.getInstance().getString(USERHEAD);
        if (TextUtils.isEmpty(head)) {
            return "";
        } else {
            return head;
        }
    }

    /**
     * 存储是否登录
     *
     * @param islogin
     */
    public static void putIsLogin(boolean islogin) {
        SharedPreferencesUtils.getInstance().putBoolean(ISLOGIN, islogin);
    }

    /**
     * 获取是否登录
     *
     * @return
     */
    public static Boolean getIsLogin() {
        boolean islogin = SharedPreferencesUtils.getInstance().getBoolean(ISLOGIN);
        return islogin;
    }

    /**
     * 存储登录二维码
     *
     * @param code
     */
    public static void putLoginCode(String code) {
        SharedPreferencesUtils.getInstance().putString(LOGINCODE, code);
    }

    /**
     * 获取登录二维码
     *
     * @return
     */
    public static String getLoginCode() {
        String code = "";
        code = SharedPreferencesUtils.getInstance().getString(LOGINCODE);
        if (TextUtils.isEmpty(code)) {
            return "";
        } else {
            return code;
        }
    }

    /**
     * 存储支付二维码
     *
     * @param code
     */
    public static void putPayCode(String code) {
        SharedPreferencesUtils.getInstance().putString(PAYCODE, code);
    }

    /**
     * 获取支付二维码
     *
     * @return
     */
    public static String getPayCode() {
        String code = "";
        code = SharedPreferencesUtils.getInstance().getString(PAYCODE);
        if (TextUtils.isEmpty(code)) {
            return "";
        } else {
            return code;
        }
    }

    /**
     * 存储是否解锁录音翻译会员
     *
     * @param isvip
     */
    public static void putIsTranslateVIP(int isvip) {
        SharedPreferencesUtils.getInstance().putInt(ISTRANSLATEVIP, isvip);
    }

    /**
     * 获取是否解锁录音翻译会员
     *
     * @return
     */
    public static int getIsTranslateVIP() {
        return SharedPreferencesUtils.getInstance().getInt(ISTRANSLATEVIP);
    }

    /**
     * 存储录音翻译会员时间
     *
     * @param time
     */
    public static void putTranslateVIPTime(String time) {
        SharedPreferencesUtils.getInstance().putString(TRANSLATEVIPTIME, time);
    }

    /**
     * 获取录音翻译会员时间
     *
     * @return
     */
    public static String getTranslateVIPTime() {
        String time = "";
        time = SharedPreferencesUtils.getInstance().getString(TRANSLATEVIPTIME);
        if (TextUtils.isEmpty(time)) {
            return "";
        } else {
            return time;
        }
    }

    /**
     * 存储是否解锁教辅会员
     *
     * @param isvip
     */
    public static void putIsBookVIP(int isvip) {
        SharedPreferencesUtils.getInstance().putInt(ISBOOKVIP, isvip);
    }

    /**
     * 获取是否解锁教辅会员
     *
     * @return
     */
    public static int getIsBookVIP() {
        return SharedPreferencesUtils.getInstance().getInt(ISBOOKVIP);
    }

    /**
     * 存储免费课数
     *
     * @param count
     */
    public static void putFreeExamCount(int count) {
        SharedPreferencesUtils.getInstance().putInt(FREEEXAMCOUNT, count);
    }

    /**
     * 获取免费课数
     *
     * @return
     */
    public static int getFreeExamCount() {
        return SharedPreferencesUtils.getInstance().getInt(FREEEXAMCOUNT);
    }

    /**
     * 存储我的教辅数据
     *
     * @param teachbook
     */
    public static void putMineTeachBook(String teachbook) {
        SharedPreferencesUtils.getInstance().putString(MINETEACHBOOK, teachbook);
    }

    /**
     * 获取我的教辅数据
     *
     * @return
     */
    public static String getMineTeachBook() {
        String teachbook = "";
        teachbook = SharedPreferencesUtils.getInstance().getString(MINETEACHBOOK);
        if (TextUtils.isEmpty(teachbook)) {
            return "";
        } else {
            return teachbook;
        }
    }

    /**
     * 存储是否接受协议
     *
     * @param accept
     */
    public static void putAccept(boolean accept) {
        SharedPreferencesUtils.getInstance().putBoolean(ACCEPT, accept);
    }

    /**
     * 获取是否接受协议
     *
     * @return
     */
    public static Boolean getAccept() {
        boolean accept = SharedPreferencesUtils.getInstance().getBoolean(ACCEPT);
        return accept;
    }

    /**
     * 存储自习广场筛选名字
     *
     * @param studyfiltratename
     */
    public static void putStudyFiltrateName(String studyfiltratename) {
        SharedPreferencesUtils.getInstance().putString(STUDYFILTRATENAME, studyfiltratename);
    }

    /**
     * 获取自习广场筛选名字
     *
     * @return
     */
    public static String getStudyFiltrateName() {
        String studyfiltratename = "";
        studyfiltratename = SharedPreferencesUtils.getInstance().getString(STUDYFILTRATENAME);
        if (TextUtils.isEmpty(studyfiltratename)) {
            return "";
        } else {
            return studyfiltratename;
        }
    }

    /**
     * 存储是否同意使用流量
     *
     * @param agreenetwork
     */
    public static void putAgreeNetWork(boolean agreenetwork) {
        SharedPreferencesUtils.getInstance().putBoolean(AGREENETWORK, agreenetwork);
    }

    /**
     * 获取是否同意使用流量
     *
     * @return
     */
    public static Boolean getAgreeNetWork() {
        boolean agreenetwork = SharedPreferencesUtils.getInstance().getBoolean(AGREENETWORK);
        return agreenetwork;
    }

    /**
     * 存储是否退出自习室
     *
     * @param isexitroom
     */
    public static void putIsExitRoom(boolean isexitroom) {
        SharedPreferencesUtils.getInstance().putBoolean(ISEXITROOM, isexitroom);
    }

    /**
     * 获取是否退出自习室
     *
     * @return
     */
    public static Boolean getIsExitRoom() {
        boolean isexitroom = SharedPreferencesUtils.getInstance().getBoolean(ISEXITROOM);
        return isexitroom;
    }

    /**
     * 存储自习室id
     *
     * @param studyroomid
     */
    public static void putStudyRoomId(int studyroomid) {
        SharedPreferencesUtils.getInstance().putInt(STUDYROOMID, studyroomid);
    }

    /**
     * 获取自习室id
     *
     * @return
     */
    public static int getStudyRoomId() {
        return SharedPreferencesUtils.getInstance().getInt(STUDYROOMID);
    }

    /**
     * 存储自习室类别
     *
     * @param studyroomchannel
     */
    public static void putStudyRoomChannel(String studyroomchannel) {
        SharedPreferencesUtils.getInstance().putString(STUDYROOMIDCHANNEL, studyroomchannel);
    }

    /**
     * 获取自习室类别
     *
     * @return
     */
    public static String getStudyRoomChannel() {
        String studyroomchannel = "";
        studyroomchannel = SharedPreferencesUtils.getInstance().getString(STUDYROOMIDCHANNEL);
        if (TextUtils.isEmpty(studyroomchannel)) {
            return "";
        } else {
            return studyroomchannel;
        }
    }

    /**
     * --------------------------------------------听写模块--------------------------------------------------
     */

    //是否第一次进来
    private static String ISFIRST = "isfirst";
    //已选年级
    private static String CHOOSEGRADE = "choosegrade";
    //已选版本
    private static String CHOOSEVERSION = "chooseversion";

    /**
     * 存储是否第一次进来
     *
     * @param IsFirst
     */
    public static void putIsFirst(boolean IsFirst) {
        SharedPreferencesUtils.getInstance().putBoolean(ISFIRST, IsFirst);
    }

    /**
     * 获取是否第一次进来
     *
     * @return
     */
    public static Boolean getIsFirst() {
        boolean IsFirst = SharedPreferencesUtils.getInstance().getBoolean(ISFIRST);
        return IsFirst;
    }

    /**
     * 存储已选年级
     *
     * @param grade
     */
    public static void putChooseGrade(int grade) {
        SharedPreferencesUtils.getInstance().putInt(CHOOSEGRADE, grade);
    }

    /**
     * 获取已选年级
     *
     * @return
     */
    public static int getChooseGrade() {
        return SharedPreferencesUtils.getInstance().getInt(CHOOSEGRADE);
    }

    /**
     * 存储已选版本
     *
     * @param version
     */
    public static void putChooseVersion(int version) {
        SharedPreferencesUtils.getInstance().putInt(CHOOSEVERSION, version);
    }

    /**
     * 获取已选版本
     *
     * @return
     */
    public static int getChooseVersion() {
        return SharedPreferencesUtils.getInstance().getInt(CHOOSEVERSION);
    }
}
