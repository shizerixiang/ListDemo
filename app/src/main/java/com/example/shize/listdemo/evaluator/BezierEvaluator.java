package com.example.shize.listdemo.evaluator;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import com.example.shize.listdemo.util.BezierUtil;

/**
 * 计算
 * Created by shize on 2017/2/24.
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF mFlagPoint1;
    private PointF mFlagPoint2;

    public BezierEvaluator(PointF mFlagPoint1) {
        this.mFlagPoint1 = mFlagPoint1;
    }

    public BezierEvaluator(PointF mFlagPoint1, PointF mFlagPoint2) {
        this.mFlagPoint1 = mFlagPoint1;
        this.mFlagPoint2 = mFlagPoint2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

        if (mFlagPoint2 != null) {
            return BezierUtil.calculateBezierPointCubic(fraction, startValue,
                    mFlagPoint1, mFlagPoint2, endValue);
        } else if (mFlagPoint1 != null) {
            return BezierUtil.calculateBezierPointQuadratic(fraction, startValue,
                    mFlagPoint1, endValue);
        }
        return null;
    }
}
