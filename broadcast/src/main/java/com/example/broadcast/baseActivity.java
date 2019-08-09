package com.example.broadcast;

import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;
import android.os.Bundle;

import butterknife.ButterKnife;

public class baseActivity extends Activity {
    private ExitAppReceiver exitReceiver = new ExitAppReceiver();
    //自定义退出应用Action,实际应用中应该放到整个应用的Constant类中.
    protected static final String EXIT_APP_ACTION = "com.qyx.exit_app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerExitReceiver();
    }

    private void registerExitReceiver() {
        IntentFilter exitFilter = new IntentFilter();
        exitFilter.addAction(EXIT_APP_ACTION);
        registerReceiver(exitReceiver, exitFilter);
    }

    private void unRegisterExitReceiver() {
        unregisterReceiver(exitReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterExitReceiver();
    }
}
