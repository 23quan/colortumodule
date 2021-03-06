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
 * @describe :??????????????????
 */
public interface BaseRequest {
    /**
     * -------------------------------------------????????????--------------------------------------------
     */
    //??????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("blade-auth/token")
    Call<QrcodeVerifyBean> getVerify(@Body RequestBody requestBody);

    //??????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/user/addUser")
    Call<QrcodeAddUserBean> adduser(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid, @Query("channel") String channel);

    //?????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/pub2")
    Call<QrcodeLoginBean> getlogincode(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid,
                                       @Query("eid") String eid, @Query("channel") String channel);

    //??????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/user/getInfo2")
    Call<QrcodeUserInfoBean> getuserinfo(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //?????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/textBookCode")
    Call<QrcodePayBean> getpaycode(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //????????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/isPay")
    Call<QrcodePayStatusBean> getpaystatus(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //?????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/isVIP")
    Call<QrcodeIsVipBean> getisvip(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid, @Query("tag") String tag);

    /**
     * -----------------------------------------??????????????????------------------------------------------
     */
    //??????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/homeworklist/historyListByUuid")
    Call<RecordHistoryBean> gethistory(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid,
                                       @Query("year") String year, @Query("month") String month);

    //????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/homework/listByUuid2")
    Call<RecordSubjectBean> gethomework(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid, @Query("date") String date);

    //???????????????/?????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/homeworklist/update")
    Call<RecordMarkFinishBean> markfinish(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //??????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/homeworklist/listByUuid")
    Call<RecordSubjectDetailBean> getsubjectdetail(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid,
                                                   @Query("date") String date, @Query("subjectId") int subjectId);

    //????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/homeworklist/submit")
    Call<RecordAddWorkBean> addhomework(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/homeworklist/copy")
    Call<RecordCopyWorkBean> copyhomework(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/homeworklist/removeByUuidAndIds")
    Call<RecordDeleteWorkBean> deletehomework(@HeaderMap HashMap<String, String> hashMap, @Query("id") int id, @Query("uuid") String uuid);

    //???????????????????????????
    @Multipart
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("common/upload/uploadAudioAndGetText")
    Call<RecordTranslateBean> gettranslate(@HeaderMap HashMap<String, String> hashMap, @Part MultipartBody.Part part);

    //??????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/subject/list")
    Call<RecordChooseSubjectBean> getselectsubject(@HeaderMap HashMap<String, String> hashMap,
                                                   @Query("current") int current, @Query("size") int size);

    /**
     * -----------------------------------------????????????----------------------------------------------
     */
    //????????????????????????-?????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("proxy/post?ms=edu&s=/wap/book/seriesList.json")
    Call<TeachBookBean> getteachbook(@HeaderMap HashMap<String, String> headers, @Body RequestBody requestBody);

    //????????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("proxy/post?ms=edu&s=/wap/exam/all.json")
    Call<TeachChooseGradeBean> getchoosegrade(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //?????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/helpDetail")
    Call<QrcodeHelpBean> gethelp(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/userexercisebook/listByUuid")
    Call<TeachMainBean> getMineTeachBookbean(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //????????????-??????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/userexercisebook/submit")
    Call<TeachAddTeachBookBean> addteachbook(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //????????????-??????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/userexercisebook/removeByUuidAndIds")
    Call<TeachDeleteTeachBookBean> deleteteachbook(@HeaderMap HashMap<String, String> hashMap, @Query("ids") int ids, @Query("uuid") String uuid);

    //????????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("proxy/post?ms=edu&s=/wap/book/examList.json")
    Call<TeachGradeClassBean> getgradeclass(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //????????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("proxy/post?ms=edu&s=/wap/exam/test.json")
    Call<TeachTopicAnswerBean> getanswerdetail(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    /**
     * -----------------------------------------????????????----------------------------------------------
     */
    //?????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/personalTenter/getCurrentVatality")
    Call<MineVitalityBean> getvitality(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //??????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/personalTenter/getVatalityRule")
    Call<MineVitalityRuleBean> getvitalityrule(@HeaderMap HashMap<String, String> hashMap);

    //??????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/personalTenter/getVatalityHistory")
    Call<MineVitalityEventBean> getvitalityevent(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //???????????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("api/code/yuanqiMall")
    Call<QrcodeShopBean> getshop(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    /**
     * -----------------------------------------???????????????----------------------------------------------
     */
    //???????????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getStudyRoomList")
    Call<StudyPlazaBean> getStudyPlaza(@HeaderMap HashMap<String, String> hashMap);

    //?????????????????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getFilterResultByFilterKey")
    Call<StudyPlazaBean> getStudyPlazaFiltrate(@HeaderMap HashMap<String, String> hashMap, @Query("filterKey") String filterKey);

    //?????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroomlist/clickBlindBox")
    Call<StudyRecommendBean> getRecommend(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //???????????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getFilterList")
    Call<StudyPlazaFiltrateBean> getPlazaFiltrate(@HeaderMap HashMap<String, String> hashMap);

    //???????????????????????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroompeople/isFirtInToStudyRoom")
    Call<StudyFirstBean> getIsFirstStudyRoom(@HeaderMap HashMap<String, String> hashMap,
                                             @Query("studyRoomId") int studyRoomId, @Query("uuid") String uuid);

    //??????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getStudyRoomDetailByStudyRoomId")
    Call<StudyDetailBean> getStudyDetail(@HeaderMap HashMap<String, String> hashMap,
                                         @Query("id") int id, @Query("uuid") String uuid, @Query("key") String key);

    //?????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroomlist/automaticMatchByPersonal")
    Call<StudyMatchBean> getStudyMatch(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //??????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroompeople/clickLikeNum")
    Call<StudyLikeBean> getStudyLike(@HeaderMap HashMap<String, String> hashMap, @Query("supportUuid") String supportUuid, @Body RequestBody requestBody);

    //????????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroompeople/getStatusList")
    Call<StudySignBean> getStudySign(@HeaderMap HashMap<String, String> hashMap);

    //???????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroompeople/inToStudyRoom")
    Call<StudyStartBean> getStudyStart(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //???????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroompeople/quiteStudyRoom")
    Call<StudyStopBean> getStudyStop(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //??????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroompeople/setMyStudyRoomStatus")
    Call<StudyUploadSignBean> getStudyUploadSign(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //?????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getMyStudyRoomList")
    Call<StudyMineBean> getStudyMine(@HeaderMap HashMap<String, String> hashMap, @Query("uuid") String uuid);

    //???????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroomlist/createMyStudyRoom")
    Call<StudyCreateBean> getStudyCreate(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //?????????????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @GET("homework/studyroomlist/getStudyRoomByPassword")
    Call<StudyCommandBean> getStudyCommand(@HeaderMap HashMap<String, String> hashMap, @Query("password") String password);

    //???????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroomlist/deprecatedToClose")
    Call<StudyCloseBean> getStudyClose(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    //???????????????
    @Headers(BaseConstant.HomeWorkHeaders)
    @POST("homework/studyroomlist/studyRoomToOpen")
    Call<StudyOpenBean> getStudyOpen(@HeaderMap HashMap<String, String> hashMap, @Body RequestBody requestBody);

    /**
     * -----------------------------------------????????????----------------------------------------------
     */
    //????????????
    @Headers(BaseConstant.ListenHeaders)
    @GET("api/keben/grade")
    Call<ListenGradeBean> getGrade();

    //??????????????????
    @Headers(BaseConstant.ListenHeaders)
    @GET("listening/publisher/list")
    Call<ListenVersionBean> getVersion(@Query("subject_id") String subjectid, @Query("current") String current,
                                       @Query("size") String size, @Query("uuid") String uuid);

    //?????????????????????
    @Headers(BaseConstant.ListenHeaders)
    @GET("api/keben/poetrys")
    Call<ListenClassBean> getClassWords(@Query("gid") int gid, @Query("subject_id") int subjectid,
                                        @Query("publisher_id") int publisherid, @Query("uuid") String uuid);

    //????????????
    @Headers(BaseConstant.ListenHeaders)
    @POST("api/keben/listeningFinished")
    Call<ListenFinishBean> getListenFinish(@Query("lessonId") int lessonId, @Query("uuid") String uuid);

    //?????????????????????
    @Headers(BaseConstant.ListenHeaders)
    @GET("ad/adposition/detail")
    Call<ListenClassBean> getListenAD(@Query("positionKey") String positionKey, @Query("uuid") String uuid);
}
