package com.example.realm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.lesson;
import entity.student;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.bt_1)
    Button bt1;
    @BindView(R.id.bt_2)
    Button bt2;
    @BindView(R.id.bt_3)
    Button bt3;
    @BindView(R.id.bt_4)
    Button bt4;
    @BindView(R.id.bt_5)
    Button bt5;
    @BindView(R.id.bt_6)
    Button bt6;
    @BindView(R.id.bt_7)
    Button bt7;
    @BindView(R.id.bt_8)
    Button bt8;
    @BindView(R.id.bt_9)
    Button bt9;
    @BindView(R.id.bt_10)
    Button bt10;
    @BindView(R.id.bt_11)
    Button bt11;
    @BindView(R.id.bt_12)
    Button bt12;

    private  Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Realm.init(this);
        String key = "1234567890abcxyz1234567890abcxyz1234567890abcxyz1234567890abcxyz";
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("test.realm")
                .encryptionKey(key.getBytes()).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);
        System.out.println("success");
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        bt10.setOnClickListener(this);
        bt11.setOnClickListener(this);
        bt12.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_1:
               realm = Realm.getDefaultInstance();
                //开启事务
                realm.beginTransaction();
                //实体类以及主键的值
                student stu = realm.createObject(student.class, 1);
                stu.setName("小明");
                stu.setAge(10);
                //添加课程
                lesson lesson = new lesson();
                lesson.setName("数学");
                lesson.setId(07270001);
                lesson.setNumber(8);
                stu.getLessons().add(lesson);
                //提交事务
                realm.commitTransaction();
                break;
            case R.id.bt_2:
                Realm realm4 = Realm.getDefaultInstance();
                final student stu1 = new student();
                stu1.setId(2);
                stu1.setName("小花");
                stu1.setAge(9);
                realm4.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(stu1);
                    }
                });
                break;
            case R.id.bt_3:
                Realm realm3 = Realm.getDefaultInstance();
                realm3.beginTransaction();
                student student = realm3.where(student.class).equalTo("name", "小明").findFirst();
                student.deleteFromRealm();
                realm3.commitTransaction();
                break;
            case R.id.bt_4:
                Realm realmd = Realm.getDefaultInstance();
                realmd.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        student student = realm.where(student.class).equalTo("id", 2).findFirst();
                        student.deleteFromRealm();
                    }
                });
                break;
            case R.id.bt_5:
                Realm realmu = Realm.getDefaultInstance();
                student studentu = realmu.where(student.class).equalTo("name", "小明").findFirst();
                realmu.beginTransaction();
                studentu.setAge(20);
                realmu.commitTransaction();
                break;
            case R.id.bt_6:
                Realm realm6 = Realm.getDefaultInstance();
                final student student6 = realm6.where(student.class).equalTo("id", 2).findFirst();
                realm6.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        student6.setName("小美");
                    }
                });
            case R.id.bt_7:
                Realm realm1 = Realm.getDefaultInstance();
                student student1 = realm1.where(student.class).equalTo("id", 1).findFirst();
                //打印日志
                Toast.makeText(MainActivity.this,student1.getId() + "," + student1.getName() + "," + student1.getAge(),Toast.LENGTH_SHORT).show();
                if(student1.getLessons().size()!=0){
                   Toast.makeText(MainActivity.this,student1.getLessons().toString(),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_8:
                Realm realm8 = Realm.getDefaultInstance();
                student student8 = realm8.where(student.class).equalTo("id", "1").findFirst();
                //打印日志   student8.getId() + "," + student8.getName() + "," + student8.getAge()   student8.getLessons().toString()
                Toast.makeText(MainActivity.this,student8.getId() + "," + student8.getName() + "," + student8.getAge(),Toast.LENGTH_SHORT).show();
                if(student8.getLessons().size()!=0){
                    Toast.makeText(MainActivity.this,student8.getLessons().toString(),Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }
}
