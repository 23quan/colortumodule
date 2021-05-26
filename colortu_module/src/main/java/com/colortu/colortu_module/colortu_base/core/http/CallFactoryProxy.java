package com.colortu.colortu_module.colortu_base.core.http;

import androidx.annotation.Nullable;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author : Code23
 * @time : 2021/4/22
 * @module : CallFactoryProxy
 * @describe :拦截newCall(Request)方法
 */
public abstract class CallFactoryProxy implements Call.Factory {
    private static final String BASEURLNAME = "baseurlname";
    private final Call.Factory delegate;

    public CallFactoryProxy(Call.Factory delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call newCall(Request request) {
        String baseUrlName = request.header(BASEURLNAME);
        if (baseUrlName != null) {
            HttpUrl newHttpUrl = getNewUrl(baseUrlName, request);
            if (newHttpUrl != null) {
                Request newRequest = request.newBuilder().url(newHttpUrl).build();
                return delegate.newCall(newRequest);
            }
        }
        return delegate.newCall(request);
    }

    @Nullable
    protected abstract HttpUrl getNewUrl(String baseUrlName, Request request);
}
