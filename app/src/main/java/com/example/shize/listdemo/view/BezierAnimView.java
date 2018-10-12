package com.example.shize.listdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * 贝塞尔曲线演示实例
 * Created by shize on 2017/2/23.
 */

public class BezierAnimView extends View implements View.OnClickListener {

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

    private ValueAnimator mValueAnimator;

    public BezierAnimView(Context context) {
        super(context);
        initData();
    }

    public BezierAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public BezierAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 4 - 200;

        mEndPointX = w * 3 / 4;
        mEndPointY = mStartPointY;

        mFlagPointOneX = mStartPointX;
        mFlagPointOneY = mStartPointY;

        mFlagPointTwoX = mEndPointX;
        mFlagPointTwoY = mStartPointY;

        // 动画变换属性
        mValueAnimator = ValueAnimator.ofFloat(mStartPointY, h);
        // 动画插值器
        mValueAnimator.setInterpolator(new BounceInterpolator());
        // 动画时间
        mValueAnimator.setDuration(1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFlagPointOneY = (float) animation.getAnimatedValue();
                mFlagPointTwoY = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        setOnClickListener(this);
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
        canvas.drawPoint(mFlagPointTwoX, mFlagPointTwoY, mPaintPoint);
        canvas.drawText("控制点2", mFlagPointTwoX, mFlagPointTwoY, mPaintText);
        // 绘制点间的线
        canvas.drawLine(mStartPointX, mStartPointY, mFlagPointOneX, mFlagPointOneY, mPaintLine);
        canvas.drawLine(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY, mPaintLine);
        canvas.drawLine(mEndPointX, mEndPointY, mFlagPointTwoX, mFlagPointTwoY, mPaintLine);

        // 绘制贝塞尔曲线
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);
        mPath.cubicTo(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX,
                mFlagPointTwoY, mEndPointX, mEndPointY);
        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public void onClick(View v) {
        mValueAnimator.start();
    }
}
