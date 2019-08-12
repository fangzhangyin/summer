package com.example.tomcat_app;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tv_version;
    private LinearLayout ll;

    private UpdateInfo info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_version = (TextView) findViewById(R.id.tv_splash_version);
        String version = getVersion();
        tv_version.setText("版本号  " + version);

        ll = (LinearLayout) findViewById(R.id.ll_splash_main);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2000);
        ll.startAnimation(alphaAnimation);

        if(isNeedUpdate(version))
        {
            showUpdateDialog();
        }
    }


    private void showUpdateDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("升级提醒");
        builder.setMessage(info.getDescription());
        builder.setCancelable(false);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // TODO Auto-generated method stub

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // TODO Auto-generated method stub

            }

        });
        builder.create().show();
    }

    private boolean isNeedUpdate(String version)
    {
        UpdateInfoService updateInfoService = new UpdateInfoService(this);
        try
        {
            info = updateInfoService.getUpdateInfo(R.string.serverUrl);
            String v = info.getVersion();
            if(v.equals(version))
            {
                System.out.println("不用更新");
                return false;
            }
            else
            {
                System.out.println("要更新");
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "获取更新信息异常，请稍后再试", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private String getVersion()
    {
        try
        {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            return packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return "版本号未知";
        }
    }
}
