package com.example.broadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.broadcast.baseActivity.EXIT_APP_ACTION;

public class finall extends baseActivity {

    @BindView(R.id.exit0)
    Button exit0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finall);
        ButterKnife.bind(this);
        exit0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //开启广播，定时关闭整个app
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        //do something
                        Intent intent = new Intent();
                        intent.setAction(EXIT_APP_ACTION);
                        sendBroadcast(intent);
                    }
                }, 2000);//延时1s执行


            }
        });
    }
}
