package com.colortu.colortu_module.colortu_study.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.StudyPlazaFiltrateBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2021/4/8
 * @module : StudyPlazaFiltrateViewModel
 * @describe :自习广场列表筛选界面ViewModel
 */
public class StudyPlazaFiltrateViewModel extends BaseActivityViewModel<BaseRequest> {
    //自习广场列表筛选接口
    private Call<StudyPlazaFiltrateBean> studyPlazaFiltrateBeanCall;

    //区域界面显示
    public MutableLiveData<Boolean> areaShow = new MutableLiveData<>();
    //年级界面显示
    public MutableLiveData<Boolean> gradeShow = new MutableLiveData<>();
    //区域数据列表
    public MutableLiveData<List<String>> areaLiveData = new MutableLiveData<>();
    //年级数据列表
    public MutableLiveData<List<String>> gradeLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate() {
        super.onCreate();

        getPlazaFiltrate();
    }

    /**
     * 自习广场列表筛选接口请求
     */
    public void getPlazaFiltrate() {
        studyPlazaFiltrateBeanCall = iRequest.getPlazaFiltrate(BaseApplication.getHeaders());
        studyPlazaFiltrateBeanCall.enqueue(new Callback<StudyPlazaFiltrateBean>() {
            @Override
            public void onResponse(Call<StudyPlazaFiltrateBean> call, Response<StudyPlazaFiltrateBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    //区域数据列表
                    if (EmptyUtils.listIsEmpty(response.body().getData().getRegion())) {
                        areaShow.setValue(true);
                        areaLiveData.setValue(response.body().getData().getRegion());
                    } else {
                        areaShow.setValue(false);
                    }

                    //年级数据列表
                    if (EmptyUtils.listIsEmpty(response.body().getData().getGrade())) {
                        gradeShow.setValue(true);
                        gradeLiveData.setValue(response.body().getData().getGrade());
                    } else {
                        gradeShow.setValue(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<StudyPlazaFiltrateBean> call, Throwable t) {//请求失败

            }
        });
    }
}
