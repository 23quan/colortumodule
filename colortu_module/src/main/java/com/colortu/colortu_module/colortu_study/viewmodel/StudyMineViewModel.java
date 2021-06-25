package com.colortu.colortu_module.colortu_study.viewmodel;

import androidx.lifecycle.MutableLiveData;

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
import com.colortu.colortu_module.colortu_base.bean.StudyCloseBean;
import com.colortu.colortu_module.colortu_base.bean.StudyMineBean;
import com.colortu.colortu_module.colortu_base.bean.StudyOpenBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/4/8
 * @module : StudyMineViewModel
 * @describe :我的自习室界面ViewModel
 */
public class StudyMineViewModel extends BaseActivityViewModel<BaseRequest> {
    //我的自习室列表接口
    private Call<StudyMineBean> studyMineBeanCall;
    //关闭自习室接口
    private Call<StudyCloseBean> studyCloseBeanCall;
    //打开自习室接口
    private Call<StudyOpenBean> studyOpenBeanCall;
    //post请求体参数
    private PostParams postParams;

    //我创建的列表数据
    public MutableLiveData<List<StudyMineBean.DataBean.MyCreateBean>> myCreateBeanLiveData = new MutableLiveData<>();
    //我加入的列表数据
    public MutableLiveData<List<StudyMineBean.DataBean.MyInJoinBean>> myInJoinBeanLiveData = new MutableLiveData<>();
    //已过期的列表数据
    public MutableLiveData<List<StudyMineBean.DataBean.DeprecatedBean>> deprecatedBeaLiveData = new MutableLiveData<>();

    //false无数据,true有数据
    public MutableLiveData<Boolean> isshow = new MutableLiveData<>();

    @Override
    protected void onResume() {
        super.onResume();
        //实例化
        postParams = new PostParams();

        getStudyMine(GetBeanDate.getUserUuid());
    }

    /**
     * 跳转创建自习室界面
     */
    public void onJumpMineCreate() {
        if (ChannelUtil.isXiaoMi()) {
            BaseUIKit.startActivity(UIKitName.STUDY_MINE, UIKitName.STUDY_AUDIOCREATE,
                    BaseConstant.STUDY_AUDIOCREATE, BaseUIKit.OTHER, null);
        } else {
            BaseUIKit.startActivity(UIKitName.STUDY_MINE, UIKitName.STUDY_CREATE,
                    BaseConstant.STUDY_CREATE, BaseUIKit.OTHER, null);
        }
    }

    /**
     * 跳转口令邀请界面
     */
    public void onJumpMineCommand() {
        BaseUIKit.startActivity(UIKitName.STUDY_MINE, UIKitName.STUDY_INPUTCOMMAND,
                BaseConstant.STUDY_INPUTCOMMAND, BaseUIKit.OTHER, null);
    }

    /**
     * 我的自习室列表接口请求
     *
     * @param uuid 用户uuid
     */
    public void getStudyMine(String uuid) {
        studyMineBeanCall = iRequest.getStudyMine(BaseApplication.getHeaders(), uuid);
        studyMineBeanCall.enqueue(new Callback<StudyMineBean>() {
            @Override
            public void onResponse(Call<StudyMineBean> call, Response<StudyMineBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    //我创建的列表
                    myCreateBeanLiveData.setValue(response.body().getData().getMyCreate());

                    //我加入的列表
                    myInJoinBeanLiveData.setValue(response.body().getData().getMyInJoin());

                    //已过期的列表
                    deprecatedBeaLiveData.setValue(response.body().getData().getDeprecated());

                    //false无数据,true有数据
                    if (EmptyUtils.listIsEmpty(response.body().getData().getMyCreate()) ||
                            EmptyUtils.listIsEmpty(response.body().getData().getMyInJoin()) ||
                            EmptyUtils.listIsEmpty(response.body().getData().getDeprecated())) {
                        isshow.setValue(true);
                    } else {
                        isshow.setValue(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<StudyMineBean> call, Throwable t) {//请求失败

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
                    getStudyMine(GetBeanDate.getUserUuid());
                }
            }

            @Override
            public void onFailure(Call<StudyCloseBean> call, Throwable t) {//请求失败

            }
        });
    }

    /**
     * 打开自习室接口实现
     *
     * @param id
     */
    public void getStudyOpen(int id) {
        HashMap hashMap = postParams.add("id", id).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        studyOpenBeanCall = iRequest.getStudyOpen(BaseApplication.getHeaders(), requestBody);
        studyOpenBeanCall.enqueue(new Callback<StudyOpenBean>() {
            @Override
            public void onResponse(Call<StudyOpenBean> call, Response<StudyOpenBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    getStudyMine(GetBeanDate.getUserUuid());
                }
            }

            @Override
            public void onFailure(Call<StudyOpenBean> call, Throwable t) {//请求失败

            }
        });
    }
}
