package com.colortu.colortu_module.colortu_record.viewmodel;

import android.os.Bundle;
import android.os.CountDownTimer;

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
import com.colortu.colortu_module.colortu_base.utils.AudioFocusUtils;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.utils.Tools;
import com.colortu.colortu_module.colortu_base.utils.audio.AudioRecord;
import com.colortu.colortu_module.colortu_record.activity.RecordInputActivity;
import com.colortu.colortu_module.colortu_base.bean.RecordAddWorkBean;
import com.colortu.colortu_module.colortu_base.bean.RecordTranslateBean;

import java.io.File;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/1
 * @module : RecordInputViewModel
 * @describe :录音界面ViewModel
 */
public class RecordInputViewModel extends BaseActivityViewModel<BaseRequest> implements AudioFocusUtils.OnAudioFocusListener {
    //上传录音转翻译文本请求
    private Call<RecordTranslateBean> recordTranslateBeanCall;
    //添加作业请求
    private Call<RecordAddWorkBean> recordAddWorkBeanCall;
    //post请求体参数
    private PostParams postParams;

    //vip提示
    public ObservableField<String> viptip = new ObservableField<>();
    //判断是否中段
    public ObservableField<Boolean> isstop = new ObservableField<>();
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
    //1 未选择科目，2科目详情进来
    public ObservableField<Integer> type = new ObservableField<>();
    //1 正在上传  2 上传失败 3 上传成功
    public ObservableField<Integer> isupload = new ObservableField<>();
    //科目id
    public ObservableField<Integer> subjectId = new ObservableField<>();
    //录音url
    public ObservableField<String> audiourl = new ObservableField<>();
    //控制页面显示
    public MutableLiveData<Boolean> isvisibleLiveData = new MutableLiveData<>();
    //控制vip提示显示
    public MutableLiveData<Boolean> isvisibleVip = new MutableLiveData<>();

    //录音时长
    private int count = 0;
    //判断是否播放
    public boolean isplay = false;
    //上传语音翻译参数
    private String translate2;
    //0 不是会员 1是会员
    public int isVip;
    //录音工具类
    public AudioRecord audioRecord;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        audioRecord = new AudioRecord();
        postParams = new PostParams();
        AudioFocusUtils.setOnAudioFocusListener(this);

        translate2 = "";
        isvisibleLiveData.setValue(true);
        isstop.set(false);
        inputtip.set(BaseApplication.getContext().getString(R.string.input_message));
        inputplayimg.set(R.mipmap.icon_play_stop);
        isVip = GetBeanDate.getIsTranslateVIP();

        initPlay();
        showVipTip();
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
                inputgif.set(R.raw.icon_play_audiogif);
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
     * 显示vip时间提示
     */
    public void showVipTip() {
        if (isVip == 1) {
            String time = Tools.dateDiff(GetBeanDate.getTranslateVIPTime());
            if (EmptyUtils.stringIsEmpty(time)) {
                isvisibleVip.setValue(false);
                viptip.set(BaseApplication.getContext().getString(R.string.vip_time) + time);
            } else {
                isvisibleVip.setValue(true);
            }
        } else {
            isvisibleVip.setValue(false);
            viptip.set(BaseApplication.getContext().getString(R.string.vip_scancode));
        }
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
     * 跳转解锁二维码界面
     */
    public void onJumpPay() {
        Bundle bundle = new Bundle();
        bundle.putInt("status", 1);
        BaseUIKit.startActivity(UIKitName.RECORD_INPUT, UIKitName.QRCODE_PAY,
                BaseConstant.QRCODE_PAY, BaseUIKit.OTHER, bundle);
    }

    /**
     * 复制作业
     */
    public void onJumpInputCommand() {
        if (GetBeanDate.getIsLogin()) {
            BaseUIKit.startActivity(UIKitName.RECORD_INPUT, UIKitName.RECORD_INPUTCOMMAND,
                    BaseConstant.RECORD_INPUTCOMMAND, BaseUIKit.OTHER, null);
        } else {
            BaseUIKit.startActivity(UIKitName.RECORD_INPUT, UIKitName.QRCODE_LOGIN,
                    BaseConstant.QRCODE_LOGIN, BaseConstant.RECORD_INPUTCOMMAND, BaseUIKit.HAVE, null);
        }
    }

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
     * 保存录音
     */
    public void onSaveAudio() {
        switch (isupload.get()) {
            case 1://正在上传
                TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.record_translate));
                break;
            case 2://上传失败
                TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.record_uploadfailure));
                break;
            case 3://上传成功
                if (type.get() == 1) {
                    if (EmptyUtils.stringIsEmpty(audiourl.get())) {
                        Bundle bundle = new Bundle();
                        //录音翻译文本
                        bundle.putString("content", translate2);
                        //录音url
                        bundle.putString("audioUrl", audiourl.get());
                        //录音时长
                        bundle.putString("audioTime", duration.get());
                        BaseUIKit.startActivity(UIKitName.RECORD_INPUT, UIKitName.RECORD_CHOOSESUBJECT,
                                BaseConstant.RECORD_CHOOSESUBJECT, BaseUIKit.OTHER, bundle);
                    } else {
                        TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.record_uploadfailure));
                    }
                } else {
                    if (EmptyUtils.stringIsEmpty(audiourl.get())) {
                        addHomeWork(GetBeanDate.getUserUuid(), subjectId.get(), translate2, audiourl.get(), duration.get());
                    } else {
                        TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.record_uploadfailure));
                    }
                }
                break;
        }
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
                        if (isVip == 1) {
                            if (!response.body().getData().getText().equals("")) {
                                translate2 = response.body().getData().getText();
                                translate.set(response.body().getData().getText());
                            } else {
                                TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.record_translatefailure));
                            }
                        } else {
                            translate.set(BaseApplication.getContext().getString(R.string.vip_scancode));
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
                isupload.set(2);
            }
        });
    }

    /**
     * 添加作业
     *
     * @param uuid      用户uuid
     * @param subjectId 科目id
     * @param content   录音翻译文本
     * @param audioUrl  录音url
     * @param audioTime 录音时长
     */
    public void addHomeWork(String uuid, int subjectId, String content, String audioUrl, String audioTime) {
        HashMap hashMap = postParams.add("uuid", uuid).add("subjectId", subjectId).add("content", content)
                .add("audioUrl", audioUrl).add("audioTime", audioTime).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        recordAddWorkBeanCall = iRequest.addhomework(BaseApplication.getHeaders(), requestBody);
        recordAddWorkBeanCall.enqueue(new Callback<RecordAddWorkBean>() {
            @Override
            public void onResponse(Call<RecordAddWorkBean> call, Response<RecordAddWorkBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    BaseApplication.getInstance().finishActivity(RecordInputActivity.class);
                } else {
                    TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.save_failure));
                }
            }

            @Override
            public void onFailure(Call<RecordAddWorkBean> call, Throwable t) {//请求失败
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
        BaseApplication.getInstance().finishActivity(RecordInputActivity.class);
    }
}
