package com.example.shize.listdemo.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.example.shize.listdemo.R;
import com.example.shize.listdemo.drawable.MyRippleDrawable;

/**
 * 自定义涟漪按钮
 * Created by shize on 2017/2/21.
 */

public class RippleButton extends Button {

    private MyRippleDrawable mRippleDrawable;

    public RippleButton(Context context) {
        super(context);
        initData();
    }

    public RippleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public RippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mRippleDrawable = new MyRippleDrawable();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mRippleDrawable.onTouch(event);
        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制自己的drawable
        mRippleDrawable.draw(canvas);
        super.onDraw(canvas);
    }
}
