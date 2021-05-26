package com.colortu.colortu_module.colortu_study.adapter;

import android.view.MotionEvent;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.colortu.colortu_module.R;
import com.colortu.colortu_module.colortu_base.core.base.BaseRecyclerAdapter;
import com.colortu.colortu_module.databinding.AdapterStudyKeyboardBinding;

/**
 * @author : Code23
 * @time : 2020/12/16
 * @module : StudyKeyBoardAdapter
 * @describe :数字键盘列表适配器
 */
public class StudyKeyBoardAdapter extends BaseRecyclerAdapter<String> {
    @Override
    public int getLayoutId() {
        return R.layout.adapter_study_keyboard;
    }

    @Override
    public void bindView(ViewDataBinding binding, final String item, final int position) {
        final AdapterStudyKeyboardBinding adapterStudyKeyboardBinding = (AdapterStudyKeyboardBinding) binding;
        adapterStudyKeyboardBinding.keyboardKey.setText(item);

        if (position != 10) {//数字键盘宽度和背景设置
            adapterStudyKeyboardBinding.keyboardKey.setBackgroundResource(R.drawable.base_blue9_bg);
        } else {//确定键盘宽度和背景设置
            adapterStudyKeyboardBinding.keyboardKey.setBackgroundResource(R.drawable.base_blue8_bg);
        }

        /**
         * 键盘item点击
         */
        adapterStudyKeyboardBinding.keyboardKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != 10) {//数字键盘监听
                    onClickKeyBoardListener.OnClickKeyBoard(item);
                } else {//确认键盘监听
                    onClickKeyBoardListener.OnClickSure();
                }
            }
        });

        /**
         * 键盘item触目监听
         */
        adapterStudyKeyboardBinding.keyboardKey.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下
                        if (position != 10) {
                            adapterStudyKeyboardBinding.keyboardKey.setBackgroundResource(R.drawable.base_blue8_bg);
                        }
                        break;
                    case MotionEvent.ACTION_UP://取消or离开
                    case MotionEvent.ACTION_CANCEL:
                        if (position != 10) {
                            adapterStudyKeyboardBinding.keyboardKey.setBackgroundResource(R.drawable.base_blue9_bg);
                        }
                        break;
                }
                return false;
            }
        });
    }

    public void setOnClickKeyBoardListener(OnClickKeyBoardListener onClickKeyBoardListener) {
        this.onClickKeyBoardListener = onClickKeyBoardListener;
    }

    private OnClickKeyBoardListener onClickKeyBoardListener;

    public interface OnClickKeyBoardListener {
        void OnClickKeyBoard(String key);

        void OnClickSure();
    }
}
