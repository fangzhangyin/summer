package com.example.servicer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class playmusic extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.play)
    Button play;
    @BindView(R.id.stop)
    Button stop;
    @BindView(R.id.pause)
    Button pause;
    @BindView(R.id.exit)
    Button exit;
    private MediaPlayer mediaPlayer;
    private boolean isplay=false;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmusic);
        ButterKnife.bind(playmusic.this);
        intent=new Intent(this,musicService.class);
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        pause.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                intent.putExtra("action","play");
                startService(intent);
                break;
            case R.id.stop:
                intent.putExtra("action","stop");
                startService(intent);
                break;
            case R.id.pause:
                intent.putExtra("action","pause");
                startService(intent);
                break;
            case R.id.exit:
                stopService(intent);
                finish();
                break;
        }
    }




}
