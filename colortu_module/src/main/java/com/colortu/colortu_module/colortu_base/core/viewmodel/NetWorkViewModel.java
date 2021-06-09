package com.colortu.colortu_module.colortu_base.core.viewmodel;

import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.data.GetBeanDate;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;
import com.colortu.colortu_module.colortu_base.utils.SuicideUtils;

/**
 * @author : Code23
 * @time : 2021/5/20
 * @module : NetWorkViewModel
 * @describe :WiFi切换移动流量界面ViewModel
 */
public class NetWorkViewModel extends BaseActivityViewModel<BaseRequest> {
    private int status = 0;

    /**
     * 不同意
     */
    public void onDisagree() {
        status = 1;
        GetBeanDate.putAgreeNetWork(false);
        BaseApplication.getInstance().exitApp();
    }

    /**
     * 同意
     */
    public void onAgree() {
        status = 2;
        GetBeanDate.putAgreeNetWork(true);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (status == 0) {
            GetBeanDate.putAgreeNetWork(false);
            BaseApplication.getInstance().exitApp();
        }
    }
}
