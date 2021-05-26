package com.colortu.colortu_module.colortu_base.core.viewmodel;

import androidx.databinding.ObservableField;

import com.colortu.colortu_module.colortu_base.request.BaseRequest;

/**
 * @author : Code23
 * @time : 2021/5/11
 * @module : AgreementViewModel
 * @describe :隐私/用户协议界面ViewModel
 */
public class AgreementViewModel extends BaseActivityViewModel<BaseRequest> {
    //文本内容
    public ObservableField<String> content = new ObservableField<>();
}
