package com.colortu.colortu_module.colortu_qrcode.viewmodel;

import androidx.databinding.ObservableField;

import com.colortu.colortu_module.colortu_base.core.viewmodel.BaseActivityViewModel;
import com.colortu.colortu_module.colortu_base.request.BaseRequest;

/**
 * @author : Code23
 * @time : 2021/3/26
 * @module : QrcodeTextTipViewModel
 * @describe :文字提示界面ViewModel
 */
public class QrcodeTextTipViewModel extends BaseActivityViewModel<BaseRequest> {
    //文字提示信息
    public ObservableField<String> codetip1 = new ObservableField<>();
    //文字提示信息
    public ObservableField<String> codetip2 = new ObservableField<>();
    //文字提示信息
    public ObservableField<String> codetip3 = new ObservableField<>();
    //跳过提示信息
    public ObservableField<String> skiptip = new ObservableField<>();
}
