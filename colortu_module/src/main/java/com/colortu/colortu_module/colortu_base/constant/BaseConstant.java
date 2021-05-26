package com.colortu.colortu_module.colortu_base.constant;

/**
 * @author : Code23
 * @time : 2020/11/24
 * @module : Constant
 * @describe :常量类
 */
public class BaseConstant {
    //小兔作业url 测试 http://homeworkt.corpidea.com:83/  正式 https://homework.corpidea.com/api/
    public static final String HomeWorkBaseUrl = "https://homework.corpidea.com/api/";
    //小兔作业图片url
    public static final String HomeWorkImgUrl = "https://cdnfile.corpidea.com";
    //小兔作业录音url
    public static final String HomeWorkAudioUrl = "http://homeworkfile.corpidea.com/";
    //小兔作业请求头参数
    public static final String HomeWorkHeaders = "baseurlname:homework";

    //小兔听写url
    public static final String ListenBaseUrl = "http://listening.corpidea.com/";
    //小兔听写图片url
    public static final String ListenImgUrl = "http://listening.corpidea.com";
    //小兔听写音频url
    public static final String ListenAudioUrl = "https://cdnfile.corpidea.com";
    //小兔听写请求头参数
    public static final String ListenHeaders = "baseurlname:listen";

    //sp文件名
    public static final String SP_NAME = "Homework";
    //录音最大时长
    public static final long MAX_DURATION = 30000;

    //小天才退出app时间
    public static final long XTC_TIMES = 5 * 60 * 1000;
    //华为退出app时间
    public static final long HUAWEI_TIMES = 30 * 1000;

    public static final int REQUEDT_DATECODE = 100;
    public static final int REQUEDT_LISTENCHOOSEGRADE = 200;
    public static final int REQUEDT_LISTENCHOOSEVERSION = 300;
    public static final int REQUEDT_STUDYFILTRATE = 400;
    public static final int REQUEDT_STUDYSIGN = 500;
    public static final int REQUEDT_STUDYSIGN2 = 600;
    public static final int REQUEDT_STUDYINPUT = 700;
    public static final int REQUEDT_STUDYTIME = 800;

    //较慢
    public static final int LISTEN_SPEED_SLOW = 21000;
    //正常
    public static final int LISTEN_SPEED_NORMAL = 16000;
    //快速
    public static final int LISTEN_SPEED_QUICK = 11000;
    //飞快播放速度
    public static final int PLAYLISTEN_SPEED_FAST = 6000;

    //---------------------------------------------跳转路由路径-----------------------------------------------------
    //隐私/用户协议界面
    public static final String BASE_AGREEMENT = "/base/AgreementActivity";
    //休息提示界面
    public static final String BASE_RESTTIMES = "/base/RestTimesActivity";
    //WiFi切换移动流量界面
    public final static String BASE_NETWORK = "/base/NetWorkActivity";

    /**
     * 作业主界面
     */
    //作业启动界面
    public static final String WORK_SPLASH = "/worksplash/WorkSplashActivity";
    //作业主界面
    public static final String WORK_MAIN = "/workmain/WorkMainActivity";

    /**
     * 二维码模块
     */
    //登录界面
    public static final String QRCODE_LOGIN = "/qrcode/QrcodeLoginActivity";
    //支付界面
    public static final String QRCODE_PAY = "/qrcode/QrcodePayActivity";
    //广告界面
    public static final String QRCODE_AD = "/qrcode/QrcodeADActivity";
    //文字提示界面
    public static final String QRCODE_TEXTTIP = "/qrcode/QrcodeTextTipActivity";
    //帮助界面
    public static final String QRCODE_HELP = "/qrcode/QrcodeHelpActivity";
    //小兔商城界面
    public static final String QRCODE_SHOP = "/qrcode/QrcodeShopActivity";

    /**
     * record(作业记录模块)
     */
    //作业记录界面
    public static final String RECORD_MAIN = "/record/RecordMainActivity";
    //今日作业界面
    public static final String RECORD_SUBJECT = "/record/RecordSubjectActivity";
    //录音界面
    public static final String RECORD_INPUT = "/record/RecordInputActivity";
    //录入口令界面
    public static final String RECORD_INPUTCOMMAND = "/record/RecordInputCommandActivity";
    //选择科目界面
    public static final String RECORD_CHOOSESUBJECT = "/record/RecordChooseSubjectActivity";
    //录入科目详情界面
    public static final String RECORD_SUBJECTDETAIL = "/record/RecordSubjectDetailActivity";
    //作业历史界面
    public static final String RECORD_HISTORY = "/record/RecordHistoryActivity";
    //选择日期界面
    public static final String RECORD_CHOOSEDATE = "/record/RecordChooseDateActivity";
    //历史科目界面
    public static final String RECORD_HISTORYSUBJECT = "/record/RecordHistorySubjectActivity";
    //历史科目详情界面
    public static final String RECORD_HISTORYSUBJECTDETAIL = "/record/RecordHistorySubjectDetailActivity";

    /**
     * teach(答案听力模块)
     */
    //我的教辅界面
    public static final String TEACH_MAIN = "/teach/TeachMainActivity";
    //年级课目界面
    public static final String TEACH_GRADECLASS = "/teach/TeachGradeClassActivity";
    //听力播放界面
    public static final String TEACH_PLAY = "/teach/TeachPlayActivity";
    //原题答案界面
    public static final String TEACH_ANSWER = "/teach/TeachAnswerActivity";
    //选择教辅系列界面
    public static final String TEACH_BOOK = "/teach/TeachBookActivity";
    //选择教辅系列下教辅界面
    public static final String TEACH_BOOKLIST = "/teach/TeachBookListActivity";
    //选择教辅年级界面
    public static final String TEACH_CHOOSEGRADECLASS = "/teach/TeachChooseGradeClassActivity";

    /**
     * study(自习室模块)
     */
    //自习室界面
    public static final String STUDY_MAIN = "/study/StudyMainActivity";
    //自习广场列表界面
    public static final String STUDY_PLAZA = "/study/StudyPlazaActivity";
    //自习广场列表筛选界面
    public static final String STUDY_PLAZAFILTRATE = "/study/StudyPlazaFiltrateActivity";
    //自习列表详情界面
    public static final String STUDY_DETAIL = "/study/StudyDetailActivity";
    //自习室积分界面
    public static final String STUDY_INTEGRAL = "/study/StudyIntegralActivity";
    //个性签名界面
    public static final String STUDY_SIGN = "/study/StudySignActivity";
    //录入个性语音界面
    public static final String STUDY_INPUT = "/study/StudyInputActivity";
    //我的自习室界面
    public static final String STUDY_MINE = "/study/StudyMineActivity";
    //创建自习室界面
    public static final String STUDY_CREATE = "/study/StudyCreateActivity";
    //输入自习室口令界面
    public static final String STUDY_INPUTCOMMAND = "/study/StudyInputCommandActivity";

    /**
     * listening(听写模块)
     */
    //听写启动界面
    public static final String LISTEN_SPLASH = "/listensplash/ListenMainActivity";
    //听写主界面
    public static final String LISTEN_MAIN = "/listenmain/ListenMainActivity";
    //听写课目界面
    public static final String LISTEN_SUBJECT = "/listen/ListenSubjectActivity";
    //听写年级界面
    public static final String LISTEN_GRADE = "/listen/ListenGradeActivity";
    //听写课本版本界面
    public static final String LISTEN_VERSION = "/listen/ListenVersionActivity";
    //听写课界面
    public static final String LISTEN_CLASS = "/listen/ListenClassActivity";
    //听力播放界面
    public static final String LISTEN_PLAY = "/listen/ListenPlayActivity";
    //听写答案界面
    public static final String LISTEN_ANSWER = "/listen/ListenAnswerActivity";

    /**
     * mine(我的模块)
     */
    //我的界面
    public static final String MINE_MAIN = "/mine/MineMainActivity";
    //勋章界面
    public static final String MINE_MEDAL = "/mine/MineMedalActivity";
    //元气界面
    public static final String MINE_VITALITY = "/mine/MineVitalityActivity";
    //元气规则界面
    public static final String MINE_VITALITYRULE = "/mine/MineVitalityRuleActivity";
    //元气活动界面
    public static final String MINE_VITALITYEVENT = "/mine/MineVitalityEventActivity";
}
