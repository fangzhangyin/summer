package entity;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class student extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private int age;

    private RealmList<lesson> lessons;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RealmList<lesson> getLessons() {
        return lessons;
    }

    public void setLessons(RealmList<lesson> lessons) {
        this.lessons = lessons;
    }
}
