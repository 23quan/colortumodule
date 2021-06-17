package com.colortu.colortu_module.colortu_record.viewmodel;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
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
import com.colortu.colortu_module.colortu_base.bean.RecordDeleteWorkBean;
import com.colortu.colortu_module.colortu_base.bean.RecordSubjectDetailBean;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/2
 * @module : RecordSubjectDetailViewModel
 * @describe :录入科目详情界面ViewModel
 */
public class RecordSubjectDetailViewModel extends BaseActivityViewModel<BaseRequest> {
    //作业列表详情请求
    private Call<RecordSubjectDetailBean> recordSubjectDetailBeanCall;
    //删除作业请求
    private Call<RecordDeleteWorkBean> recordDeleteWorkBeanCall;

    //录入科目详情列表数据
    public MutableLiveData<List<RecordSubjectDetailBean.DataBean.RecordsBean>> recordSubjectDetailBeanLiveData = new MutableLiveData<>();

    //科目名字
    public ObservableField<String> titlename = new ObservableField<>();
    //编辑/完成
    public ObservableField<String> edit = new ObservableField<>();
    //控制编辑和完成转换
    public MutableLiveData<Boolean> switchLiveData = new MutableLiveData<>();
    //判断是否播放完成
    public MutableLiveData<Boolean> isPlayLiveData = new MutableLiveData<>();

    private Handler handler;
    //查询时间
    public String checktime;
    //科目id
    public ObservableField<Integer> subjectId = new ObservableField<>();
    //课id
    public ObservableField<Integer> classId = new ObservableField<>();
    //语音播放工具类
    public AudioPlayer audioPlayer;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        audioPlayer = new AudioPlayer();
        handler = new Handler();

        edit.set(BaseApplication.getInstance().getString(R.string.edit));
        switchLiveData.setValue(false);
        checktime = String.valueOf(DateFormat.format("yyyy-MM-dd", Calendar.getInstance(Locale.CHINA)));
        initPlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSubjectDetailRunnable();
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
            public void recoverplayerstart() {//恢复播放

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
     * 跳转语音录入界面
     */
    public void onJumpInput() {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        bundle.putInt("subjectId", subjectId.get());
        BaseUIKit.startActivity(UIKitName.RECORD_SUBJECTDETAIL, UIKitName.RECORD_INPUT,
                BaseConstant.RECORD_INPUT, BaseUIKit.OTHER, bundle);
    }

    /**
     * 编辑
     */
    public void onClickEdit() {
        if (switchLiveData.getValue()) {
            edit.set(BaseApplication.getInstance().getString(R.string.edit));
            switchLiveData.setValue(false);
        } else {
            edit.set(BaseApplication.getInstance().getString(R.string.finish));
            switchLiveData.setValue(true);
        }
    }

    /**
     * 作业列表详情Runnable
     */
    public void getSubjectDetailRunnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getSubjectDetail(GetBeanDate.getUserUuid(), checktime, subjectId.get());
            }
        });
    }

    /**
     * 作业列表详情
     *
     * @param uuid
     * @param date
     * @param subjectId
     */
    public void getSubjectDetail(String uuid, String date, int subjectId) {
        recordSubjectDetailBeanCall = iRequest.getsubjectdetail(BaseApplication.getHeaders(), uuid, date, subjectId);
        recordSubjectDetailBeanCall.enqueue(new Callback<RecordSubjectDetailBean>() {
            @Override
            public void onResponse(Call<RecordSubjectDetailBean> call, Response<RecordSubjectDetailBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    recordSubjectDetailBeanLiveData.setValue(response.body().getData().getRecords());
                }
            }

            @Override
            public void onFailure(Call<RecordSubjectDetailBean> call, Throwable t) {//请求失败
            }
        });
    }

    /**
     * 删除作业Runnable
     */
    public void deleteHomeWorkRunnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                deleteHomeWork(classId.get(), GetBeanDate.getUserUuid());
            }
        });
    }

    /**
     * 删除作业
     *
     * @param id   课id
     * @param uuid 用户uuid
     */
    public void deleteHomeWork(int id, String uuid) {
        recordDeleteWorkBeanCall = iRequest.deletehomework(BaseApplication.getHeaders(), id, uuid);
        recordDeleteWorkBeanCall.enqueue(new Callback<RecordDeleteWorkBean>() {
            @Override
            public void onResponse(Call<RecordDeleteWorkBean> call, Response<RecordDeleteWorkBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    getSubjectDetailRunnable();
                } else {
                    TipToast.tipToastShort("删除失败");
                }
            }

            @Override
            public void onFailure(Call<RecordDeleteWorkBean> call, Throwable t) {//请求失败
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //暂停播放，释放资源
        if (audioPlayer != null) {
            audioPlayer.onRelease();
        }
    }
}
