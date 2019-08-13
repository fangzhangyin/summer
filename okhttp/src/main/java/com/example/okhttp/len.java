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

import java.io.File;
import java.io.IOException;

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

public class len extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.lname)
    EditText lname;
    @BindView(R.id.lpass)
    EditText lpass;
    @BindView(R.id.lendin)
    Button lendin;
    @BindView(R.id.lres)
    TextView lres;

    private Handler handler;
    private Message message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_len);
        ButterKnife.bind(this);
        lendin.setOnClickListener(this);
        handler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1&&msg.arg1==200){
                    String s=msg.obj.toString();
                    lres.setText(s);
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.lendin){
            String name=lname.getText().toString().trim();
            String pass=lpass.getText().toString().trim();
            lend(name,pass);
        }
    }

    public void lend(String name,String pass){
        final MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        //POST方式提交表单
        final RequestBody requestBody = new FormBody.Builder()
                .add("name", name).add("password",pass)
                .build();
        Request request = new Request.Builder()
                .url("http://fzytest.j2eeall.com/android/lend")
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
                System.out.println(s);
                if(s.equals("success")){
                    s="登陆成功";
                }else{
                    s="用户名或者账号不正确";
                }
                message=new Message();
                message.what=1;
                message.obj=s;
                message.arg1=response.code();
                handler.sendMessage(message);
            }
        });
    }
}
