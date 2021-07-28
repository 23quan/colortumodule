package com.colortu.colortu_module.colortu_base.core.api;

/**
 * @author : Code23
 * @time : 2020/12/17
 * @name : PermissionListener
 * @Parameters :
 * @describe : 授权监听
 */
public interface PermissionListener {
    //已授权或者授权成功
    void permissionSuccess();
   //未授权或授权失败
    void permissionFail();
}
