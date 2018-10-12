package com.example.shize.listdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.shize.listdemo.R;

import java.lang.ref.WeakReference;

/**
 * handler使用实例及注意点
 * Created by shize on 2017/2/15.
 */

public class HandlerActivity extends AppCompatActivity {
    public TextView txtDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        txtDownload = (TextView) findViewById(R.id.id_handler_textView);
    }
    //若如下所示进行通信将无法在消息接收之前关闭activity，因为activity被隐式引用，
    // 只有等消息结束才能关闭一直占用内存，则最后可能导致内存泄露
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 100:
//                    txtDownload.setText("下载完成！！！");
//                    break;
//            }
//        }
//    };

    private Handler handler = new HandlerActivity.MyHandler(this);

    public void downloading(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(100);
            }
        }).start();
    }

    private static class MyHandler extends Handler{
        // 对MainActivity的弱引用，可以使得MainActivity在未关闭handler的情况下关闭activity
        private WeakReference<HandlerActivity> weakReference;

        private MyHandler(HandlerActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerActivity activity = weakReference.get();
            if (activity != null) {
                // 需要处理的事件
                activity.txtDownload.setText("下载完成！！！");
            }
        }
    }
}