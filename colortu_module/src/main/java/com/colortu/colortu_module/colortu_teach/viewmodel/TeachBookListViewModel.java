package com.colortu.colortu_module.colortu_teach.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.bean.TeachBookBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachBookListViewModel
 * @describe :选择教辅系列下教辅界面ViewModel
 */
public class TeachBookListViewModel extends BaseActivityViewModel<BaseRequest> {
    //获取教辅系列列表 - 语数英请求
    private Call<TeachBookBean> teachBookBeanCall;
    //post请求体参数
    private PostParams postParams;

    //教辅系列下语文数学教辅列表数据
    public MutableLiveData<List<TeachBookBean.DataBean.ListBeanX>> teachBookBeanLiveData = new MutableLiveData<>();

    //是否登录
    public MutableLiveData<Boolean> islogin = new MutableLiveData<>();
    //1英语 2语数英
    public ObservableField<Integer> type = new ObservableField<>();

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        postParams = new PostParams();

        islogin.setValue(GetBeanDate.getIsLogin());
        if (type.get() == 1) {//英语听力
            HashMap hashMap = postParams.add("pageSize", 1000).add("pageStartCount", 0)
                    .add("orderField", "top").add("orderAct", "desc").add("isEnglish", 1).getHashMap();
            getTeachBook(hashMap);
        } else {//语数英
            HashMap hashMap = postParams.add("pageSize", 1000).add("pageStartCount", 0)
                    .add("orderField", "top").add("orderAct", "desc").getHashMap();
            getTeachBook(hashMap);
        }
    }

    /**
     * 跳转帮助界面
     */
    public void onJumpHelp() {
        BaseUIKit.startActivity(UIKitName.TEACH_BOOKLIST, UIKitName.QRCODE_HELP,
                BaseConstant.QRCODE_HELP, BaseUIKit.OTHER, null);
    }

    /**
     * 获取教辅系列列表 - 语数英
     *
     * @param hashMap
     */
    private void getTeachBook(HashMap hashMap) {
        //请求参数
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        teachBookBeanCall = iRequest.getteachbook(BaseApplication.getHeaders(), requestBody);
        teachBookBeanCall.enqueue(new Callback<TeachBookBean>() {
            @Override
            public void onResponse(Call<TeachBookBean> call, Response<TeachBookBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    if (EmptyUtils.listIsEmpty(response.body().getData().getList())) {
                        teachBookBeanLiveData.setValue(response.body().getData().getList());
                    }
                }
            }

            @Override
            public void onFailure(Call<TeachBookBean> call, Throwable t) {//请求失败
            }
        });
    }
}
