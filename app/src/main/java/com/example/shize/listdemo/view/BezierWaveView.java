package com.example.shize.listdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * 贝塞尔曲线演示实例
 * Created by shize on 2017/2/23.
 */

public class BezierWaveView extends View implements View.OnClickListener {

    // 波浪长度
    private int mWaveLength;

    // 记录屏幕宽高
    private int mScreeHeight;
    private int mScreeWidth;
    private int mCenterY;

    // 波浪数
    private int mWaveCount;

    private int mOffset;

    // 绘制用笔
    private Paint mPaintBezier;

    // 贝塞尔曲线路径
    private Path mPath;

    private ValueAnimator mValueAnimator;

    public BezierWaveView(Context context) {
        super(context);
        initData();
    }

    public BezierWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public BezierWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintBezier.setStrokeWidth(6);
        mPaintBezier.setColor(Color.LTGRAY);

        mWaveLength = 800;

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mScreeHeight = h;
        mScreeWidth = w;
        mCenterY = h / 2;
        // 绘制波浪个数需要加上屏幕外的波浪并四舍五入
        mWaveCount = (int) Math.round(mScreeWidth / mWaveLength + 1.5);
        setWaveAnimator();

        setOnClickListener(this);
    }

    /**
     * 设置动画
     */
    private void setWaveAnimator() {
        mValueAnimator = ValueAnimator.ofInt(0, mWaveLength);
        mValueAnimator.setDuration(1000);
        // 重复播放动画
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // 设置插值器
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制贝塞尔曲线
        mPath.reset();
        mPath.moveTo(-mWaveLength + mOffset, mCenterY);

        for (int i = 0; i < mWaveCount; i++) {
            mPath.quadTo(-mWaveLength * 3 / 4 + i * mWaveLength + mOffset, mCenterY + 60,
                    -mWaveLength / 2 + i * mWaveLength + mOffset, mCenterY);
            mPath.quadTo(-mWaveLength / 4 + i * mWaveLength + mOffset, mCenterY - 60,
                    i * mWaveLength + mOffset, mCenterY);
        }
        // 闭合图形
        mPath.lineTo(mScreeWidth, mScreeHeight);
        mPath.lineTo(0, mScreeHeight);
        mPath.close();

        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public void onClick(View v) {
        if (!mValueAnimator.isRunning()) {
            mValueAnimator.start();
        } else {
            stopAnimator();
        }
    }

    public void stopAnimator() {
        if (mValueAnimator != null && mValueAnimator.isRunning())
            mValueAnimator.cancel();
        invalidate();
    }
}
