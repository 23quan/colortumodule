package com.colortu.colortu_module.colortu_teach.viewmodel;

import android.os.Bundle;

import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;

/**
 * @author : Code23
 * @time : 2020/12/3
 * @module : TeachBookViewModel
 * @describe :选择教辅系列界面ViewModel
 */
public class TeachBookViewModel extends BaseActivityViewModel<BaseRequest> {
    @Override
    protected void onCreate() {
        super.onCreate();
    }

    /**
     * 选择英语听力
     */
    public void onChooseEnglish() {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        BaseUIKit.startActivity(UIKitName.TEACH_BOOK, UIKitName.TEACH_BOOKLIST,
                BaseConstant.TEACH_BOOKLIST, BaseUIKit.OTHER, bundle);
    }

    /**
     * 选择语文数学
     */
    public void onChooseChineseMath() {
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        BaseUIKit.startActivity(UIKitName.TEACH_BOOK, UIKitName.TEACH_BOOKLIST,
                BaseConstant.TEACH_BOOKLIST, BaseUIKit.OTHER, bundle);
    }
}
