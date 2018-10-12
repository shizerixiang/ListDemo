package com.example.shize.listdemo.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.shize.listdemo.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 危险权限申请实例
 * Created by shize on 2017/2/15.os-
 */

public class PermissionActivity extends AppCompatActivity {

    // 权限识别代号
    final private static int REQUEST_CODE_WRITE_PERMISSIONS = 141;
    final private static String TAG = "PermissionActivity";
    private EditText etContent;
    private EditText etExpandName;
    private ListView pathList;
    private List<HashMap<String, String>> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        etContent = (EditText) findViewById(R.id.id_permission_content);
        etExpandName = (EditText) findViewById(R.id.id_permission_expand_name);
        pathList = (ListView) findViewById(R.id.id_permission_list_result);
        files = new ArrayList<>();

        // M之后申请危险权限
        grantedPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_CODE_WRITE_PERMISSIONS);

    }

//    /**
//     * M之后申请危险权限
//     */
//    private void getPermission() {
//        // 获取权限申请状态，checkSelfPermission返回权限在指定Activity内的状态
//        int checkSelfPermission = ContextCompat.checkSelfPermission(PermissionActivity.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        // 若未申请，则进行申请操作，拥有权限返回PackageManager.PERMISSION_GRANTED，
//        // 否则返回PackageManager.PERMISSION_DENIED
//        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
//            // 检测版本是否为M版本之前，shouldShowRequestPermissionRationale在M版本之前都返回false
//            if (ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                // M版本的处理代码
//                ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSIONS);
//            }
//        }
//    }

    /**
     * 申请权限
     */
    private void grantedPermission(String[] permission, int permissionCode) {
        // 判断是否已经被授权读取文件
        if (ContextCompat.checkSelfPermission(PermissionActivity.this,
                permission[0]) != PackageManager.PERMISSION_GRANTED) {
            // 未被授权，是否被拒绝过，判断并给出提示
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permission[0])) {
                Log.i(TAG, "grantedPermission: 拒绝！！！");
            }
            // 申请授权,该方法是异步的，第二个参数可同时申请多个
            ActivityCompat.requestPermissions(this,
                    permission, permissionCode);
        } else {
            // 被授权，执行程序
            Toast.makeText(PermissionActivity.this, "已被授权！！！",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理权限申请回调
     *
     * @param requestCode  申请代号
     * @param permissions  申请权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE_PERMISSIONS:
                // 如果申请被取消，结果数组为空，所以要进行判断结果数组是否为空
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被授予，做你需要做的
                    Toast.makeText(PermissionActivity.this, "已被授权！！！",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // 权限未被授予
                    Log.i(TAG, "onRequestPermissionsResult: 没有权限！！！");
                    Toast.makeText(PermissionActivity.this, "未被授权！！！",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 向私有文件写入数据
     */
    public void onWriteButtonClick(View view) {
        if (etContent.getText() != null) {
            String str = etContent.getText().toString();
            putByteToFile(str);
        } else {
            Toast.makeText(PermissionActivity.this, "请输入需要写入的内容！！！",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 以字节码形式向文件输入信息
     *
     * @param str 需要输入的字符串
     */
    private void putByteToFile(String str) {
        try {
            OutputStream out = openFileOutput("filePermission.txt", Context.MODE_PRIVATE);
            byte[] bytes = str.getBytes();
            out.write(bytes, 0, bytes.length);
            out.close();
            Toast.makeText(PermissionActivity.this, "写入成功！！！", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从私有文件读取数据
     */
    public void onReadButtonClick(View view) {
        String fileName = "filePermission.txt";
        String str = String.valueOf(getByteFromFile(fileName));
        Toast.makeText(PermissionActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 从私有文件中获取字节码
     *
     * @param fileName 文件名
     * @return 字符串
     */
    private StringBuffer getByteFromFile(String fileName) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            InputStream in = openFileInput(fileName);
            // 规定内存容量
            byte[] bytes = new byte[1024];
            int len;
            while ((len = in.read(bytes)) != -1) {
                stringBuffer.append(new String(bytes, 0, len));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    /**
     * 搜索手机中的文件
     */
    public void onSearchFileButtonClick(View view) {
        // 获取用户输入的后缀名
        String expandName = etExpandName.getText().toString();
        files.clear();
        // 指定搜索文件范围
        File file = new File(Environment.getExternalStorageDirectory() + "/");
        // 获取文件集合
        searchFiles(file, new String[]{expandName});
        Log.i(TAG, "onSearchFileButtonClick: " + files.size());
        SimpleAdapter adapter = new SimpleAdapter(this, files, R.layout.permission_list_item,
                new String[]{"path"}, new int[]{R.id.id_permission_item_path});
        pathList.setAdapter(adapter);
    }

    /**
     * 搜索文件
     * @param file        搜索文件范围
     * @param expandNames 文件扩展名
     */
    private void searchFiles(File file, String[] expandNames) {

        if (file != null) {
            Log.i(TAG, "searchFiles: 路径文件夹不为空！！！！！！！");
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    for (File subFile : subFiles) {
                        searchFiles(subFile, expandNames);
                    }
                }
            } else {
                String filePath = file.getAbsolutePath();
                Log.i(TAG, "searchFiles: 不为文件夹！！！！！！");
                for (String en : expandNames) {
                    if (filePath.endsWith(en)) {
                        Log.i(TAG, "searchFiles: 找到了相符合的文件！！！！！");
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("path", filePath);
                        files.add(hashMap);
                        break;
                    }
                }
            }
        }
    }
}
