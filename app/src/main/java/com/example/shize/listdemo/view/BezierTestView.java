package com.example.shize.listdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 贝塞尔曲线演示实例
 * Created by shize on 2017/2/23.
 */

public class BezierTestView extends View {

    // 屏幕中点的坐标
    private float mStartPointX;
    private float mStartPointY;
    private float mEndPointX;
    private float mEndPointY;
    private float mFlagPointOneX;
    private float mFlagPointOneY;
    private float mFlagPointTwoX;
    private float mFlagPointTwoY;

    // 绘制用笔
    private Paint mPaintLine;
    private Paint mPaintBezier;
    private Paint mPaintPoint;
    private Paint mPaintText;

    // 贝塞尔曲线路径
    private Path mPath;

    private boolean isThreeBezier;
    private boolean isChangeTwo;

    public BezierTestView(Context context) {
        super(context);
        initData();
    }

    public BezierTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public BezierTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setStrokeWidth(3);

        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(8);

        mPaintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPoint.setStyle(Paint.Style.STROKE);
        mPaintPoint.setStrokeWidth(12);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPoint.setStyle(Paint.Style.STROKE);
        mPaintText.setTextSize(30);

        mPath = new Path();
    }

    public void changeBezier() {
        isThreeBezier = !isThreeBezier;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 4 - 200;

        mEndPointX = w * 3 / 4;
        mEndPointY = h / 4 - 200;

        mFlagPointOneX = w / 2;
        mFlagPointOneY = h / 2 - 300;

        mFlagPointTwoX = w / 2 + 100;
        mFlagPointTwoY = h / 2 - 300 + 100;

        isThreeBezier = false;
        isChangeTwo = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制点
        canvas.drawPoint(mStartPointX, mStartPointY, mPaintPoint);
        canvas.drawText("起点", mStartPointX, mStartPointY, mPaintText);
        canvas.drawPoint(mEndPointX, mEndPointY, mPaintPoint);
        canvas.drawText("终点", mEndPointX, mEndPointY, mPaintText);
        canvas.drawPoint(mFlagPointOneX, mFlagPointOneY, mPaintPoint);
        canvas.drawText("控制点1", mFlagPointOneX, mFlagPointOneY, mPaintText);
        // 绘制点间的线
        canvas.drawLine(mStartPointX, mStartPointY, mFlagPointOneX, mFlagPointOneY, mPaintLine);

        // 绘制贝塞尔曲线
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);

        // 判断是否为三阶Bezier曲线
        if (isThreeBezier) {
            canvas.drawPoint(mFlagPointTwoX, mFlagPointTwoY, mPaintPoint);
            canvas.drawText("控制点2", mFlagPointTwoX, mFlagPointTwoY, mPaintText);

            canvas.drawLine(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY, mPaintLine);
            canvas.drawLine(mEndPointX, mEndPointY, mFlagPointTwoX, mFlagPointTwoY, mPaintLine);

            mPath.cubicTo(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX,
                    mFlagPointTwoY, mEndPointX, mEndPointY);
        } else {
            canvas.drawLine(mEndPointX, mEndPointY, mFlagPointOneX, mFlagPointOneY, mPaintLine);
            mPath.quadTo(mFlagPointOneX, mFlagPointOneY, mEndPointX, mEndPointY);
        }
        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                onCheckHand(event);
                onFlagPointChange(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onFlagPointChange(event);
                break;
            case MotionEvent.ACTION_UP:
                isChangeTwo = false;
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 检测手与哪个点较近
     */
    private void onCheckHand(MotionEvent event) {
        float flagOne = Math.abs(event.getX() - mFlagPointOneX) +
                Math.abs(event.getY() - mFlagPointOneY);
        float flagTwo = Math.abs(event.getX() - mFlagPointTwoX) +
                Math.abs(event.getY() - mFlagPointTwoY);
        isChangeTwo = flagOne > flagTwo;
    }

    /**
     * 点的坐标发生改变
     */
    private void onFlagPointChange(MotionEvent event) {
        if (isThreeBezier && isChangeTwo) {
            mFlagPointTwoX = event.getX();
            mFlagPointTwoY = event.getY();
        } else {
            mFlagPointOneX = event.getX();
            mFlagPointOneY = event.getY();
        }
    }
}
