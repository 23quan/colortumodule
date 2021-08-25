package com.colortu.colortu_module.colortu_base.utils.sidebarutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.colortu.colortu_module.R;

@SuppressLint("AppCompatCustomView")
public class SideBar extends View {
    //字母列表
    private static final String[] letterList = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"};
    //绘画
    private Paint paint = new Paint();
    //滑动下标
    private int selectIndex = -1;
    //activity
    private Activity activity;
    //显示字符弹窗
    PopupWindow popupWindow = null;
    //自定义弹窗view
    View view;
    //显示字符textview
    TextView dialogSidebarContent;

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        activity = (Activity) context;
        init(context);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_base_sidebar, null);
        dialogSidebarContent = (TextView) view.findViewById(R.id.dialog_sidebar_content);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();
        float letterHeight = height / letterList.length;

        for (int i = 0; i < letterList.length; i++) {
            //抗锯齿
            paint.setAntiAlias(true);
            //设置颜色
            paint.setColor(i == selectIndex ? Color.BLACK : Color.WHITE);
            //设置字体大小
            paint.setTextSize(10);
            //计算字母的X坐标
            float xPos = width / 2 - paint.measureText(letterList[i]) / 2;
            //计算字母的Y坐标
            float yPos = (letterHeight * i) + letterHeight;
            canvas.drawText(letterList[i], xPos, yPos, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //判断是哪一个字母被点击了
        int nIndex = (int) (event.getY() / getHeight() * letterList.length);
        if (nIndex >= 0 && nIndex < letterList.length) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    //如果滑动改变
                    if (selectIndex != nIndex) {
                        selectIndex = nIndex;
                        showCharacter(letterList[selectIndex]);
                        if (onTouchAssortListener != null) {
                            onTouchAssortListener.onTouchAssortListener(letterList[selectIndex]);
                        }
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    selectIndex = nIndex;
                    showCharacter(letterList[selectIndex]);
                    if (onTouchAssortListener != null) {
                        onTouchAssortListener.onTouchAssortListener(letterList[selectIndex]);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    disShowCharacter();
                    selectIndex = -1;
                    break;
            }
        } else {
            selectIndex = -1;
            disShowCharacter();
        }
        invalidate();
        return true;
    }

    /**
     * 显示弹出的字符
     *
     * @param string
     */
    private void showCharacter(String string) {
        if (popupWindow != null) {
            dialogSidebarContent.setText(string);
        } else {
            popupWindow = new PopupWindow(view, 120, 120, false);
            popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
        dialogSidebarContent.setText(string);
    }

    /**
     * 取消弹出的字符
     */
    private void disShowCharacter() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    //触摸监听
    private OnTouchAssortListener onTouchAssortListener = null;

    public interface OnTouchAssortListener {
        void onTouchAssortListener(String s);
    }

    public void setOnTouchAssortListener(OnTouchAssortListener onTouchAssortListener) {
        this.onTouchAssortListener = onTouchAssortListener;
    }
}