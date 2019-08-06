package com.example.summer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import db.createDB;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.t1)
    EditText t1;
    @BindView(R.id.t2)
    EditText t2;
    @BindView(R.id.insert)
    Button insert;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.search)
    Button search;
    @BindView(R.id.update)
    Button update;
    @BindView(R.id.all)
    TextView all;

    private String text1, text2;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        insert.setOnClickListener(this);
        delete.setOnClickListener(this);
        search.setOnClickListener(this);
        update.setOnClickListener(this);
        createDB db = new createDB(MainActivity.this, "test_db", null, 1);
        sqLiteDatabase = db.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert:
                //创建存放数据的ContentValues对象
                text1 = t1.getText().toString().trim();
                ContentValues values = new ContentValues();
                values.put("name", text1);
                //数据库执行插入命令
                sqLiteDatabase.insert("user", null, values);
                break;
            case R.id.delete:
                text1 = t1.getText().toString().trim();
                sqLiteDatabase.delete("user", "name=?", new String[]{text1});
                break;
            case R.id.search:
                //创建游标对象
                Cursor cursor = sqLiteDatabase.query("user", new String[]{"name"}, null, null, null, null, null);
                //利用游标遍历所有数据对象
                //为了显示全部，把所有对象连接起来，放到TextView中
                String textview_data = "";
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    textview_data = textview_data + "\n" + name;
                }
                all.setText(textview_data);
                // 关闭游标，释放资源
                cursor.close();
                break;
            case R.id.update:
                text1 = t1.getText().toString().trim();
                text2=t2.getText().toString().trim();
                ContentValues values2 = new ContentValues();
                values2.put("name", text1);
                sqLiteDatabase.update("user", values2, "name = ?", new String[]{text2});
                break;
        }
    }
}
