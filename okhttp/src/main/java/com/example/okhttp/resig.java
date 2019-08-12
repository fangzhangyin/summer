package com.example.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class resig extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.rname)
    EditText rname;
    @BindView(R.id.rpassword)
    EditText rpassword;
    @BindView(R.id.res)
    Button res;
    @BindView(R.id.result)
    TextView result;

    private Handler handler;
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resig);
        ButterKnife.bind(this);
        res.setOnClickListener(this);
        handler=new Handler(Looper.getMainLooper()){
            public void handleMessage(android.os.Message msg) {
                switch (msg.what){
                    case 1:
                        String str=  msg.obj.toString();
                        result.setText(str);
                        //注册成功延时2s返回
                        if(msg.arg1==200){
                            Timer timer=new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            },2000);
                        }
                        break;
                }
            };
        };
    }


    public void re(String name,String pass){
        final MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        //POST方式提交表单
        RequestBody requestBody = new FormBody.Builder()
                .add("name", name).add("password",pass)
                .build();
        Request request = new Request.Builder()
                .url("http://fzytest.j2eeall.com/android/resign")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("", "onFailure: " + e.getMessage());
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("", response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d("", headers.name(i) + ":" + headers.value(i));
                }
                String s=response.body().string();
                if(s.equals("success")){
                    s="注册成功";
                }else{
                    s="注册失败";
                }
                message=new Message();
                message.what=1;
                message.obj=s;
                message.arg1=response.code();
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.res:
                String name=rname.getText().toString().trim();
                String pass=rpassword.getText().toString().trim();
                re(name,pass);
                break;
        }
    }
}
