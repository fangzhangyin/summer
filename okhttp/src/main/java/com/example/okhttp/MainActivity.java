package com.example.okhttp;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.resign)
    Button resign;
    @BindView(R.id.lend)
    Button lend;
    @BindView(R.id.chosefile)
    Button chosefile;
    @BindView(R.id.up)
    Button up;
    private TextView textView;
    private Button btn;
    private Message msg;
    private Handler handler;
    private String fname,fpath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.text);
        ButterKnife.bind(this);
        resign.setOnClickListener(this);
        lend.setOnClickListener(this);
        chosefile.setOnClickListener(this);
        up.setOnClickListener(this);
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatasync();
            }
        });
        handler = new Handler(Looper.getMainLooper())
        {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what){
                    case 1:
                        String str=  msg.obj.toString();
                        textView.setText(str);
                        break;
                }
            };
        };

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==0){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(MainActivity.this,"定位服务已开启",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,"请先开启存储访问",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    public void getDatasync(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url("http://fzytest.j2eeall.com/android/test")//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        Log.d("kwwl","response.code()=="+response.code());
                        Log.d("kwwl","response.message()=="+response.message());
                        Log.d("kwwl","res=="+response.body().string());
                       // textView.setText(response.message());
                        System.out.println(response.code());
                        System.out.println(response.body());
                        // 此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        msg=new Message();
                        msg.what=1;
                        msg.obj=response.code();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resign:
                Intent intent=new Intent(MainActivity.this,resig.class);
                startActivity(intent);
                break;
            case R.id.lend:
                Intent intent1=new Intent(MainActivity.this,len.class);
                startActivity(intent1);
                break;
            case R.id.chosefile:
                //选择文件
                Intent inten = new Intent(Intent.ACTION_GET_CONTENT);
                inten.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                inten.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(inten,1);
                break;
            case R.id.up:
                doup(fname,fpath);
                break;
        }
    }

    private void doup(String fname, String fpath) {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");

        RequestBody requestBody = new FormBody.Builder()
                .add("name", fname).add("fpath",fpath)
                .build();
        OkHttpClient okHttpClient=new OkHttpClient();
        File file=new File(fpath);
        final Request request=new Request.Builder()
                .url("http://fzytest.j2eeall.com/android/UploadServlet")
                .post(RequestBody.create(mediaType,file))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res=response.body().string();
                msg=new Message();
                msg.what=1;
                msg.obj=res;
                handler.sendMessage(msg);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    String path = getPath(this, uri);
                    if (path != null) {
                        File file = new File(path);
                        if (file.exists()) {
                            fpath = file.toString();
                            fname = file.getName();
                        }
                    }
                }
            }
        }
    }

    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
//                Log.i(TAG,"isExternalStorageDocument***"+uri.toString());
//                Log.i(TAG,"docId***"+docId);
//                以下是打印示例：
//                isExternalStorageDocument***content://com.android.externalstorage.documents/document/primary%3ATset%2FROC2018421103253.wav
//                docId***primary:Test/ROC2018421103253.wav
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
//                Log.i(TAG,"isDownloadsDocument***"+uri.toString());
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
//                Log.i(TAG,"isMediaDocument***"+uri.toString());
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"content***"+uri.toString());
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"file***"+uri.toString());
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
