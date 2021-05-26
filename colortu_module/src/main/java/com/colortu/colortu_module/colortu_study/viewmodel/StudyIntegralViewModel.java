package com.colortu.colortu_module.colortu_study.viewmodel;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;

/**
 * @author : Code23
 * @time : 2021/4/15
 * @module : StudyIntegralViewModel
 * @describe :自习室积分界面ViewModel
 */
public class StudyIntegralViewModel extends BaseActivityViewModel<BaseRequest> {
    /**
     * 跳转元气商城
     */
    public void onJumpShop() {
        BaseUIKit.startActivity(UIKitName.STUDY_INTEGRAL, UIKitName.QRCODE_SHOP,
                BaseConstant.QRCODE_SHOP, BaseUIKit.OTHER, null);
    }
}
