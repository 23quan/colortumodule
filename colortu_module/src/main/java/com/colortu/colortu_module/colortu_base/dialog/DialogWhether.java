package com.colortu.colortu_module.colortu_base.dialog;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;

import androidx.databinding.ObservableField;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseDialog;
import com.colortu.colortu_module.databinding.DialogBaseWhetherBinding;

/**
 * @author : Code23
 * @time : 2020/11/30
 * @module : DialogWhether
 * @describe :是否选择弹窗
 */
public class DialogWhether extends BaseDialog<DialogBaseWhetherBinding> {
    public ObservableField<String> title = new ObservableField<>();

    @Override
    public int getLayoutId() {
        return R.layout.dialog_base_whether;
    }

    public DialogWhether(Context context) {
        super(context, 0, Gravity.CENTER, true, true);
        mdialogbinding.setViewmodel(this);
        mdialogbinding.dialogWhetherContent.setMovementMethod(ScrollingMovementMethod.getInstance());
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
     * 否
     */
    public void onNo() {
        onWhetherListener.onNo();
    }

    /**
     * 是
     */
    public void onYes() {
        onWhetherListener.onYes();
    }

    public void setOnWhetherListener(OnWhetherListener onWhetherListener) {
        this.onWhetherListener = onWhetherListener;
    }

    private OnWhetherListener onWhetherListener;

    public interface OnWhetherListener {
        //否
        void onNo();

        //是
        void onYes();
    }
}
