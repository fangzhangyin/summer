package com.example.broadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.broadcast.baseActivity.EXIT_APP_ACTION;

public class finall extends baseActivity {

    @BindView(R.id.exit0)
    Button exit0;
    @BindView(R.id.canel)
    Button canel;
    private Timer timer;
    private long secound;

    @BindView(R.id.back)
    Button back;

    @BindView(R.id.time)
    EditText time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finall);
        ButterKnife.bind(this);
        exit0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=time.getText().toString();
                if(s!=null&&!s.equals("")){
                    secound=Long.parseLong(s);
                    secound=secound*1000;
                    //开启广播，定时关闭整个app
                    if(timer==null){
                        timer=new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                //do something
                                Intent intent = new Intent();
                                intent.setAction(EXIT_APP_ACTION);
                                sendBroadcast(intent);
                            }
                        }, secound);//延时执行
                    }
                }

            }
        });
        canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer!=null){
                    timer.cancel();
                    timer.purge();
                    timer=null;
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
