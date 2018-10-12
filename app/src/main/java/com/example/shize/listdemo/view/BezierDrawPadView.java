package com.example.shize.listdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 贝塞尔曲线画板
 * Created by shize on 2017/2/23.
 */

public class BezierDrawPadView extends View {

    private Path mPath;
    private Paint mPaint;

    private float mX;
    private float mY;

    public BezierDrawPadView(Context context) {
        super(context);
        initData();
    }

    public BezierDrawPadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public BezierDrawPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
    }

    /**
     * 重置绘图板
     */
    public void resetPath(){
        mPath.reset();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // 重置路径
//                mPath.reset();
                mX = event.getX();
                mY = event.getY();
                mPath.moveTo(mX, mY);
                break;
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                // 一般绘制，绘制圆形时会造成很多菱角
//                mPath.lineTo(x1, y1);
                // 利用贝塞尔曲线绘制
                float cx = (x1 + mX) / 2;
                float cy = (y1 + mY) / 2;
                mPath.quadTo(mX, mY, cx, cy);
                mX = x1;
                mY = y1;
                break;
        }
        // 刷新界面
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }
}
