package com.colortu.colortu_module.colortu_study.viewmodel;

import android.os.Bundle;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.bean.StudyCommandBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/4/16
 * @module : StudyInputCommandViewModel
 * @describe :输入自习室口令界面ViewModel
 */
public class StudyInputCommandViewModel extends BaseActivityViewModel<BaseRequest> {
    //自习室口令复制接口
    private Call<StudyCommandBean> studyCommandBeanCall;

    //口令
    public ObservableField<String> command = new ObservableField<>();
    //数字键盘列表
    private List<String> stringList = new ArrayList<>();
    //数字键盘列表数据
    public MutableLiveData<List<String>> keyboardLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        command.set("");
        //获取资源文件数据
        String[] keyboards = BaseApplication.getContext().getResources().getStringArray(R.array.keyboard2);
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
     * 自习室口令复制接口请求
     *
     * @param password 自习室口令
     */
    public void getStudyCommand(String password) {
        studyCommandBeanCall = iRequest.getStudyCommand(BaseApplication.getHeaders(), password);
        studyCommandBeanCall.enqueue(new Callback<StudyCommandBean>() {
            @Override
            public void onResponse(Call<StudyCommandBean> call, Response<StudyCommandBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.listIsEmpty(response.body().getData().getRecords())) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", response.body().getData().getRecords().get(0).getId());
                        bundle.putString("channel", response.body().getData().getRecords().get(0).getChannel());
                        BaseUIKit.startActivity(UIKitName.STUDY_INPUTCOMMAND, UIKitName.STUDY_DETAIL,
                                BaseConstant.STUDY_DETAIL, BaseUIKit.OTHER, bundle);
                        finish();
                    } else {
                        TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.studyroom_commanderror));
                    }
                } else {
                    TipToast.tipToastShort(BaseApplication.getContext().getResources().getString(R.string.studyroom_commanderror));
                }
            }

            @Override
            public void onFailure(Call<StudyCommandBean> call, Throwable t) {//请求失败

            }
        });
    }
}
