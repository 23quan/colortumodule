package com.colortu.colortu_module.colortu_base.core.viewmodel;

import android.os.Message;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.http.NetworkManager;
import com.colortu.colortu_module.colortu_base.data.GetChannelDate;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Code23
 * @time : 2020/11/24
 * @module : BaseViewModel
 * @describe :每个Activity对应一个viewmodel，又或者多个Fragment同Activity共享一个viewmodel，
 * 负责拿到model(请求网络或者封装)，通过Livedata完成页面数据更新
 */
public abstract class BaseActivityViewModel<Q> extends ViewModel implements LifecycleObserver {
    //关闭页面
    public MutableLiveData<Void> finish = new MutableLiveData<>();
    //如果上个页面使用了startActivityForResult，则本页面回传值需要使用此参数进行回传值的操作
    public MutableLiveData<Void> forResult = new MutableLiveData<>();
    //Fragment数据共享
    public MutableLiveData<Message> fragmentShareData = new MutableLiveData<>();
    //BaseFragmentViewModel
    private List<BaseFragmentViewModel> baseFragmentViewModelList = new ArrayList<>();

    protected Q iRequest;
    private boolean isCreated;

    public BaseActivityViewModel() {
        Class<Q> tClass = getTClass();
        if (!tClass.equals(Void.class)) {
            iRequest = NetworkManager.instance().create(tClass);
        }
    }

    /**
     * 组合Fragment的viewmodel和寄存Activity的viewModel
     *
     * @param baseFragmentViewModel
     */
    public void addFragViewModel(BaseFragmentViewModel baseFragmentViewModel) {
        if (baseFragmentViewModel == null)
            return;
        baseFragmentViewModelList.add(baseFragmentViewModel);
        if (isCreated) {
            //如果activity已经创建，fragment后续加入
            baseFragmentViewModel.initData(finish, forResult, fragmentShareData);
            baseFragmentViewModel.create();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
        isCreated = true;
        for (BaseFragmentViewModel baseFragmentViewModel : baseFragmentViewModelList) {
            baseFragmentViewModel.initData(finish, forResult, fragmentShareData);
            baseFragmentViewModel.create();
        }
    }

    /**
     * 适配圆角水滴屏或刘海屏
     *
     * @param viewGroup
     */
    public void setAdapteScreen(ViewGroup viewGroup) {
        if (ChannelUtil.isXiaoMi()) {
            if (GetChannelDate.isWaterDrop(BaseApplication.getContext())) {
                viewGroup.setPadding(0, 60, 0, 0);
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
    }

    /**
     * 设置图片
     */
    @BindingAdapter("ressrc")
    public static void setResSrc(ImageView imageView, int resId) {
        Glide.with(BaseApplication.getContext()).load(resId).into(imageView);
    }

    /**
     * 设置Gif图片
     */
    @BindingAdapter("resgifsrc")
    public static void setResGifSrc(ImageView imageView, int resId) {
        Glide.with(BaseApplication.getContext()).asGif().load(resId).into(imageView);
    }

    /**
     * 设置图片
     */
    @BindingAdapter("urlsrc")
    public static void setUrlSrc(ImageView imageView, String url) {
        Glide.with(BaseApplication.getContext()).load(url).into(imageView);
    }

    /**
     * 设置图片
     */
    @BindingAdapter("loadsrc")
    public static void setLoadSrc(ImageView imageView, String url) {
        Glide.with(BaseApplication.getContext()).load(url).apply(new RequestOptions()
                .placeholder(R.drawable.base_img_loading)).into(imageView);
    }

    /**
     * 获取泛型对相应的Class对象
     *
     * @return
     */
    private Class<Q> getTClass() {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        //返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
        return (Class) parameterizedType.getActualTypeArguments()[0];//<T>
    }

    /**
     * finish页面
     */
    public void finish() {
        finish.setValue(null);
    }
}
