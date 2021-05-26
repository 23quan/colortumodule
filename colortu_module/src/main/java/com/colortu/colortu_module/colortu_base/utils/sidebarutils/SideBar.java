package com.colortu.colortu_module.colortu_base.utils.sidebarutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.colortu.colortu_module.R;

@SuppressLint("AppCompatCustomView")
public class SideBar extends Button {

    private void initView() {
        dialogSidebarContent = (TextView) findViewById(R.id.dialog_sidebar_content);
    }

    public interface OnTouchAssortListener {
        void onTouchAssortListener(String s);
    }

    // 分类
    private static final String[] ASSORT_TEXT = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"};

    private Paint mPaint = new Paint();
    private int mSelectIndex = -1;
    private OnTouchAssortListener mListener = null;
    private Activity mAttachActivity;
    PopupWindow mPopupWindow = null;
    View layoutView;
    TextView dialogSidebarContent;

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mAttachActivity = (Activity) context;
        init(context);
    }

    private void init(Context context) {
        layoutView = LayoutInflater.from(context).inflate(R.layout.dialog_base_sidebar, null);
        dialogSidebarContent = (TextView) layoutView.findViewById(R.id.dialog_sidebar_content);
    }

    public void setOnTouchAssortListener(OnTouchAssortListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int nHeight = getHeight();
        int hWidth = getWidth();
        int nAssortCount = ASSORT_TEXT.length;
        int nInterval = nHeight / nAssortCount;

        for (int i = 0; i < nAssortCount; i++) {
            //抗锯齿
            mPaint.setAntiAlias(true);
            //默认粗体
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setColor(Color.parseColor("#FFFFFF"));
            if (i == mSelectIndex) {
                // 被选择的字母改变颜色和粗体
                mPaint.setColor(Color.parseColor("#000000"));
                mPaint.setFakeBoldText(true);
                mPaint.setTextSize(20);
            }
            //计算字母的X坐标
            float xPos = hWidth / 2 - mPaint.measureText(ASSORT_TEXT[i]) / 2;
            //计算字母的Y坐标
            float yPos = nInterval * i + nInterval;
            canvas.drawText(ASSORT_TEXT[i], xPos, yPos, mPaint);
            mPaint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //判断是哪一个字母被点击了
        int nIndex = (int) (event.getY() / getHeight() * ASSORT_TEXT.length);
        if (nIndex >= 0 && nIndex < ASSORT_TEXT.length) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    //如果滑动改变
                    if (mSelectIndex != nIndex) {
                        mSelectIndex = nIndex;
                        showCharacter(ASSORT_TEXT[mSelectIndex]);
                        if (mListener != null) {
                            mListener.onTouchAssortListener(ASSORT_TEXT[mSelectIndex]);
                        }
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    mSelectIndex = nIndex;
                    showCharacter(ASSORT_TEXT[mSelectIndex]);
                    if (mListener != null) {
                        mListener.onTouchAssortListener(ASSORT_TEXT[mSelectIndex]);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    disShowCharacter();
                    mSelectIndex = -1;
                    break;
            }
        } else {
            mSelectIndex = -1;
            disShowCharacter();
        }
        invalidate();
        return true;
    }

    private void disShowCharacter() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    /**
     * 显示弹出的字符
     *
     * @param string
     */
    private void showCharacter(String string) {
        if (mPopupWindow != null) {
            dialogSidebarContent.setText(string);
        } else {
            mPopupWindow = new PopupWindow(layoutView, 120, 120, false);
            mPopupWindow.showAtLocation(mAttachActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
        dialogSidebarContent.setText(string);
    }
}