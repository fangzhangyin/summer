package com.example.xutils;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.btn)
    private Button btn;
    @ViewInject(R.id.text)
    private TextView textView;
    @ViewInject(R.id.web)
    private Button web;
    @ViewInject(R.id.sql)
    private Button sql;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value = {R.id.btn,R.id.web,R.id.sql},type =View.OnClickListener.class )
    private void onTestBaidu1Click(View view) {
        switch (view.getId()){
            case R.id.btn:
                textView.setText("click");
                break;
            case R.id.web:
                Intent intent=new Intent(MainActivity.this,http.class);
                startActivity(intent);
                break;
            case R.id.sql:
                Intent intent1=new Intent(MainActivity.this,usql.class);
                startActivity(intent1);
                break;
        }
    }
    @Event(value = R.id.btn,type =View.OnLongClickListener.class)
    private boolean btn(View view){
        textView.setText("long click");
        return true;
    }

}
