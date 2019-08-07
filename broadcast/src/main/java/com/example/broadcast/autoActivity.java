package com.example.broadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class autoActivity extends AppCompatActivity {

    private EditText msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        msg=(EditText)findViewById(R.id.msg);

    }
    public void send(View view){
        String m=msg.getText().toString();
        Intent intent=new Intent();
        intent.setAction("msg");
        intent.putExtra("mm",m);
        //发送广播
        sendBroadcast(intent);
    }
}
