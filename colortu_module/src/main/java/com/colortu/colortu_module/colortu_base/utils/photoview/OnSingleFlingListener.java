package com.colortu.colortu_module.colortu_base.utils.photoview;

import android.view.MotionEvent;

/**
 * @author : Code23
 * @time : 2021/8/4
 * @module : OnSingleFlingListener
 * @describe :
 */
public interface OnSingleFlingListener {
    boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
}
