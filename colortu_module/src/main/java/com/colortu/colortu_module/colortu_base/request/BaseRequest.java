package com.colortu.colortu_module.colortu_base.request;

import com.colortu.colortu_module.colortu_base.bean.ListenClassBean;
import com.colortu.colortu_module.colortu_base.bean.ListenFinishBean;
import com.colortu.colortu_module.colortu_base.bean.ListenGradeBean;
import com.colortu.colortu_module.colortu_base.bean.ListenVersionBean;
import com.colortu.colortu_module.colortu_base.bean.QrcodeShopBean;
import com.colortu.colortu_module.colortu_base.bean.MineVitalityBean;
import com.colortu.colortu_module.colortu_base.bean.MineVitalityEventBean;
import com.colortu.colortu_module.colortu_base.bean.MineVitalityRuleBean;
import com.colortu.colortu_module.colortu_base.bean.QrcodeAddUserBean;
import com.colortu.colortu_module.colortu_base.bean.QrcodeIsVipBean;
import com.colortu.colortu_module.colortu_base.bean.QrcodeLoginBean;
import com.colortu.colortu_module.colortu_base.bean.QrcodePayBean;
import com.colortu.colortu_module.colortu_base.bean.QrcodePayStatusBean;
import com.colortu.colortu_module.colortu_base.bean.QrcodeUserInfoBean;
import com.colortu.colortu_module.colortu_base.bean.QrcodeVerifyBean;
import com.colortu.colortu_module.colortu_base.bean.RecordAddWorkBean;
import com.colortu.colortu_module.colortu_base.bean.RecordChooseSubjectBean;
import com.colortu.colortu_module.colortu_base.bean.RecordCopyWorkBean;
import com.colortu.colortu_module.colortu_base.bean.RecordDeleteWorkBean;
import com.colortu.colortu_module.colortu_base.bean.RecordHistoryBean;
import com.colortu.colortu_module.colortu_base.bean.RecordMarkFinishBean;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectBean;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectDetailBean;
import com.colortu.colortu_module.colortu_base.bean.RecordTranslateBean;
import com.colortu.colortu_module.colortu_base.bean.StudyCloseBean;
import com.colortu.colortu_module.colortu_base.bean.StudyCommandBean;
import com.colortu.colortu_module.colortu_base.bean.StudyCreateBean;
import com.colortu.colortu_module.colortu_base.bean.StudyDetailBean;
import com.colortu.colortu_module.colortu_base.bean.StudyFirstBean;
import com.colortu.colortu_module.colortu_base.bean.StudyLikeBean;
import com.colortu.colortu_module.colortu_base.bean.StudyMatchBean;
import com.colortu.colortu_module.colortu_base.bean.StudyMineBean;
import com.colortu.colortu_module.colortu_base.bean.StudyOpenBean;
import com.colortu.colortu_module.colortu_base.bean.StudyPlazaBean;
import com.colortu.colortu_module.colortu_base.bean.StudyPlazaFiltrateBean;
import com.colortu.colortu_module.colortu_base.bean.StudyRecommendBean;
import com.colortu.colortu_module.colortu_base.bean.StudySignBean;
import com.colortu.colortu_module.colortu_base.bean.StudyStartBean;
import com.colortu.colortu_module.colortu_base.bean.StudyStopBean;
import com.colortu.colortu_module.colortu_base.bean.StudyUploadSignBean;
import com.colortu.colortu_module.colortu_base.bean.TeachAddTeachBookBean;
import com.colortu.colortu_module.colortu_base.bean.TeachBookBean;
import com.colortu.colortu_module.colortu_base.bean.TeachChooseGradeBean;
import com.colortu.colortu_module.colortu_base.bean.TeachDeleteTeachBookBean;
import com.colortu.colortu_module.colortu_base.bean.TeachGradeClassBean;
import com.colortu.colortu_module.colortu_base.bean.QrcodeHelpBean;
import com.colortu.colortu_module.colortu_base.bean.TeachMainBean;
import com.colortu.colortu_module.colortu_base.bean.TeachTopicAnswerBean;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author : Code23
 * @time : 2021/5/17
 * @module : BaseRequest
 * @describe :网络请求接口
 */
public interface BaseRequest {
    /**
     * -------------------------------------------登录接口--------------------------------------------
     */
    //登录验证接口
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("blade-auth/token")
    Call<QrcodeVerifyBean> getVerify(@Body RequestBody requestBody);

    //添加用户接口
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/user/addUser")
    Call<QrcodeAddUserBean> adduser(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid, @Query("channel") String channel);

    //登录二维码接口
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/pub2")
    Call<QrcodeLoginBean> getlogincode(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid,
                                       @Query("eid") String eid, @Query("channel") String channel);

    //用户信息接口
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/user/getInfo")
    Call<QrcodeUserInfoBean> getuserinfo(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //获取支付二维码
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/textBookCode")
    Call<QrcodePayBean> getpaycode(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //判断是否支付成功
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/isPay")
    Call<QrcodePayStatusBean> getpaystatus(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //判断是否是会员
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/isVIP")
    Call<QrcodeIsVipBean> getisvip(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid, @Query("tag") String tag);

    /**
     * -----------------------------------------作业记录接口------------------------------------------
     */
    //查询历史作业
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/homeworklist/historyListByUuid")
    Call<RecordHistoryBean> gethistory(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid,
                                       @Query("year") String year, @Query("month") String month);

    //作业列表
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/homework/listByUuid2")
    Call<RecordSubjectBean> gethomework(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid, @Query("date") String date);

    //标为已完成/未完成
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/homeworklist/update")
    Call<RecordMarkFinishBean> markfinish(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //作业列表详情
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/homeworklist/listByUuid")
    Call<RecordSubjectDetailBean> getsubjectdetail(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid,
                                                   @Query("date") String date, @Query("subjectId") int subjectId);

    //添加作业
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/homeworklist/submit")
    Call<RecordAddWorkBean> addhomework(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //复制作业
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/homeworklist/copy")
    Call<RecordCopyWorkBean> copyhomework(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //删除作业
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/homeworklist/removeByUuidAndIds")
    Call<RecordDeleteWorkBean> deletehomework(@HeaderMap HashMap<String, String> hashMap, @Query("id") int id, @Query("uuid") String uuid);

    //上传录音转翻译文本
    @Multipart
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("common/upload/uploadAudioAndGetText")
    Call<RecordTranslateBean> gettranslate(@HeaderMap HashMap<String, String> hashMap, @Part MultipartBody.Part part);

    //获取所有科目
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/subject/list")
    Call<RecordChooseSubjectBean> getselectsubject(@HeaderMap HashMap<String, String> hashMap,
                                                   @Query("current") int current, @Query("size") int size);

    /**
     * -----------------------------------------教辅接口----------------------------------------------
     */
    //获取教辅系列列表-语数英
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("proxy/post?ms=edu&s=/wap/book/seriesList.json")
    Call<TeachBookBean> getteachbook(@HeaderMap HashMap<String, String> headers, @Body RequestBody requestBody);

    //教辅系列下的教辅
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("proxy/post?ms=edu&s=/wap/exam/all.json")
    Call<TeachChooseGradeBean> getchoosegrade(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //获取帮助二维码
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/helpDetail")
    Call<QrcodeHelpBean> gethelp(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //我的教辅
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/userexercisebook/listByUuid")
    Call<TeachMainBean> getMineTeachBookbean(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //我的教辅-添加
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/userexercisebook/submit")
    Call<TeachAddTeachBookBean> addteachbook(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //我的教辅-删除
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/userexercisebook/removeByUuidAndIds")
    Call<TeachDeleteTeachBookBean> deleteteachbook(@HeaderMap HashMap<String, String> hashMap, @Query("ids") int ids, @Query("uuid") String uuid);

    //教辅原题答案列表
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("proxy/post?ms=edu&s=/wap/book/examList.json")
    Call<TeachGradeClassBean> getgradeclass(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //教辅原题答案详情
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("proxy/post?ms=edu&s=/wap/exam/test.json")
    Call<TeachTopicAnswerBean> getanswerdetail(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    /**
     * -----------------------------------------我的接口----------------------------------------------
     */
    //查看当前元气值
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/personalTenter/getCurrentVatality")
    Call<MineVitalityBean> getvitality(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //查看元气规则
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/personalTenter/getVatalityRule")
    Call<MineVitalityRuleBean> getvitalityrule(@HeaderMap HashMap<String, String> hashMap);

    //查看元气活动
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/personalTenter/getVatalityHistory")
    Call<MineVitalityEventBean> getvitalityevent(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //获取元气商城二维码
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/yuanqiMall")
    Call<QrcodeShopBean> getshop(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    /**
     * -----------------------------------------自习室接口----------------------------------------------
     */
    //获得自习室广场列表
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getStudyRoomList")
    Call<StudyPlazaBean> getStudyPlaza(@HeaderMap HashMap<String, String> hashMap);

    //获得自习室广场筛选列表
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getFilterResultByFilterKey")
    Call<StudyPlazaBean> getStudyPlazaFiltrate(@HeaderMap HashMap<String, String> hashMap, @Query("filterKey") String filterKey);

    //自习室随机推荐
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroomlist/clickBlindBox")
    Call<StudyRecommendBean> getRecommend(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //自习室广场列表筛选
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getFilterList")
    Call<StudyPlazaFiltrateBean> getPlazaFiltrate(@HeaderMap HashMap<String, String> hashMap);

    //判断是否是第一次加入自习室
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroompeople/isFirtInToStudyRoom")
    Call<StudyFirstBean> getIsFirstStudyRoom(@HeaderMap HashMap<String, String> hashMap,
                                             @Query("studyRoomId") int studyRoomId, @Query("uuid") String uuid);

    //自习列表详情
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getStudyRoomDetailByStudyRoomId")
    Call<StudyDetailBean> getStudyDetail(@HeaderMap HashMap<String, String> hashMap,
                                         @Query("id") int id, @Query("uuid") String uuid, @Query("key") String key);

    //自习室自动匹配
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroomlist/automaticMatchByPersonal")
    Call<StudyMatchBean> getStudyMatch(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //点赞
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroompeople/clickLikeNum")
    Call<StudyLikeBean> getStudyLike(@HeaderMap HashMap<String, String> hashMap, @Query("supportUuid") String supportUuid, @Body RequestBody requestBody);

    //获取个性状态列表
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroompeople/getStatusList")
    Call<StudySignBean> getStudySign(@HeaderMap HashMap<String, String> hashMap);

    //入座自习室
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroompeople/inToStudyRoom")
    Call<StudyStartBean> getStudyStart(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //退出自习室
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroompeople/quiteStudyRoom")
    Call<StudyStopBean> getStudyStop(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //修改个性状态
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroompeople/setMyStudyRoomStatus")
    Call<StudyUploadSignBean> getStudyUploadSign(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //我的自习室列表
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getMyStudyRoomList")
    Call<StudyMineBean> getStudyMine(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //创建自习室
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroomlist/createMyStudyRoom")
    Call<StudyCreateBean> getStudyCreate(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //自习室口令复制
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getStudyRoomByPassword")
    Call<StudyCommandBean> getStudyCommand(@HeaderMap HashMap<String, String> hashMap, @Query("password") String password);

    //关闭自习室
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroomlist/deprecatedToClose")
    Call<StudyCloseBean> getStudyClose(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //打开自习室
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroomlist/studyRoomToOpen")
    Call<StudyOpenBean> getStudyOpen(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    /**
     * -----------------------------------------听写接口----------------------------------------------
     */
    //年级列表
    @Headers(BaseConstant.ListenHeaders)
    @GET("api/keben/grade")
    Call<ListenGradeBean> getGrade();

    //获取教材版本
    @Headers(BaseConstant.ListenHeaders)
    @GET("listening/publisher/list")
    Call<ListenVersionBean> getVersion(@Query("subject_id") String subjectid, @Query("current") String current,
                                       @Query("size") String size, @Query("uuid") String uuid);

    //获取课文及词汇
    @Headers(BaseConstant.ListenHeaders)
    @GET("api/keben/poetrys")
    Call<ListenClassBean> getClassWords(@Query("gid") int gid, @Query("subject_id") int subjectid,
                                        @Query("publisher_id") int publisherid, @Query("uuid") String uuid);

    //听写完成
    @Headers(BaseConstant.ListenHeaders)
    @POST("api/keben/listeningFinished")
    Call<ListenFinishBean> getListenFinish(@Query("lessonId") int lessonId, @Query("uuid") String uuid);

    //答案界面广告图
    @Headers(BaseConstant.ListenHeaders)
    @GET("ad/adposition/detail")
    Call<ListenClassBean> getListenAD(@Query("positionKey") String positionKey, @Query("uuid") String uuid);
}
