package com.colortu.colortu_module.colortu_base.dialog;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;

import androidx.databinding.ObservableField;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseDialog;
import com.colortu.colortu_module.databinding.DialogBaseAffirmBinding;

/**
 * @author : Code23
 * @time : 2020/11/30
 * @module : DialogAffirm
 * @describe :单确认弹窗
 */
public class DialogAffirm extends BaseDialog<DialogBaseAffirmBinding> {
    public ObservableField<String> title = new ObservableField<>();

    @Override
    public int getLayoutId() {
        return R.layout.dialog_base_affirm;
    }

    public DialogAffirm(Context context) {
        super(context, 0, Gravity.CENTER, true, true);
        mdialogbinding.setViewmodel(this);
        mdialogbinding.dialogAffirmContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    /**
     * 设置标题
     *
     * @param content
     */
    public void setContent(String content) {
        title.set(content);
    }

    /**
     * 确认
     */
    public void onAffirm() {
        onAffirmListener.onAffirm();
    }

    public void setOnAffirmListener(OnAffirmListener onAffirmListener) {
        this.onAffirmListener = onAffirmListener;
    }

    private OnAffirmListener onAffirmListener;

    public interface OnAffirmListener {
        void onAffirm();
    }
}
