package com.example.shize.listdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.shize.listdemo.R;
import com.example.shize.listdemo.view.RippleButton;

/**
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 打开handler实例
     */
    public void openHandlerTest(View view) {
        startActivity(new Intent(MainActivity.this, HandlerActivity.class));
    }

    /**
     * 打开危险权限测试实例
     */
    public void openPermissionTest(View view) {
        startActivity(new Intent(MainActivity.this, PermissionActivity.class));
    }

    /**
     * 打开分页加载测试实例
     */
    public void openPagingTest(View view) {
        startActivity(new Intent(MainActivity.this, PagingActivity.class));
    }


    /**
     * 打开RecyclerView测试实例
     */
    public void openRecyclerTest(View view) {
        startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
    }

    /**
     * 打开Ripple测试实例
     */
    public void openRippleTest(View view) {
        startActivity(new Intent(MainActivity.this, RippleActivity.class));
    }

    /**
     * 打开drag拖动测试实例
     */
    public void openDragTest(View view) {
        startActivity(new Intent(MainActivity.this, DragActivity.class));
    }

    /**
     * 打开Bezier曲线测试实例
     */
    public void openBezierTest(View view) {
        startActivity(new Intent(MainActivity.this, BezierActivity.class));
    }
}
