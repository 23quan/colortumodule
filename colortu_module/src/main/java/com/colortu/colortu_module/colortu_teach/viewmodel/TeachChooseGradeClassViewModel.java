package com.colortu.colortu_module.colortu_teach.viewmodel;

import android.os.Handler;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.PostParams;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.EmptyUtils;
import com.colortu.colortu_module.colortu_base.utils.TipToast;
import com.colortu.colortu_module.colortu_teach.activity.TeachBookActivity;
import com.colortu.colortu_module.colortu_teach.activity.TeachBookListActivity;
import com.colortu.colortu_module.colortu_base.bean.TeachAddTeachBookBean;
import com.colortu.colortu_module.colortu_base.bean.TeachChooseGradeBean;
import com.colortu.colortu_module.colortu_base.bean.TeachMainBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachChooseGradeClassViewModel
 * @describe :选择教辅年级界面ViewModel
 */
public class TeachChooseGradeClassViewModel extends BaseActivityViewModel<BaseRequest> {
    //获取选择教辅年级数据请求
    private Call<TeachChooseGradeBean> teachChooseGradeBeanCall;
    //我的教辅-添加请求
    private Call<TeachAddTeachBookBean> teachAddTeachBookBeanCall;
    //post请求体参数
    private PostParams postParams;

    //我的教辅列表数据
    public ObservableField<TeachMainBean.DataBean> dataBean = new ObservableField<>();

    //选择教辅年级数据
    public MutableLiveData<List<TeachChooseGradeBean.DataBean.ListBean>> teachChooseGradeBeanLiveData = new MutableLiveData<>();
    //已选择教辅年级数据
    public ObservableField<List<TeachChooseGradeBean.DataBean.ListBean>> listBean = new ObservableField<>();

    private Handler handler;
    //教辅id
    public ObservableField<Integer> id = new ObservableField<>();
    //教辅名字
    public ObservableField<String> teachbookame = new ObservableField<>();
    //0 不是会员 1是会员
    private int isVip;
    //1锁定 2解锁
    private int lockStatus;

    @Override
    protected void onCreate() {
        super.onCreate();
        //实例化
        postParams = new PostParams();
        handler = new Handler();

        if (id.get() != null) {
            getChooseGrade(id.get());
        }

        isVip = GetBeanDate.getIsBookVIP();
        if (isVip == 1) {
            lockStatus = 2;
        } else {
            lockStatus = 1;
        }
    }

    /**
     * 返回
     */
    public void onBack() {
        finish();
    }

    /**
     * 选择好了
     */
    public void onChosen() {
        for (int i = 0; i < listBean.get().size(); i++) {
            TeachChooseGradeBean.DataBean.ListBean bean = listBean.get().get(i);
            addTeachBookRunnable(i, GetBeanDate.getUserUuid(),
                    bean.getId(), bean.getTitle(), lockStatus,
                    bean.getSubject_id(), bean.getGrade_id(),
                    bean.getSemester_id());
        }
    }

    /**
     * 获取选择教辅年级数据
     *
     * @param id 父级id
     */
    public void getChooseGrade(int id) {
        //请求参数
        HashMap hashMap = postParams.add("parent_id", id).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        teachChooseGradeBeanCall = iRequest.getchoosegrade(BaseApplication.getHeaders(), requestBody);
        teachChooseGradeBeanCall.enqueue(new Callback<TeachChooseGradeBean>() {
            @Override
            public void onResponse(Call<TeachChooseGradeBean> call, Response<TeachChooseGradeBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && EmptyUtils.objectIsEmpty(response.body().getData())) {
                    teachChooseGradeBeanLiveData.setValue(response.body().getData().getList());
                } else {
                    teachChooseGradeBeanLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TeachChooseGradeBean> call, Throwable t) {//请求失败
            }
        });
    }

    /**
     * 我的教辅-添加
     */
    public void addTeachBookRunnable(final int index, final String uuid, final int bookId, final String title,
                                     final int lockStatus, final int subjectId, final int gradeId, final int semesterId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                addTeachBook(index, uuid, bookId, title, lockStatus, subjectId, gradeId, semesterId);
            }
        });
    }

    /**
     * 我的教辅-添加
     *
     * @param uuid       用户uuid
     * @param bookId     子级id
     * @param title      书名
     * @param lockStatus 是否解锁
     * @param subjectId  科目id
     * @param gradeId    年级id
     * @param semesterId 学期id
     */
    public void addTeachBook(final int index, String uuid, int bookId, String title, int lockStatus, int subjectId, int gradeId, int semesterId) {
        //请求参数
        HashMap hashMap = postParams
                .add("uuid", uuid).add("bookId", bookId).add("title", title).add("lockStatus", lockStatus)
                .add("subjectId", subjectId).add("gradeId", gradeId).add("semesterId", semesterId).getHashMap();
        RequestBody requestBody = postParams.getGsonRequestBody(hashMap);

        teachAddTeachBookBeanCall = iRequest.addteachbook(BaseApplication.getHeaders(), requestBody);
        teachAddTeachBookBeanCall.enqueue(new Callback<TeachAddTeachBookBean>() {
            @Override
            public void onResponse(Call<TeachAddTeachBookBean> call, Response<TeachAddTeachBookBean> response) {//请求成功
                if (EmptyUtils.objectIsEmpty(response.body()) && response.body().getCode() != 200) {
                    TipToast.tipToastShort(response.body().getMsg());
                }

                if ((index + 1) == listBean.get().size()) {
                    BaseApplication.getInstance().finishActivity(TeachBookActivity.class);
                    BaseApplication.getInstance().finishActivity(TeachBookListActivity.class);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<TeachAddTeachBookBean> call, Throwable t) {//请求失败
                if ((index + 1) == listBean.get().size()) {
                    BaseApplication.getInstance().finishActivity(TeachBookActivity.class);
                    BaseApplication.getInstance().finishActivity(TeachBookListActivity.class);
                    finish();
                }
            }
        });
    }
}
