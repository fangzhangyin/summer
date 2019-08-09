package com.example.xutils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xutils.entity.person;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ContentView(R.layout.activity_usql)
public class usql extends AppCompatActivity {

    @ViewInject(R.id.addp)
    private Button addp;
    @ViewInject(R.id.searchp)
    private Button searchp;
    @ViewInject(R.id.deletep)
    private Button deletep;
    @ViewInject(R.id.updatep)
    private Button updatep;

    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.sex)
    private EditText sex;
    @ViewInject(R.id.age)
    private EditText age;

    private String pname, page, psex;

    @ViewInject(R.id.result)
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }

    @Event(value = {R.id.addp, R.id.searchp, R.id.deletep, R.id.updatep}, type = View.OnClickListener.class)
    private void setclick(View view) {
        switch (view.getId()) {
            case R.id.addp:
                person person = new person();
                pname = name.getText().toString();
                page = age.getText().toString();
                int a = Integer.parseInt(page);
                psex = sex.getText().toString();
                person.setName(pname);
                person.setAge(a);
                person.setSex(psex);
                if (DB.add(person)) {
                    result.setText("增加数据成功");
                }
                break;
            case R.id.searchp:
                List<person>list=new ArrayList<person>();
                list=DB.findall();
                Iterator iterator=list.iterator();
                while (iterator.hasNext()){
                    person p=(person)iterator.next();
                }
                result.setText(list.size()+list.toString());
                break;
            case R.id.deletep:
                pname=name.getText().toString();
                if(DB.deletebyname(pname)){
                    result.setText("删除成功");
                }
                break;
            case R.id.updatep:
                person person1 = new person();
                pname = name.getText().toString();
                page = age.getText().toString();
                int b = Integer.parseInt(page);
                psex = sex.getText().toString();
                person1.setName(pname);
                person1.setAge(b);
                person1.setSex(psex);
                if(DB.update(person1)){
                    result.setText("更新成功");
                }
                break;
        }
    }
}
