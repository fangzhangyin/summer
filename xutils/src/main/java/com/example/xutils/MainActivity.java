package com.example.xutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.EventBase;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.btn)
    Button btn;
    @ViewInject(R.id.text)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

    }
    @OnClick(R.id.btn)
    public void btn(View v){
        textView.setText("xutils");
    }

}
