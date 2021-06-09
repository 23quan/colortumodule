package com.colortu.colortu_module.colortu_qrcode.viewmodel;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;

/**
 * @author : Code23
 * @time : 2021/6/2
 * @module : QrcodeAccountDetailViewModel
 * @describe :账号详情界面ViewModel
 */
public class QrcodeAccountDetailViewModel extends BaseActivityViewModel<BaseRequest> {
    /**
     * 切换账号
     */
    public void onJumpAccount() {
        BaseUIKit.startActivity(UIKitName.QRCODE_ACCOUNTDETAIL, UIKitName.QRCODE_ACCOUNT,
                BaseConstant.QRCODE_ACCOUNT, BaseUIKit.NONE, null);
    }
}
