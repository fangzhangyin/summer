package com.example.wifimanager;

import android.app.AlertDialog;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.wifilist)
    TextView wifilist;
    @BindView(R.id.fresh)
    Button fresh;
    @BindView(R.id.wifiall)
    ListView wifiall;
    @BindView(R.id.wifialls)
    ListView wifialls;

    private BaseFullDialog mBaseFullDialog;
    private BaseFullDialog.Builder builder;
    private String wifiname;
    private String password;
    private WifiConfiguration configuration;
    private int networkId;
    private autobase autobase;

    private List<ScanResult> all;
    private List<String> wall;
    private List<String> walls;
    private List<WifiConfiguration>confs;
    private autobase adapter;


    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        fresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fresh:
                all = new ArrayList<ScanResult>();
                wall = new ArrayList<String>();
                walls = new ArrayList<String>();
                confs=new ArrayList<WifiConfiguration>();
                wifiManager = (WifiManager) MainActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                all = wifiManager.getScanResults();
                if (all != null) {
                    for (ScanResult result : all) {
                        wall.add(result.SSID);
                    }
                }
                for (WifiConfiguration conf : wifiManager.getConfiguredNetworks()) {
                    for(String ssid:wall){
                        if(conf.SSID.equals(String.format("\"%s\"", ssid))){
                            walls.add(conf.SSID);
                            confs.add(conf);
                        }
                    }
                }
                showlist();
                break;
            case R.id.btn_dialog_left:
                mBaseFullDialog.dismiss();
                break;
            case R.id.btn_dialog_right:
                password = builder.getEditText(R.id.edit_text2).getText().toString().trim();
                if (password == null || password.equals("")) {
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    //wps类型的wifi配网
                    configuration = new WifiConfiguration();
                    configuration.SSID = "\"" + wifiname + "\"";
                    configuration.preSharedKey = "\"" + password + "\"";
                    configuration.hiddenSSID = true;
                    configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                    configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                    configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                    configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                    configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                    configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                    configuration.status = WifiConfiguration.Status.ENABLED;
                    networkId = (int) wifiManager.addNetwork(configuration);
                    wifiManager.saveConfiguration();
                    boolean connected = wifiManager.reconnect();
                    wifiManager.enableNetwork(networkId, true);
                }
                break;
        }

    }

    private void showlist() {
        adapter = new autobase(wall, MainActivity.this);
        wifiall.setAdapter(adapter);
        wifiall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, wall.get(position).toString(),Toast.LENGTH_SHORT).show();
                wifiname = wall.get(position).toString();
                show();
                builder.getTextView(R.id.edit_text1).setText(wifiname);
            }
        });
        adapter = new autobase(walls, MainActivity.this);
        wifialls.setAdapter(adapter);
        wifialls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    networkId = confs.get(position).networkId;
                    boolean connected = wifiManager.reconnect();
                    wifiManager.enableNetwork(networkId, true);
            }
        });
    }

    private void show() {
        builder = new BaseFullDialog.Builder(this);
        mBaseFullDialog = builder.setlayout(R.layout.connect)
                .style(R.style.common_alert_dialog)
                .addViewClick(R.id.btn_dialog_left, this)
                .addViewClick(R.id.btn_dialog_right, this)
                .build();
        mBaseFullDialog.show();
        mBaseFullDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

}

