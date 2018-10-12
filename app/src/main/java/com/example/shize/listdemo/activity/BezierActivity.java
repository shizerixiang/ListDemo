package com.example.shize.listdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.shize.listdemo.R;
import com.example.shize.listdemo.view.BezierAnimView;
import com.example.shize.listdemo.view.BezierDrawPadView;
import com.example.shize.listdemo.view.BezierPathAnimView;
import com.example.shize.listdemo.view.BezierTestView;
import com.example.shize.listdemo.view.BezierWaveView;

public class BezierActivity extends AppCompatActivity {

    private BezierDrawPadView padView;
    private BezierTestView testView;
    private BezierAnimView animView;
    private BezierWaveView waveView;
    private BezierPathAnimView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);

        initView();
    }

    private void initView() {
        padView = (BezierDrawPadView) findViewById(R.id.id_bezier_pad);
        testView = (BezierTestView) findViewById(R.id.id_bezier_test);
        animView = (BezierAnimView) findViewById(R.id.id_bezier_anim);
        waveView = (BezierWaveView) findViewById(R.id.id_bezier_wave);
        pathView = (BezierPathAnimView) findViewById(R.id.id_bezier_path);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bezier_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_menu_reset:
                padView.resetPath();
                waveView.stopAnimator();
                break;
            case R.id.id_menu_three:
                testView.changeBezier();
                break;
            case R.id.id_menu_test:
                testView.setVisibility(View.VISIBLE);
                padView.setVisibility(View.GONE);
                animView.setVisibility(View.GONE);
                waveView.setVisibility(View.GONE);
                pathView.setVisibility(View.GONE);
                break;
            case R.id.id_menu_pad:
                testView.setVisibility(View.GONE);
                padView.setVisibility(View.VISIBLE);
                animView.setVisibility(View.GONE);
                waveView.setVisibility(View.GONE);
                pathView.setVisibility(View.GONE);
                break;
            case R.id.id_menu_anim:
                testView.setVisibility(View.GONE);
                padView.setVisibility(View.GONE);
                animView.setVisibility(View.VISIBLE);
                waveView.setVisibility(View.GONE);
                pathView.setVisibility(View.GONE);
                break;
            case R.id.id_menu_wave:
                testView.setVisibility(View.GONE);
                padView.setVisibility(View.GONE);
                animView.setVisibility(View.GONE);
                waveView.setVisibility(View.VISIBLE);
                pathView.setVisibility(View.GONE);
                break;
            case R.id.id_menu_path:
                testView.setVisibility(View.GONE);
                padView.setVisibility(View.GONE);
                animView.setVisibility(View.GONE);
                waveView.setVisibility(View.GONE);
                pathView.setVisibility(View.VISIBLE);
                break;
        }
        return false;
    }
}
