package com.colortu.colortu_module.colortu_mine.viewmodel;

import androidx.databinding.ObservableField;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;

/**
 * @author : Code23
 * @time : 2020/12/7
 * @module : MineVitalityViewModel
 * @describe :元气界面ViewModel
 */
public class MineVitalityViewModel extends BaseActivityViewModel<BaseRequest> {
    //元气值
    public ObservableField<String> vitality = new ObservableField<>();

    @Override
    protected void onCreate() {
        super.onCreate();
    }

    /**
     * 跳转元气规则界面
     */
    public void onJumpVitalityRule() {
        BaseUIKit.startActivity(UIKitName.MINE_VITALITY, UIKitName.MINE_VITALITYRULE,
                BaseConstant.MINE_VITALITYRULE, BaseUIKit.OTHER, null);
    }

    /**
     * 跳转元气活动界面
     */
    public void onJumpVitalityEnvent() {
        BaseUIKit.startActivity(UIKitName.MINE_VITALITY, UIKitName.MINE_VITALITYEVENT,
                BaseConstant.MINE_VITALITYEVENT, BaseUIKit.OTHER, null);
    }
}
