package com.example.shize.listdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.example.shize.listdemo.R;
import com.example.shize.listdemo.view.PagesListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PagingActivity extends AppCompatActivity {

    private PagesListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging);

        listView = (PagesListView) findViewById(R.id.id_paging_list);
        initListData();
    }

    /**
     * 加载list数据
     */
    private void initListData() {
        final List<HashMap<String, String>> data = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("path", "这是路径！！！");
            data.add(hashMap);
        }
        final SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.permission_list_item,
                new String[]{"path"}, new int[]{R.id.id_permission_item_path});
        listView.setAdapter(adapter);
        listView.setOnLoadData(new PagesListView.OnLoadData() {
            @Override
            public void onLoad() {
                for (int i = 0; i < 5; i++){
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("path", "更多路径！！！");
                    data.add(hashMap);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
