package com.example.servicer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class phoneService extends Service {

    private TelephonyManager telephonyManager;
    private PhoneStateListener listener = new PhoneStateListener() {
        //重写方法

        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://空闲(挂断电话，未来电之前)
                    System.out.println("空闲");
                    break;
                case TelephonyManager.CALL_STATE_RINGING://响铃
                    System.out.println("响铃");
                    //可以通过数据库存放黑名单，然后再通过取得数据来进行判断；
                    if ("18257512546".equals(phoneNumber)||"18069486356".equals(phoneNumber)) {
                        endCall();
                    }
                    //如果来电属于黑名单，自动挂断电话
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://接通
                    System.out.println("接通");
                    break;

            }
        }
    };

    private void endCall() {
        Class c = null;
        try {
            c = Class.forName("android.os.ServiceManager");
            Method method = null;
            try {
                method = c.getMethod("getService", String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            IBinder iBinder = null;
            try {
                iBinder = (IBinder) method.invoke(null, new Object[]{Context.TELECOM_SERVICE});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            ITelephony telephony = ITelephony.Stub.asInterface(iBinder);
            try {
                telephony.endCall();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("service is start");
        //得到电话管理器，监听电话
        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("service is close");
        //停止监听
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_NONE);
    }


}
