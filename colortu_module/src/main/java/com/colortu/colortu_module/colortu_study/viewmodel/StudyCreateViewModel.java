package com.colortu.colortu_module.colortu_study.viewmodel;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;

/**
 * @author : Code23
 * @time : 2021/6/10
 * @module : StudyCreateViewModel
 * @describe :创建自习室界面ViewModel
 */
public class StudyCreateViewModel extends BaseActivityViewModel<BaseRequest> {
    /**
     * 跳转手动创建自习室界面
     */
    public void onJumpManualCreate() {
        BaseUIKit.startActivity(UIKitName.STUDY_CREATE, UIKitName.STUDY_MANUALCREATE,
                BaseConstant.STUDY_MANUALCREATE, BaseUIKit.OTHER, null);
    }

    /**
     * 跳转语音创建自习室界面
     */
    public void onJumpAudioCreate() {
        BaseUIKit.startActivity(UIKitName.STUDY_CREATE, UIKitName.STUDY_AUDIOCREATE,
                BaseConstant.STUDY_AUDIOCREATE, BaseUIKit.OTHER, null);
    }
}
