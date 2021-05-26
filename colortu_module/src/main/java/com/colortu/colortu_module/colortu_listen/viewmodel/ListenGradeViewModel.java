package com.colortu.colortu_module.colortu_listen.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.ListenGradeBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenGradeViewModel
 * @describe :听写年级界面ViewModel
 */
public class ListenGradeViewModel extends BaseActivityViewModel<BaseRequest> {
    //获取年级列表数据请求
    private Call<ListenGradeBean> listenGradeBeanCall;

    //年级列表数据
    public MutableLiveData<List<ListenGradeBean.DataBean.ListBean>> listenGradeBeanLiveData = new MutableLiveData<>();

    private List<Integer> integerList = new ArrayList<>();
    public MutableLiveData<List<Integer>> integerLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        integerList.add(R.mipmap.icon_listen_grade1);
        integerList.add(R.mipmap.icon_listen_grade2);
        integerList.add(R.mipmap.icon_listen_grade3);
        integerList.add(R.mipmap.icon_listen_grade4);
        integerList.add(R.mipmap.icon_listen_grade5);
        integerList.add(R.mipmap.icon_listen_grade6);
        integerLiveData.setValue(integerList);
    }

    /**
     * 获取年级列表数据请求
     */
    public void getGrade() {
        listenGradeBeanCall = iRequest.getGrade();
        listenGradeBeanCall.enqueue(new Callback<ListenGradeBean>() {
            @Override
            public void onResponse(Call<ListenGradeBean> call, Response<ListenGradeBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.listIsEmpty(response.body().getData().getList())) {
                        //获取数据
                        listenGradeBeanLiveData.setValue(response.body().getData().getList());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListenGradeBean> call, Throwable t) {//请求失败
            }
        });
    }
}
