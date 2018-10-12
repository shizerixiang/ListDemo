package com.example.shize.listdemo.util;

import android.graphics.PointF;

/**
 * Bezier计算类
 * Created by shize on 2017/2/24.
 */

public class BezierUtil {

    /**
     * 计算二阶Bezier点的坐标
     * B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1]
     *
     * @param ratio      曲线长度比例
     * @param startPoint 起始点
     * @param flagPoint  控制点
     * @param endPoint   终止点
     * @return 对应位置点
     */
    public static PointF calculateBezierPointQuadratic(float ratio, PointF startPoint,
                                                       PointF flagPoint, PointF endPoint) {
        PointF pointF = new PointF();
        float temp = 1 - ratio;
        pointF.x = temp * temp * startPoint.x + 2 * ratio * temp * flagPoint.x +
                ratio * ratio * endPoint.x;
        pointF.y = temp * temp * startPoint.y + 2 * ratio * temp * flagPoint.y +
                ratio * ratio * endPoint.y;
        return pointF;
    }


    /**
     * 计算三阶Bezier点的坐标
     * B(t) = P0 * (1-t)^3 + 3 * P1 * t * (1-t)^2 + 3 * P2 * t^2 * (1-t) + P3 * t^3, t ∈ [0,1]
     *
     * @param ratio      曲线长度比例
     * @param startPoint 起始点
     * @param flagPoint1 控制点1
     * @param flagPoint2 控制点2
     * @param endPoint   终止点
     * @return 对应位置点
     */
    public static PointF calculateBezierPointCubic(float ratio, PointF startPoint,
                                                   PointF flagPoint1, PointF flagPoint2,
                                                   PointF endPoint) {
        PointF pointF = new PointF();
        float temp = 1 - ratio;
        pointF.x = startPoint.x * temp * temp * temp + 3 * flagPoint1.x * ratio * temp * temp +
                3 * flagPoint2.x * ratio * ratio * temp + endPoint.x * ratio * ratio * ratio;
        pointF.y = startPoint.y * temp * temp * temp + 3 * flagPoint1.y * ratio * temp * temp +
                3 * flagPoint2.y * ratio * ratio * temp + endPoint.y * ratio * ratio * ratio;
        return pointF;
    }

}
