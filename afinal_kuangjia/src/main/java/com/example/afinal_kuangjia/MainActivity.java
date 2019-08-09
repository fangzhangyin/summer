package com.example.afinal_kuangjia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

public class MainActivity extends FinalActivity {

    @ViewInject(id=R.id.btn,click = "btn")
    Button btn;
    @ViewInject(id=R.id.text)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void btn(View v){
        textView.setText("注解成功");
    }
}
