package com.colortu.colortu_module.colortu_base.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.constant.BaseConstant;
import com.colortu.colortu_module.colortu_base.core.base.BaseApplication;
import com.colortu.colortu_module.colortu_base.core.base.BaseDialog;
import com.colortu.colortu_module.colortu_base.core.uikit.BaseUIKit;
import com.colortu.colortu_module.colortu_base.core.uikit.UIKitName;
import com.colortu.colortu_module.colortu_base.utils.ChannelUtil;
import com.colortu.colortu_module.databinding.DialogBaseAgreementBinding;

/**
 * @author : Code23
 * @time : 2021/4/14
 * @module : DialogAgreement
 * @describe :用户协议/隐私政策弹窗
 */
public class DialogAgreement extends BaseDialog<DialogBaseAgreementBinding> {
    //标题
    private String title;
    //用户协议
    private String agreement;
    //隐私政策
    private String policy;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_base_agreement;
    }

    public DialogAgreement(Context context) {
        super(context, 0, Gravity.CENTER, true, true);
        mdialogbinding.setViewmodel(this);
        if (BaseApplication.appType == 1) {//作业
            title = context.getResources().getString(R.string.work_agreement_content);
            policy = BaseApplication.getContext().getResources().getString(R.string.work_privacypolicy);
            if (!ChannelUtil.isHuaWei()) {
                agreement = BaseApplication.getContext().getResources().getString(R.string.work_useragreement);
            }
        } else {//听写
            title = context.getResources().getString(R.string.listen_agreement_content);
            policy = BaseApplication.getContext().getResources().getString(R.string.listen_privacypolicy);
            if (!ChannelUtil.isHuaWei()) {
                agreement = BaseApplication.getContext().getResources().getString(R.string.listen_useragreement);
            }
        }
        if (ChannelUtil.isHuaWei()) {
            agreement = BaseApplication.getContext().getResources().getString(R.string.agreement_huawei);
        }

        //SpannableStringBuilder实例化
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(title);
        //单独设置点击事件（用户协议）
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Bundle bundle = new Bundle();
                bundle.putString("content", agreement);
                BaseUIKit.startActivity(UIKitName.WORK_SPLASH, UIKitName.BASE_AGREEMENT,
                        BaseConstant.BASE_AGREEMENT, BaseUIKit.OTHER, bundle);
            }
        };
        spannableStringBuilder.setSpan(clickableSpan1, 29, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //单独设置点击事件（隐私政策）
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Bundle bundle = new Bundle();
                bundle.putString("content", policy);
                BaseUIKit.startActivity(UIKitName.WORK_SPLASH, UIKitName.BASE_AGREEMENT,
                        BaseConstant.BASE_AGREEMENT, BaseUIKit.OTHER, bundle);
            }
        };
        spannableStringBuilder.setSpan(clickableSpan2, 36, 42, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //不设置不生效
        mdialogbinding.dialogAgreementContent.setMovementMethod(LinkMovementMethod.getInstance());
        mdialogbinding.dialogAgreementContent.setText(spannableStringBuilder);
    }

    /**
     * 拒绝
     */
    public void onRefuseAgreement() {
        onClickAgreementListener.onRefuseAgreement();
    }

    /**
     * 同意
     */
    public void onAgreeAgreement() {
        onClickAgreementListener.onAgreeAgreement();
    }

    private OnClickAgreementListener onClickAgreementListener;

    public void setOnClickAgreementListener(OnClickAgreementListener onClickAgreementListener) {
        this.onClickAgreementListener = onClickAgreementListener;
    }

    public interface OnClickAgreementListener {
        //拒绝
        void onRefuseAgreement();

        //同意
        void onAgreeAgreement();
    }
}
