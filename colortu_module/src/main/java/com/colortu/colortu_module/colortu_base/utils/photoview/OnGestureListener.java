package com.colortu.colortu_module.colortu_base.utils.photoview;

/**
 * @author : Code23
 * @time : 2021/8/4
 * @module : OnGestureListener
 * @describe :
 */
public interface OnGestureListener {
    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX, float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);
}
