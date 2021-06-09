package com.colortu.colortu_module.colortu_study.viewmodel;

import android.os.Handler;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioPlayer;
import com.colortu.colortu_module.colortu_base.bean.StudyCloseBean;
import com.colortu.colortu_module.colortu_base.bean.StudyDetailBean;
import com.colortu.colortu_module.colortu_base.bean.StudyFirstBean;
import com.colortu.colortu_module.colortu_base.bean.StudyLikeBean;
import com.colortu.colortu_module.colortu_base.bean.StudyMatchBean;
import com.colortu.colortu_module.colortu_base.bean.StudyStartBean;
import com.colortu.colortu_module.colortu_base.bean.StudyStopBean;
import com.colortu.colortu_module.colortu_base.bean.StudyUploadSignBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/4/9
 * @module : StudyDetailViewModel
 * @describe :自习列表详情界面ViewModel
 */
public class StudyDetailViewModel extends BaseActivityViewModel<BaseRequest> {
    //判断是否是第一次加入自习室接口
    private Call<StudyFirstBean> studyFirstBeanCall;
    //自习列表详情接口
    private Call<StudyDetailBean> studyDetailBeanCall;
    //自习室自动匹配接口
    private Call<StudyMatchBean> studyMatchBeanCall;
    //点赞接口
    private Call<StudyLikeBean> studyLikeBeanCall;
    //入座自习室接口
    private Call<StudyStartBean> studyStartBeanCall;
    //退出自习室接口
    private Call<StudyStopBean> studyStopBeanCall;
    //修改个性状态接口
    private Call<StudyUploadSignBean> studyUploadSignBeanCall;
    //关闭自习室接口
    private Call<StudyCloseBean> studyCloseBeanCall;
    //post请求体参数
    private PostParams postParams;

    //自习室详情数据
    public MutableLiveData<StudyDetailBean.DataBean.StudyRoomBean> studyRoomBeanLiveData = new MutableLiveData<>();
    //自习广场个人详情列表数据
    public MutableLiveData<StudyDetailBean.DataBean.TopUserBean> topUserBeanLiveData = new MutableLiveData<>();
    //自习广场列表详情列表数据
    public MutableLiveData<List<StudyDetailBean.DataBean.UserDetailsBean>> plazaDetailLiveData = new MutableLiveData<>();

    //自习室标题
    public ObservableField<String> studytitle = new ObservableField<>();
    //自习室人数
    public ObservableField<String> studynum = new ObservableField<>();
    //开始/结束文本
    public ObservableField<String> studytip = new ObservableField<>();
    //开始/结束图标
    public ObservableField<Integer> studytipicon = new ObservableField<>();
    //自习室id
    public ObservableField<Integer> roomid = new ObservableField<>();
    //0官方自习室,1个人自习室
    public ObservableField<String> channel = new ObservableField<>();

    private Handler handler;
    //0表示未加入过自习室,1表示已经加入过自习室,2未获取获取到数据
    public int isfirst = 2;
    //是否入座/结束自习室
    public MutableLiveData<Boolean> isStudyLiveData = new MutableLiveData<>();
    //true自己签名播放,false他人签名播放
    public ObservableField<Boolean> typeplay = new ObservableField<>();
    //判断是否播放完成
    public MutableLiveData<Boolean> isPlayLiveData = new MutableLiveData<>();
    //播放器工具类
    public AudioPlayer audioPlayer;

    public void initStudyDetail() {
        //实例化
        handler = new Handler();
        postParams = new PostParams();
        audioPlayer = new AudioPlayer();

        isStudyLiveData.setValue(false);

        getIsFirstStudyRoom(roomid.get(), GetBeanDate.getUserUuid());
        initPlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStudyDetail(roomid.get(), GetBeanDate.getUserUuid());
    }

    /**
     * 初始化播放
     */
    public void initPlay() {
        audioPlayer.setOnPlayerListener(new AudioPlayer.OnPlayerListener() {
            @Override
            public void playerstart() {//播放
                //取消息屏app销毁
                SuicideUtils.onCancelKill();
            }

            @Override
            public void playerpause() {//暂停
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                isPlayLiveData.setValue(true);
            }

            @Override
            public void playerstop() {//停止
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                isPlayLiveData.setValue(true);
            }

            @Override
            public void playerfinish() {//完成
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                isPlayLiveData.setValue(true);
            }

            @Override
            public void playerfailure() {//失败
                //启动息屏app销毁
                SuicideUtils.onStartKill();
                isPlayLiveData.setValue(true);
            }
        });
    }

    /**
     * 跳转积分界面
     */
    public void onJumpStudyIntegral() {
        BaseUIKit.startActivity(UIKitName.STUDY_DETAIL, UIKitName.STUDY_INTEGRAL,
                BaseConstant.STUDY_INTEGRAL, BaseUIKit.OTHER, null);
    }

    /**
     * 小兔推荐
     */
    public void onStudyRecommend() {
        getStudyMatch(studyRoomBeanLiveData.getValue().getId(), GetBeanDate.getUserUuid());
    }

    /**
     * 判断是否是第一次加入自习室接口请求
     *
     * @param studyRoomId 自习室id
     * @param uuid        用户uuid
     */
    public void getIsFirstStudyRoom(int studyRoomId, String uuid) {
        studyFirstBeanCall = iRequest.getIsFirstStudyRoom(BaseApplication.getHeaders(), studyRoomId, uuid);
        studyFirstBeanCall.enqueue(new Callback<StudyFirstBean>() {
            @Override
            public void onResponse(Call<StudyFirstBean> call, Response<StudyFirstBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    isfirst = response.body().getData().getIsFirtInToStudyRoom();
                }
            }

            @Override
            public void onFailure(Call<StudyFirstBean> call, Throwable t) {//请求失败

            }
        });
    }

    /**
     * 自习列表详情接口请求Runnable
     */
    Runnable getStudyDetailRunnable = new Runnable() {
        @Override
        public void run() {
            getStudyDetail(roomid.get(), GetBeanDate.getUserUuid());
        }
    };

    /**
     * 自习列表详情接口请求
     *
     * @param id   自习室id
     * @param uuid 用户uuid
     */
    public void getStudyDetail(int id, String uuid) {
        studyDetailBeanCall = iRequest.getStudyDetail(BaseApplication.getHeaders(), id, uuid, "userLikeNum");
        studyDetailBeanCall.enqueue(new Callback<StudyDetailBean>() {
            @Override
            public void onResponse(Call<StudyDetailBean> call, Response<StudyDetailBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    //自习室详情数据
                    if (EmptyUtils.listIsEmpty(response.body().getData().getStudyRoom())) {
                        studyRoomBeanLiveData.setValue(response.body().getData().getStudyRoom().get(0));
                    }

                    //自习广场个人详情列表数据
                    if (EmptyUtils.listIsEmpty(response.body().getData().getTopUser())) {
                        GetBeanDate.putIsExitRoom(true);
                        topUserBeanLiveData.setValue(response.body().getData().getTopUser().get(0));
                    } else {
                        GetBeanDate.putIsExitRoom(false);
                        topUserBeanLiveData.setValue(null);
                    }

                    //自习广场列表详情列表数据
                    plazaDetailLiveData.setValue(response.body().getData().getUserDetails());
                }
            }

            @Override
            public void onFailure(Call<StudyDetailBean> call, Throwable t) {//请求失败

            }
        });
    }

    /**
     * 自习室自动匹配接口请求
     *
     * @param id   自习室id
     * @param uuid 用户uuid
     */
    public void getStudyMatch(int id, String uuid) {
        HashMap hashMap = postParams.add("id", id).add("uuid", uuid).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        studyMatchBeanCall = iRequest.getStudyMatch(BaseApplication.getHeaders(), requestBody);
        studyMatchBeanCall.enqueue(new Callback<StudyMatchBean>() {
            @Override
            public void onResponse(Call<StudyMatchBean> call, Response<StudyMatchBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    TipToast.tipToastShort(response.body().getMsg());
                    if (handler != null) {
                        handler.removeCallbacks(getStudyDetailRunnable);
                        handler.postDelayed(getStudyDetailRunnable, 3 * 1000);
                    }
                }
            }

            @Override
            public void onFailure(Call<StudyMatchBean> call, Throwable t) {//请求失败

            }
        });
    }

    /**
     * 点赞接口请求
     *
     * @param supportUuid 用户uuid
     * @param uuid        被点赞用户uuid
     * @param studyRoomId 自习室id
     */
    public void getStudyLike(String supportUuid, String uuid, int studyRoomId) {
        HashMap hashMap = postParams.add("uuid", uuid).add("studyRoomId", studyRoomId).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        studyLikeBeanCall = iRequest.getStudyLike(BaseApplication.getHeaders(), supportUuid, requestBody);
        studyLikeBeanCall.enqueue(new Callback<StudyLikeBean>() {
            @Override
            public void onResponse(Call<StudyLikeBean> call, Response<StudyLikeBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    if (EmptyUtils.objectIsEmpty(response.body().getData())) {
                        if (response.body().getData().getFlag() != 0) {
                            TipToast.tipToastShort(response.body().getMsg());
                            getStudyDetail(roomid.get(), GetBeanDate.getUserUuid());
                        } else {
                            TipToast.tipToastShort(response.body().getMsg());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<StudyLikeBean> call, Throwable t) {//请求失败

            }
        });
    }

    /**
     * 修改个性状态接口请求
     */
    public void getStudyUploadSign(HashMap hashMap) {
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        studyUploadSignBeanCall = iRequest.getStudyUploadSign(BaseApplication.getHeaders(), requestBody);
        studyUploadSignBeanCall.enqueue(new Callback<StudyUploadSignBean>() {
            @Override
            public void onResponse(Call<StudyUploadSignBean> call, Response<StudyUploadSignBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body())) {
                    if (response.body().getCode() == 200) {
                        getStudyDetail(roomid.get(), GetBeanDate.getUserUuid());
                    }

                    if (EmptyUtils.stringIsEmpty(response.body().getMsg())) {
                        TipToast.tipToastShort(response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<StudyUploadSignBean> call, Throwable t) {//请求失败
            }
        });
    }

    /**
     * 入座自习室接口请求
     */
    public void getStudyStart(HashMap hashMap) {
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        studyStartBeanCall = iRequest.getStudyStart(BaseApplication.getHeaders(), requestBody);
        studyStartBeanCall.enqueue(new Callback<StudyStartBean>() {
            @Override
            public void onResponse(Call<StudyStartBean> call, Response<StudyStartBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    GetBeanDate.putIsExitRoom(true);
                    isStudyLiveData.setValue(true);
                    getIsFirstStudyRoom(roomid.get(), GetBeanDate.getUserUuid());
                    getStudyDetail(roomid.get(), GetBeanDate.getUserUuid());
                } else {
                    TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.study_startfailure));
                }
            }

            @Override
            public void onFailure(Call<StudyStartBean> call, Throwable t) {//请求失败
                TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.study_startfailure));
            }
        });
    }

    /**
     * 退出自习室接口请求
     *
     * @param studyRoomId 自习室id
     * @param uuid        用户uuid
     */
    public void getStudyStop(int studyRoomId, String uuid) {
        HashMap hashMap = postParams.add("studyRoomId", studyRoomId).add("uuid", uuid).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        studyStopBeanCall = iRequest.getStudyStop(BaseApplication.getHeaders(), requestBody);
        studyStopBeanCall.enqueue(new Callback<StudyStopBean>() {
            @Override
            public void onResponse(Call<StudyStopBean> call, Response<StudyStopBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    GetBeanDate.putIsExitRoom(false);
                    isStudyLiveData.setValue(false);
                    getStudyDetail(roomid.get(), GetBeanDate.getUserUuid());
                } else {
                    TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.study_finishfailure));
                }
            }

            @Override
            public void onFailure(Call<StudyStopBean> call, Throwable t) {//请求失败
                TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.study_finishfailure));
            }
        });
    }

    /**
     * 关闭自习室接口实现
     *
     * @param id
     */
    public void getStudyClose(int id) {
        HashMap hashMap = postParams.add("id", id).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        studyCloseBeanCall = iRequest.getStudyClose(BaseApplication.getHeaders(), requestBody);
        studyCloseBeanCall.enqueue(new Callback<StudyCloseBean>() {
            @Override
            public void onResponse(Call<StudyCloseBean> call, Response<StudyCloseBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<StudyCloseBean> call, Throwable t) {//请求失败

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(getStudyDetailRunnable);
        }

        //暂停播放，释放资源
        if (audioPlayer != null) {
            audioPlayer.onRelease();
        }
    }
}
