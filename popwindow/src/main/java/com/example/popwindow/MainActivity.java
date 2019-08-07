package com.example.popwindow;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout layout;
    private Button btn;
    private boolean isFold=true; // 判断是否显示
    private PopupWindow taxWindow; // 弹出框
    private TextView tv=null; // 遮罩层

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout=(LinearLayout) findViewById(R.id.layout);
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isFold){
                    isFold=false;
       showTaxDetail(v);
                    tv=new TextView(MainActivity.this);
       tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
       tv.setBackgroundColor(Color.parseColor("#66000000"));
      tv.setClickable(true);
      tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isFold=true;
                            taxWindow.dismiss();
                            layout.removeView(tv);
                        }
                    });
      layout.addView(tv);
                }
                else{
                    isFold=true;
                    taxWindow.dismiss();
                    layout.removeView(tv);
                }
            }
        });
    }

    private void showTaxDetail(View view){
        LayoutInflater inflater=LayoutInflater.from(this);
        // 加载弹出框的布局
        View contentView=inflater.inflate(R.layout.pop, null);
        contentView.measure(0,0);
        taxWindow=new PopupWindow(contentView,contentView.getMeasuredWidth(),contentView.getMeasuredHeight(),true);
        //taxWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        //taxWindow.setOutsideTouchable(true);
        taxWindow.setFocusable(false);
        int[] location = new int[2];
        // 得到按钮控件的坐标，便于定位弹出框位置
        btn.getLocationInWindow(location);
        int taxWindowWidth=taxWindow.getContentView().getMeasuredWidth();
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        taxWindow.showAtLocation(btn, Gravity.NO_GRAVITY,(screenWidth-taxWindowWidth)/2,location[1]+95);
    }
}
