package com.example.shize.listdemo.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;

import static android.content.ContentValues.TAG;

/**
 * 重写一个drawable
 * Created by shize on 2017/2/22.
 */

public class MyRippleDrawable extends Drawable {

    // 透明度
    private int mAlpha;
    // 涟漪颜色
    private int mRippleColor;
    // 画笔
    private Paint mPaint;

    // 绘制圆的参数
    private float mRipplePointX, mRipplePointY, mRippleRadius;

    private Bitmap mBitmap;

    public MyRippleDrawable(){
        this(null);
    }

    public MyRippleDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
        mAlpha = 255;
        mRippleColor = 0;
        mRippleRadius = 0;
        // 抗锯齿画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 开启抗锯齿
        mPaint.setAntiAlias(true);
        // 开启防抖动
        mPaint.setDither(true);
        // 设置涟漪颜色
        setRippleColor(0x44444444);
//        setColorFilter(new LightingColorFilter(0xffeeeeee, 0xff555555));
    }

    /**
     * 设置涟漪颜色
     */
    public void setRippleColor(int color) {
        // 不建议直接设置颜色

        mRippleColor = color;
        onColorOrAlphaChange();
    }

    /**
     * 点击事件
     */
    public void onTouch(MotionEvent event){
        Log.i(TAG, "onTouch: "+event.getAction());
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mRipplePointX = event.getX();
                mRipplePointY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mRippleRadius += 20;
                break;
            case MotionEvent.ACTION_UP:
                mRippleRadius = 0;
                break;
            case MotionEvent.ACTION_CANCEL:
                mRippleRadius += 100;
                break;
            case MotionEvent.ACTION_OUTSIDE:
                mRippleRadius = 0;
                break;
        }
    }

    /**
     * 改变颜色或透明度时调用的方法
     */
    private void onColorOrAlphaChange() {
        mPaint.setColor(mRippleColor);

        if (mAlpha != 255) {
            // 得到颜色本身的透明度
            int pAlpha = mPaint.getAlpha();
            // 获取真实透明度
            int realAlpha = (int) (pAlpha * (mAlpha / 255f));

            mPaint.setAlpha(realAlpha);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // 绘制一个背景
//        canvas.drawColor(0x401565c0);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        }

        // 绘制一个圆
        canvas.drawCircle(mRipplePointX, mRipplePointY, mRippleRadius, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        // 透明度
        mAlpha = alpha;
    }

    @Override
    public int getAlpha() {
        return mAlpha;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // 颜色滤镜
        if (mPaint.getColorFilter() != colorFilter) {
            mPaint.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
