package com.colortu.colortu_module.colortu_teach.viewmodel;

import android.os.Handler;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSON;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_base.bean.TeachDeleteTeachBookBean;
import com.colortu.colortu_module.colortu_base.bean.TeachMainBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachMainViewModel
 * @describe :我的教辅界面ViewModel
 */
public class TeachMainViewModel extends BaseActivityViewModel<BaseRequest> {
    //获取我的教辅请求
    private Call<TeachMainBean> teachMainBeanCall;
    //我的教辅-删除请求
    private Call<TeachDeleteTeachBookBean> teachDeleteTeachBookBeanCall;

    //我的教辅列表数据
    public MutableLiveData<List<TeachMainBean.DataBean.RecordsBean>> teachMainBeanLiveData = new MutableLiveData<>();

    private Handler handler;
    //编辑/完成
    public ObservableField<String> edit = new ObservableField<>();
    //控制编辑和完成转换
    public MutableLiveData<Boolean> booleanLiveData = new MutableLiveData<>();
    //我的教辅id
    public ObservableField<Integer> ids = new ObservableField<>();
    //判断我的教辅是否为空
    public MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        handler = new Handler();

        edit.set(BaseApplication.getInstance().getString(R.string.edit));
        booleanLiveData.setValue(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCheckBeanRunnable();
    }

    /**
     * 选教辅
     */
    public void onChooseTeachBook() {
        BaseUIKit.startActivity(UIKitName.TEACH_MAIN, UIKitName.TEACH_BOOK,
                BaseConstant.TEACH_BOOK, BaseUIKit.OTHER, null);
    }

    /**
     * 编辑教辅
     */
    public void onEdit() {
        if (booleanLiveData.getValue()) {
            edit.set(BaseApplication.getInstance().getString(R.string.edit));
            booleanLiveData.setValue(false);
        } else {
            edit.set(BaseApplication.getInstance().getString(R.string.finish));
            booleanLiveData.setValue(true);
        }
    }

    /**
     * 获取我的教辅Runnable
     */
    public void getCheckBeanRunnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getCheckBean(GetBeanDate.getUserUuid());
            }
        });
    }

    /**
     * 获取我的教辅
     *
     * @param uuid 用户uuid
     */
    public void getCheckBean(String uuid) {
        teachMainBeanCall = iRequest.getMineTeachBookbean(BaseApplication.getHeaders(), uuid);
        teachMainBeanCall.enqueue(new Callback<TeachMainBean>() {
            @Override
            public void onResponse(Call<TeachMainBean> call, Response<TeachMainBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.listIsEmpty(response.body().getData().getRecords())) {
                        GetBeanDate.putMineTeachBook(JSON.toJSONString(response.body().getData()));
                        isEmpty.setValue(false);
                        teachMainBeanLiveData.setValue(response.body().getData().getRecords());
                    } else {
                        GetBeanDate.putMineTeachBook(null);
                        isEmpty.setValue(true);
                    }
                } else {
                    isEmpty.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<TeachMainBean> call, Throwable t) {//请求失败
            }
        });
    }

    /**
     * 我的教辅-删除Runnable
     */
    public void deleteTeachBookRunnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                deleteTeachBook(ids.get(), GetBeanDate.getUserUuid());
            }
        });
    }

    /**
     * 我的教辅-删除
     *
     * @param ids  教辅id
     * @param uuid 用户uuid
     */
    public void deleteTeachBook(int ids, String uuid) {
        teachDeleteTeachBookBeanCall = iRequest.deleteteachbook(BaseApplication.getHeaders(), ids, uuid);
        teachDeleteTeachBookBeanCall.enqueue(new Callback<TeachDeleteTeachBookBean>() {
            @Override
            public void onResponse(Call<TeachDeleteTeachBookBean> call, Response<TeachDeleteTeachBookBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() == 200) {
                    getCheckBeanRunnable();
                } else {
                    TipToast.tipToastShort("删除失败");
                }
            }

            @Override
            public void onFailure(Call<TeachDeleteTeachBookBean> call, Throwable t) {//请求失败
            }
        });
    }
}
