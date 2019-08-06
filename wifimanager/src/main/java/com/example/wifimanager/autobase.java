package com.example.wifimanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class autobase {
    public static void myadapt(final List<String>list, final Context context, final int layout, final int v){

        class adapt extends BaseAdapter{

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(layout, null);
                TextView wifi = (TextView) view.findViewById(v);
                wifi.setText(list.get(position));
                return view;
            }
        }

    }
}
