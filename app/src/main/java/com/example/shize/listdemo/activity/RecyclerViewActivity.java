package com.example.shize.listdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.shize.listdemo.adapter.MyRecyclerViewAdapter;
import com.example.shize.listdemo.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> mData;
    private MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initData();
        initView();
        initAttribute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_add:
                adapter.addData(1, "new");
                break;
            case R.id.id_menu_delete:
                adapter.deleteData(1);
                break;
            case R.id.id_menu_setting:
                break;
        }
        return true;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mData = new ArrayList<>();
        for (int i = 'a'; i <= 'z'; i++) {
            mData.add("" + (char) i);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.id_recycler_list);
    }

    /**
     * 初始化属性
     */
    private void initAttribute() {
        // 添加适配器
        adapter = new MyRecyclerViewAdapter(this, mData);
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(RecyclerViewActivity.this, "你点击了第" + position + "个item",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(RecyclerViewActivity.this, "你长按了第" + position + "个item",
                        Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        // 添加布局管理
        LinearLayoutManager linearLayout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);

//        // 添加分割线，通过样式属性listDivider获取样式
//        recyclerView.addItemDecoration(new DividerItemDecoration(
//                this, DividerItemDecoration.VERTICAL));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 切换到线性布局
     */
    public void setLinearLayout(View view) {
        LinearLayoutManager linearLayout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);
    }

    /**
     * 切换到网格布局
     */
    public void setGridLayout(View view) {
        GridLayoutManager gridLayout = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayout);
    }

    /**
     * 切换到瀑布流
     */
    public void setStaggerLayout(View view) {
        StaggeredGridLayoutManager staggeredGridLayout = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayout);
    }
}
