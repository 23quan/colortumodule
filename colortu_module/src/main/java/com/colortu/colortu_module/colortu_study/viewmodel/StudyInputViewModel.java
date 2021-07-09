package com.colortu.colortu_module.colortu_study.viewmodel;

import android.os.CountDownTimer;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.bean.RecordTranslateBean;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioRecord;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/4/13
 * @module : StudyInputViewModel
 * @describe :录入个性语音ViewModel
 */
public class StudyInputViewModel extends BaseActivityViewModel<BaseRequest> implements AudioFocusUtils.OnAudioFocusListener {
    //上传录音转翻译文本接口
    private Call<RecordTranslateBean> recordTranslateBeanCall;
    //post请求体参数
    private PostParams postParams;

    //0表示未加入过自习室,1表示已经加入过自习室
    public ObservableField<Integer> isfirst = new ObservableField<>();
    //录入提示
    public ObservableField<String> inputtip = new ObservableField<>();
    //gif图片
    public ObservableField<Integer> inputgif = new ObservableField<>();
    //暂停播放icon
    public ObservableField<Integer> inputplayimg = new ObservableField<>();
    //录入时长
    public ObservableField<String> duration = new ObservableField<>();
    //语音翻译
    public ObservableField<String> translate = new ObservableField<>();
    //判断是否中段
    public ObservableField<Boolean> isstop = new ObservableField<>();
    //1 正在上传  2 上传失败 3 上传成功
    public ObservableField<Integer> isupload = new ObservableField<>();
    //录音url
    public ObservableField<String> audiourl = new ObservableField<>();
    //控制页面显示
    public MutableLiveData<Boolean> isvisibleLiveData = new MutableLiveData<>();

    //录音时长
    private int count = 0;
    //判断是否播放
    private boolean isplay = false;
    //录音工具类
    public AudioRecord audioRecord;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        audioRecord = new AudioRecord();
        postParams = new PostParams();
        AudioFocusUtils.setOnAudioFocusListener(this);

        isvisibleLiveData.setValue(true);
        isstop.set(false);
        inputtip.set(BaseApplication.getContext().getResources().getString(R.string.input_message));
        inputplayimg.set(R.mipmap.icon_play_stop);

        initPlay();
    }

    /**
     * 初始化播放
     */
    public void initPlay() {
        audioRecord.setOnRecorderListener(new AudioRecord.OnRecorderListener() {
            @Override
            public void onStartPlayer() {//播放开始监听
                isplay = true;
                inputplayimg.set(R.mipmap.icon_play_start);
            }

            @Override
            public void onFinishPlayer() {//播放完成监听
                //解绑音频焦点
                AudioFocusUtils.abandonAudioFocus();
                isplay = false;
                inputplayimg.set(R.mipmap.icon_play_stop);
            }

            @Override
            public void onStopPlayer() {//播放暂停监听
                //解绑音频焦点
                AudioFocusUtils.abandonAudioFocus();
                isplay = false;
                inputplayimg.set(R.mipmap.icon_play_stop);
            }

            @Override
            public void onFailurePlayer() {//播放失败监听
                //解绑音频焦点
                AudioFocusUtils.abandonAudioFocus();
                isplay = false;
                inputplayimg.set(R.mipmap.icon_play_stop);
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
                if (count > 3) {
                    //录音翻译请求
                    isupload.set(1);
                    getTranslate(file);
                } else {
                    TipToast.tipToastShort(BaseApplication.getContext().getString(R.string.record_message));
                    onAgainAudio();
                }
            }

            @Override
            public void onFailureRecorder() {//录音失败监听
                //解绑音频焦点
                AudioFocusUtils.abandonAudioFocus();
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
     * 播放录音
     */
    public void onPlayAudio() {
        if (audioRecord != null) {
            if (isplay) {
                audioRecord.OnPlayer(false);
            } else {
                //抢占音频焦点
                AudioFocusUtils.initAudioFocus(BaseApplication.getContext());
                audioRecord.OnPlayer(true);
            }
        }
    }

    /**
     * 重新录音
     */
    public void onAgainAudio() {
        if (audioRecord.isPlayer()) {
            audioRecord.OnPlayer(false);
        }
        if (audioRecord != null) {
            audioRecord.onCancelRecorder();
        }
        count = 0;
        translate.set("");
        inputtip.set(BaseApplication.getContext().getString(R.string.input_message));
        isvisibleLiveData.setValue(true);
    }

    /**
     * 失去焦点
     */
    @Override
    public void onLossAudioFocus() {
        if (audioRecord != null) {
            if (audioRecord.isPlayer()) {
                audioRecord.OnPlayer(false);
            }
        }
    }

    /**
     * 获取焦点
     */
    @Override
    public void onGainAudioFocus() {

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
                        //录音文件url
                        audiourl.set(response.body().getData().getAudioUrl() + "");
                        //录音翻译文本
                        if (!response.body().getData().getText().equals("")) {
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

    @Override
    protected void onStop() {
        super.onStop();
        if (audioRecord != null) {
            if (isstop.get()) {//暂停录音
                audioRecord.OnRecorder(false);
            } else {//暂停播放
                if (audioRecord.getMediaPlayer() != null) {
                    audioRecord.OnPlayer(false);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑音频焦点
        AudioFocusUtils.abandonAudioFocus();
    }
}
