package com.example.servicer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class auto extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.auto1)
    Button auto1;
    @BindView(R.id.auto2)
    Button auto2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        ButterKnife.bind(this);
        auto1.setOnClickListener(this);
        auto2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.auto1:
                startService(new Intent(this,phoneService.class));
                break;
            case R.id.auto2:
                stopService(new Intent(this,phoneService.class));
                break;
        }
    }
}
