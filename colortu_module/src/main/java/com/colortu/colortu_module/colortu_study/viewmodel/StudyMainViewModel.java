package com.colortu.colortu_module.colortu_study.viewmodel;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;

/**
 * @author : Code23
 * @time : 2021/3/31
 * @module : StudyMainViewModel
 * @describe :自习室界面ViewModel
 */
public class StudyMainViewModel extends BaseActivityViewModel<BaseRequest> {
    /**
     * 跳转自习室广场
     */
    public void onJumpStudyPlaza() {
        BaseUIKit.startActivity(UIKitName.STUDY_MAIN, UIKitName.STUDY_PLAZA,
                BaseConstant.STUDY_PLAZA, BaseUIKit.OTHER, null);
    }

    /**
     * 跳转我的自习室
     */
    public void onJumpMineStudyRoom() {
        BaseUIKit.startActivity(UIKitName.STUDY_MAIN, UIKitName.STUDY_MINE,
                BaseConstant.STUDY_MINE, BaseUIKit.OTHER, null);
    }
}
