package com.colortu.colortu_module.colortu_base.core.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;

/**
 * @author : Code23
 * @time : 2020/11/30
 * @module : BaseDialog
 * @describe :Dialog父类
 */
public abstract class BaseDialog<T extends ViewDataBinding> extends Dialog {
    public T mdialogbinding;

    //设置layoutId
    public abstract int getLayoutId();

    public BaseDialog(@NonNull Context context) {
        super(context);
        initView();
        mdialogbinding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), null, false);
        setContentView(mdialogbinding.getRoot());
    }

    private void initView() {
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawableResource(R.color.base_transparent);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
    }

    /**
     * @param context           上下文
     * @param themeResId        主题
     * @param gravity           对齐方式
     * @param backCancelable    返回键是否可以取消
     * @param outsideCancelable 外部点击是否可以取消
     */
    public BaseDialog(@NonNull Context context, int themeResId, int gravity, boolean backCancelable, boolean outsideCancelable) {
        super(context, themeResId);
        initView(context, themeResId, gravity, backCancelable, outsideCancelable);
        mdialogbinding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), null, false);
        setContentView(mdialogbinding.getRoot());
    }

    private void initView(Context context, int themeResId, int gravity, boolean backCancelable, boolean outsideCancelable) {
        this.setCancelable(backCancelable);
        this.setCanceledOnTouchOutside(outsideCancelable);
        Window dialogWindow = this.getWindow();
        dialogWindow.requestFeature(Window.FEATURE_NO_TITLE);
        dialogWindow.setBackgroundDrawableResource(R.color.base_transparent);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = gravity;
        dialogWindow.setAttributes(layoutParams);
    }
}
