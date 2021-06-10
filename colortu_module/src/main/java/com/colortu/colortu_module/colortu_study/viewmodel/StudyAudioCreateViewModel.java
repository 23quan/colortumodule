package com.colortu.colortu_module.colortu_study.viewmodel;

import android.os.CountDownTimer;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.bean.RecordTranslateBean;
import com.colortu.colortu_module.colortu_base.bean.StudyCreateBean;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioRecord;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/4/16
 * @module : StudyAudioCreateViewModel
 * @describe :语音创建自习室界面ViewModel
 */
public class StudyAudioCreateViewModel extends BaseActivityViewModel<BaseRequest> {
    //上传录音转翻译文本接口
    private Call<RecordTranslateBean> recordTranslateBeanCall;
    //创建自习室接口
    private Call<StudyCreateBean> studyCreateBeanCall;
    //post请求体参数
    private PostParams postParams;

    //录入提示
    public ObservableField<String> inputtip = new ObservableField<>();
    //gif图片
    public ObservableField<Integer> inputgif = new ObservableField<>();
    //录入时长
    public ObservableField<String> duration = new ObservableField<>();
    //语音翻译
    public ObservableField<String> translate = new ObservableField<>();
    //判断是否中段
    public ObservableField<Boolean> isstop = new ObservableField<>();
    //1 正在上传  2 上传失败 3 上传成功
    public ObservableField<Integer> isupload = new ObservableField<>();
    //控制页面显示
    public MutableLiveData<Boolean> isvisibleLiveData = new MutableLiveData<>();

    //自习室名
    private String studyRoomName = "";
    //录音时长
    private int count = 0;
    //录音工具类
    public AudioRecord audioRecord;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        postParams = new PostParams();

        initPlay();
    }

    /**
     * 初始化播放
     */
    public void initPlay() {
        audioRecord.setOnRecorderListener(new AudioRecord.OnRecorderListener() {
            @Override
            public void onStartPlayer() {//播放开始监听

            }

            @Override
            public void onFinishPlayer() {//播放完成监听

            }

            @Override
            public void onStopPlayer() {//播放暂停监听

            }

            @Override
            public void onFailurePlayer() {//播放失败监听

            }

            @Override
            public void onStartRecorder() {//录音开始监听
                inputtip.set("0''");
                inputgif.set(R.mipmap.icon_play_audiogif);
                countDownTimer.start();
            }

            @Override
            public void onCanceRecorderl() {//录音取消监听
                isvisibleLiveData.setValue(true);
            }

            @Override
            public void onStopRecorder(File file) {//录音结束监听
                inputgif.set(0);
                countDownTimer.onFinish();
                countDownTimer.cancel();
                //录音翻译请求
                isupload.set(1);
                getTranslate(file);
            }

            @Override
            public void onFailureRecorder() {//录音失败监听
                TipToast.tipToastShort(BaseApplication.getContext().getString(R.string.record_failure));
                if (audioRecord != null) {
                    audioRecord.onCancelRecorder();
                }
            }
        });
    }

    /**
     * 录音时间，最多30秒
     */
    private CountDownTimer countDownTimer = new CountDownTimer(BaseConstant.MAX_DURATION, 1000) {
        @Override
        public void onTick(long l) {
            count++;
            inputtip.set(count + "''");
        }

        @Override
        public void onFinish() {
            duration.set(count + "''");
            isvisibleLiveData.setValue(false);
        }
    };

    /**
     * 重新录音
     */
    public void onAgainAudio() {
        if (audioRecord != null) {
            audioRecord.onCancelRecorder();
        }
        count = 0;
        translate.set("");
        studyRoomName = "";
        inputtip.set(BaseApplication.getContext().getString(R.string.input_message));
        isvisibleLiveData.setValue(true);
    }

    /**
     * 创建自习室
     */
    public void onAudioCreate() {
        switch (isupload.get()) {
            case 1://正在上传
                TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.record_translate));
                break;
            case 2://上传失败
                TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.record_uploadfailure));
                break;
            case 3://上传成功
                if (EmptyUtils.stringIsEmpty(studyRoomName)) {
                    getStudyCommand(GetBeanDate.getUserUuid(), studyRoomName);
                } else {
                    TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.studyroom_namenone));
                }
                break;
        }
    }

    /**
     * 上传录音转翻译文本
     *
     * @param file 录音文件
     */
    public void getTranslate(File file) {
        //请求参数
        RequestBody requestBody = postParams.getGsonRequestBody(file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        recordTranslateBeanCall = iRequest.gettranslate(BaseApplication.getHeaders(), part);
        recordTranslateBeanCall.enqueue(new Callback<RecordTranslateBean>() {
            @Override
            public void onResponse(Call<RecordTranslateBean> call, Response<RecordTranslateBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    if (EmptyUtils.objectIsEmpty(response.body().getData())) {
                        isupload.set(3);
                        //录音翻译文本
                        if (!response.body().getData().getText().equals("")) {
                            studyRoomName = response.body().getData().getText();
                            translate.set(response.body().getData().getText());
                        } else {
                            translate.set(BaseApplication.getContext().getResources().getString(R.string.record_translatefailure));
                        }
                    } else {
                        isupload.set(2);
                    }
                } else {
                    isupload.set(2);
                }
            }

            @Override
            public void onFailure(Call<RecordTranslateBean> call, Throwable t) {//请求失败

            }
        });
    }

    /**
     * 创建自习室接口请求
     *
     * @param uuid 用户uuid
     * @param name 自习室名称
     */
    public void getStudyCommand(String uuid, String name) {
        HashMap hashMap = postParams.add("uuid", uuid).add("name", name).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        studyCreateBeanCall = iRequest.getStudyCreate(BaseApplication.getHeaders(), requestBody);
        studyCreateBeanCall.enqueue(new Callback<StudyCreateBean>() {
            @Override
            public void onResponse(Call<StudyCreateBean> call, Response<StudyCreateBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body())) {
                    if (response.body().getCode() == 200) {
                        TipToast.tipToastShort(response.body().getMsg());
                        finish();
                    } else {
                        TipToast.tipToastShort(response.body().getMsg());
                    }
                } else {
                    TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.studyroom_createfailure));
                }
            }

            @Override
            public void onFailure(Call<StudyCreateBean> call, Throwable t) {//请求失败
                TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.studyroom_createfailure));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (audioRecord != null) {
            if (isstop.get()) {//暂停录音
                audioRecord.OnRecorder(false);
            }
        }
    }
}
