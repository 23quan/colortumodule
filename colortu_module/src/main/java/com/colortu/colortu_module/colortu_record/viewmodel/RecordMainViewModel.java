package com.colortu.colortu_module.colortu_record.viewmodel;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;

/**
 * @author : Code23
 * @time : 2021/3/31
 * @module : RecordMainViewModel
 * @describe :作业记录界面ViewModel
 */
public class RecordMainViewModel extends BaseActivityViewModel<BaseRequest> {
    /**
     * 跳转作业记录
     */
    public void onJumpRecordSubject() {
        BaseUIKit.startActivity(UIKitName.RECORD_MAIN, UIKitName.RECORD_SUBJECT,
                BaseConstant.RECORD_SUBJECT, BaseUIKit.OTHER, null);
    }

    /**
     * 跳转作业历史
     */
    public void onJumpHistory() {
        BaseUIKit.startActivity(UIKitName.RECORD_MAIN, UIKitName.RECORD_HISTORY,
                BaseConstant.RECORD_HISTORY, BaseUIKit.OTHER, null);
    }
}
