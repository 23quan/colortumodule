package com.colortu.colortu_module.colortu_base.core.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.colortu.colortu_module.BR;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseFragmentViewModel;

import java.lang.reflect.ParameterizedType;

/**
 * @author : Code23
 * @time : 2020/11/24
 * @module : BaseFragment
 * @describe :Fragment父类
 */
public abstract class BaseFragment<VM extends BaseFragmentViewModel, VDB extends ViewDataBinding> extends Fragment {
    public VM viewModel = initFragViewModel();
    public VDB binding;

    public abstract VM initFragViewModel();

    //设置layoutId
    protected abstract int getLayoutId();
    //初始化视图
    protected abstract void initView(Bundle savedInstanceState);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            // 每次ViewPager要展示该页面时，均会调用该方法获取显示的View
            binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            binding.setVariable(BR.viewmodel, viewModel);
            initView(savedInstanceState);
        }
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public VM getBaseFragmentViewModel() {
        return viewModel;
    }

    /**
     * 获取泛型对相应的Class对象
     *
     * @return
     */
    private Class<VM> getTClass() {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        //返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
        return (Class) type.getActualTypeArguments()[0];//<T>
    }

    /**
     * @param path 传送Activity的
     */
    public void intentByRouter(String path) {
        ARouter.getInstance().build(path).navigation(getContext());
    }

    /**
     * @param path   传送Activity的
     * @param bundle
     */
    public void intentByRouter(String path, Bundle bundle) {
        ARouter.getInstance().build(path).with(bundle).navigation(getContext());
    }

    /**
     * @param path   传送Activity的
     * @param bundle
     */
    public void intentForResultByRouter(String path, Bundle bundle, int requestCode) {
        ARouter.getInstance().build(path).with(bundle).navigation(getActivity(), requestCode);
    }

    /**
     * @param path 传送Activity的
     */
    public void intentForResultByRouter(String path, int requestCode) {
        ARouter.getInstance().build(path).navigation(getActivity(), requestCode);
    }
}
