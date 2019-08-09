package com.example.broadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class autoActivity extends baseActivity {

    @BindView(R.id.start1)
    Button start1;
    private EditText msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        ButterKnife.bind(this);
        msg=(EditText)findViewById(R.id.msg);

        start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(autoActivity.this,MainActivity.class));
            }
        });

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
