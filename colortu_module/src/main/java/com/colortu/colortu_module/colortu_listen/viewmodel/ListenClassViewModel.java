package com.colortu.colortu_module.colortu_listen.viewmodel;

import android.os.Handler;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.ListenClassBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenClassViewModel
 * @describe :听写课界面ViewModel
 */
public class ListenClassViewModel extends BaseActivityViewModel<BaseRequest> {
    //获取课文及词汇请求
    private Call<ListenClassBean> listenClassBeanCall;

    //课目数据列表
    public MutableLiveData<List<ListenClassBean.DataBean.PoetryVOSBean>> listenClassBeanLiveData = new MutableLiveData<>();

    private Handler handler;
    //标题
    public ObservableField<String> nametip = new ObservableField<>();
    //点击提示
    public ObservableField<String> clicktip = new ObservableField<>();
    //年级id
    public ObservableField<Integer> gid = new ObservableField<>();
    //课目id
    public ObservableField<Integer> subjectid = new ObservableField<>();
    //版本id
    public ObservableField<Integer> publisherid = new ObservableField<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        handler = new Handler();

        getClassWordsRunnable();
    }

    /**
     * 获取课文及词汇请求Runnable
     */
    public void getClassWordsRunnable() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getClassWords(gid.get(), subjectid.get(), publisherid.get(), GetBeanDate.getUserUuid());
            }
        });
    }

    /**
     * 获取课文及词汇请求
     *
     * @param gid
     * @param subjectid
     * @param publisherid
     * @param uuid
     */
    public void getClassWords(int gid, int subjectid, int publisherid, String uuid) {
        listenClassBeanCall = iRequest.getClassWords(gid, subjectid, publisherid, uuid);
        listenClassBeanCall.enqueue(new Callback<ListenClassBean>() {
            @Override
            public void onResponse(Call<ListenClassBean> call, Response<ListenClassBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    listenClassBeanLiveData.setValue(response.body().getData().getPoetryVOS());
                }
            }

            @Override
            public void onFailure(Call<ListenClassBean> call, Throwable t) {//请求失败
            }
        });
    }
}
