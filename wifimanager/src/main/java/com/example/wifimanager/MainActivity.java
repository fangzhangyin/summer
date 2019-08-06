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


    private BaseFullDialog mBaseFullDialog;
    private BaseFullDialog.Builder builder;
    private String wifiname;
    private String password;
    private WifiConfiguration configuration;
    private int networkId;

    private List<ScanResult> all;
    private List<String> wall;
    private myadapt adapter;


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
                wifiManager =(WifiManager) MainActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                all = wifiManager.getScanResults();
                if (all != null) {
                    for (ScanResult result : all) {
                        wall.add(result.SSID);
                        adapter = new myadapt();
                        wifiall.setAdapter(adapter);
                        wifiall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //Toast.makeText(MainActivity.this, wall.get(position).toString(),Toast.LENGTH_SHORT).show();
                                wifiname=wall.get(position).toString();
                                show();
                                builder.getTextView(R.id.edit_text1).setText(wifiname);
                            }
                        });
                    }
                    //autobase.myadapt(wall,MainActivity.this,R.layout.wifilist1,R.id.wifi);
                }
                break;
            case R.id.btn_dialog_left:
                mBaseFullDialog.dismiss();
                break;
            case R.id.btn_dialog_right:
                password=builder.getEditText(R.id.edit_text2).getText().toString().trim();
                if(password==null||password.equals("")){
                    Toast.makeText(MainActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else{
                    configuration=new WifiConfiguration();
                    configuration.SSID="\"" + wifiname + "\"";
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

    public class myadapt extends BaseAdapter {

        @Override
        public int getCount() {
            return wall.size();
        }

        @Override
        public Object getItem(int position) {
            return wall.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View view = layoutInflater.inflate(R.layout.wifilist1, null);
            TextView wifi = (TextView) view.findViewById(R.id.wifi);
            wifi.setText(wall.get(position));
            return view;
        }
    }



}
