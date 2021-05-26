package com.colortu.colortu_module.colortu_listen.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.bean.ListenSubjectBean;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Code23
 * @time : 2021/3/29
 * @module : ListenSubjectViewModel
 * @describe :听写课目界面ViewModel
 */
public class ListenSubjectViewModel extends BaseActivityViewModel<BaseRequest> {
    private List<ListenSubjectBean> listenSubjectBeanList = new ArrayList<>();
    //科目数据
    public MutableLiveData<List<ListenSubjectBean>> listenSubjectBeanLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        listenSubjectBeanList.add(new ListenSubjectBean(1, "语文"));
        listenSubjectBeanList.add(new ListenSubjectBean(2, "英语"));
        listenSubjectBeanLiveData.setValue(listenSubjectBeanList);
    }
}
