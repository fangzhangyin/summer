package com.example.okhttp;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.resign)
    Button resign;
    @BindView(R.id.lend)
    Button lend;
    private TextView textView;
    private Button btn;
    private Message msg;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.text);
        ButterKnife.bind(this);
        resign.setOnClickListener(this);
        lend.setOnClickListener(this);
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
        }
    }
}
