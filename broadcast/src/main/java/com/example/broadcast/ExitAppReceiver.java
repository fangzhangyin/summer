package com.example.broadcast;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

class ExitAppReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context != null) {
            if (context instanceof Activity) {
                //退出Activity
                ((Activity) context).finish();
            } else if (context instanceof FragmentActivity) {
                //退出FragmentActivity
                ((FragmentActivity) context).finish();
            } else if (context instanceof Service) {
                //退出Service
                ((Service) context).stopSelf();
            }
            String action =intent.getAction();
            System.out.println(action);
        }
    }
}
