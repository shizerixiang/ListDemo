package com.example.shize.listdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.shize.listdemo.evaluator.BezierEvaluator;

/**
 * Bezier路径动画
 * Created by shize on 2017/2/24.
 */

public class BezierPathAnimView extends View implements View.OnClickListener {
    // 起始点
    private float mStartPointX;
    private float mStartPointY;
    // 结束点
    private float mEndPointX;
    private float mEndPointY;
    // 控制点
    private float mFlagPointX;
    private float mFlagPointY;
    // 移动点
    private float mMovePointX;
    private float mMovePointY;
    // 路径
    private Path mPath;
    // 画笔
    private Paint mPathPaint;
    private Paint mCirclePaint;

    private ValueAnimator mValueAnimator;

    public BezierPathAnimView(Context context) {
        super(context);
        initData();
    }

    public BezierPathAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public BezierPathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        mStartPointX = 100;
        mStartPointY = 100;

        mEndPointX = 600;
        mEndPointY = 600;

        mFlagPointX = 600;
        mFlagPointY = 200;

        mMovePointX = mStartPointX;
        mMovePointY = mStartPointY;

        mPath = new Path();
        // 创建动画
        createAnimator();

        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(4);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCirclePaint.setColor(Color.DKGRAY);

        setOnClickListener(this);
    }

    /**
     * 创建动画
     */
    private void createAnimator() {
        BezierEvaluator evaluator = new BezierEvaluator(new PointF(mFlagPointX, mFlagPointY));
        mValueAnimator = ValueAnimator.ofObject(evaluator, new PointF(mStartPointX, mStartPointY),
                new PointF(mEndPointX, mEndPointY));
        mValueAnimator.setDuration(600);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                mMovePointX = pointF.x;
                mMovePointY = pointF.y;
                invalidate();
            }
        });
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);
        mPath.quadTo(mFlagPointX, mFlagPointY, mEndPointX, mEndPointY);

        canvas.drawPath(mPath, mPathPaint);
        canvas.drawCircle(mStartPointX, mStartPointY, 20, mCirclePaint);
        canvas.drawCircle(mMovePointX, mMovePointY, 20, mCirclePaint);
        canvas.drawCircle(mEndPointX, mEndPointY, 20, mCirclePaint);
    }

    @Override
    public void onClick(View v) {
        if (!mValueAnimator.isRunning()) {
            mValueAnimator.start();
        } else {
            stopAnimator();
        }
    }

    /**
     * 停止动画
     */
    public void stopAnimator() {
        if (mValueAnimator != null && mValueAnimator.isRunning())
            mValueAnimator.cancel();
        invalidate();
    }
}
