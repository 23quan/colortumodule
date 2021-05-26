package com.colortu.colortu_module.colortu_base.core.viewmodel;

import android.os.Message;

import androidx.lifecycle.MutableLiveData;

import com.colortu.colortu_module.colortu_base.core.http.NetworkManager;

import java.lang.reflect.ParameterizedType;

/**
 * @author : Code23
 * @time : 2020/11/25
 * @name : BaseFragmentViewModel
 * @Parameters :
 * @describe : 每个Activity对应一个viewmodel，又或者多个Fragment同Activity共享一个viewmodel，
 * 负责拿到model(请求网络或者封装)，通过Livedata完成页面数据更新
 */
public abstract class BaseFragmentViewModel<Q> {
    //关闭页面
    public MutableLiveData<Void> finish;
    //如果上个页面使用了startActivityForResult，则本页面回传值需要使用此参数进行回传值的操作
    public MutableLiveData<Void> forResult;
    //Fragment数据共享
    public MutableLiveData<Message> fragmentShareData;

    protected Q iRequest;

    public BaseFragmentViewModel() {
        Class<Q> tClass = getTClass();
        if (!tClass.equals(Void.class)) {
            iRequest = NetworkManager.instance().create(tClass);
        }
    }

    public void initData(MutableLiveData<Void> finish, MutableLiveData<Void> forResult, MutableLiveData<Message> fragmentShareData) {
        this.finish = finish;
        this.forResult = forResult;
        this.fragmentShareData = fragmentShareData;
    }

    protected void create() {

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

