package com.colortu.colortu_module.colortu_record.viewmodel;

import android.os.Handler;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_record.activity.RecordInputActivity;
import com.colortu.colortu_module.colortu_base.bean.RecordCopyWorkBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/15
 * @module : RecordInputCommandViewModel
 * @describe :录入口令界面ViewModel
 */
public class RecordInputCommandViewModel extends BaseActivityViewModel<BaseRequest> {
    //复制作业请求
    private Call<RecordCopyWorkBean> recordCopyWorkBeanCall;
    //post请求体参数
    private PostParams postParams;

    private Handler handler;
    //数字键盘列表数据
    public MutableLiveData<List<String>> keyboardLiveData = new MutableLiveData<>();
    //判断口令是否正确
    public MutableLiveData<Boolean> isright = new MutableLiveData<>();
    //口令
    public ObservableField<String> command = new ObservableField<>();
    //口令错误提示
    public ObservableField<String> commanderrortip = new ObservableField<>();
    //数字键盘列表
    private List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        postParams = new PostParams();
        handler = new Handler();

        command.set("");
        //获取资源文件数据
        String[] keyboards = BaseApplication.getContext().getResources().getStringArray(R.array.keyboard1);
        for (String key : keyboards) {
            stringList.add(key);
        }
        keyboardLiveData.setValue(stringList);
    }

    /**
     * 删除键盘口令
     */
    public void onClearCommand() {
        if (command.get() != null) {
            if (EmptyUtils.stringIsEmpty(command.get())) {
                if (command.get().length() != 0) {
                    String strcommand = command.get().substring(0, command.get().length() - 1);
                    command.set(strcommand);
                } else {
                    command.set("");
                }
            }
        }
    }

    /**
     * 复制作业Runnable
     */
    public void copyHomeWorkRunnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                copyHomeWork(GetBeanDate.getUserUuid(), command.get());
            }
        });
    }

    /**
     * 复制作业
     *
     * @param uuid     用户uuid
     * @param password 作业口令
     */
    public void copyHomeWork(String uuid, String password) {
        //请求参数
        HashMap hashMap = postParams.add("uuid", uuid).add("password", password).getHashMap();
        final RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        recordCopyWorkBeanCall = iRequest.copyhomework(BaseApplication.getHeaders(), requestBody);
        recordCopyWorkBeanCall.enqueue(new Callback<RecordCopyWorkBean>() {
            @Override
            public void onResponse(Call<RecordCopyWorkBean> call, Response<RecordCopyWorkBean> response) {//请求成功
                if (response.isSuccessful()) {
                    if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                        TipToast.tipToastShort("复制成功");
                        activityFinish();
                    } else {
                        commanderrortip.set(response.body().getMsg());
                        isright.setValue(true);
                    }
                } else {
                    commanderrortip.set(BaseApplication.getContext().getString(R.string.command_failure));
                    isright.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<RecordCopyWorkBean> call, Throwable t) {//请求失败
            }
        });
    }

    /**
     * 销毁活动
     */
    public void activityFinish() {
        BaseApplication.getInstance().finishActivity(RecordInputActivity.class);
        finish();
    }
}
