package com.example.servicer;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;

public class phonetest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonetest);
    }

    public void endphone(View v) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, RemoteException {
        Class c=Class.forName("android.os.ServiceManager");
        Method method=c.getMethod("getService",String.class);
        IBinder iBinder=(IBinder)method.invoke(null,new Object[]{Context.TELECOM_SERVICE});
        ITelephony telephony=ITelephony.Stub.asInterface(iBinder);
        telephony.endCall();
    }

    /**
     * 挂断电话
     */
    public void endcall() {
        try {
            Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null, new Object[]{Context.TELEPHONY_SERVICE});
            ITelephony telephony = ITelephony.Stub.asInterface(binder);
            telephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
