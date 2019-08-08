package com.example.servicer;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Button btn1,btn2;
    private Button btnBind;
    private Button btnUnBind;
    private Button btnGetDatas;
    private ServiceConnection conn;
    private localservice mService;
    private TextView text;
    private TextView text2;
    private Intent it;
    private Button music;
    private Button phone;
    protected static final String TAG = "wzj";

    @BindView(R.id.autoend)
    Button autoend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        it=new Intent(this,myservice.class);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        text=findViewById(R.id.text);
        text2=findViewById(R.id.text2);
        music=findViewById(R.id.music);
        phone=findViewById(R.id.phone);
        btnBind = (Button) findViewById(R.id.BindService);
        btnUnBind = (Button) findViewById(R.id.unBindService);
        btnGetDatas = (Button) findViewById(R.id.getServiceDatas);


        final Intent intent=new Intent(MainActivity.this,localservice.class);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(it);
                text.setText("服务已开启");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(it);
                text.setText("服务已关闭");
            }
        });

        // 开启绑定
        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2.setText("绑定调用：bindService");
                Log.d(TAG, "绑定调用：bindService");
                //调用绑定方法
                bindService(intent, conn, Service.BIND_AUTO_CREATE);
            }
        });
        // 解除绑定
        btnUnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2.setText("解除绑定调用：unbindService");
                Log.d(TAG, "解除绑定调用：unbindService");
                // 解除绑定
                if(mService!=null) {
                    mService = null;
                    unbindService(conn);
                }
            }
        });

        // 获取数据
        btnGetDatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mService != null) {
                    // 通过绑定服务传递的Binder对象，获取Service暴露出来的数据
                    text2.setText("从服务端获取数据：" + mService.getCount());
                    Log.d(TAG, "从服务端获取数据：" + mService.getCount());
                } else {
                    text2.setText("还没绑定呢，先绑定,无法从服务端获取数据");
                    Log.d(TAG, "还没绑定呢，先绑定,无法从服务端获取数据");
                }
            }
        });

        conn = new ServiceConnection() {
            /**
             * 与服务器端交互的接口方法 绑定服务的时候被回调，在这个方法获取绑定Service传递过来的IBinder对象，
             * 通过这个IBinder对象，实现宿主和Service的交互。
             */
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "绑定成功调用：onServiceConnected");
                // 获取Binder
                localservice.LocalBinder binder = (localservice.LocalBinder) service;
                mService = binder.getService();
            }
            /**
             * 当取消绑定的时候被回调。但正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
             * 例如内存的资源不足时这个方法才被自动调用。
             */
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService=null;
            }
        };

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,playmusic.class);
                startActivity(intent1);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,phonetest.class);
                startActivity(intent1);
            }
        });

        autoend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,auto.class);
                startActivity(intent1);
            }
        });
    }
}
