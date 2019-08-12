import entity.lesson;
import entity.student;
import io.realm.Realm;

public class DB {
    private static Realm realm;

    public static boolean add(student student){
        boolean flag=false;
        realm = Realm.getDefaultInstance();
        //开启事务
        realm.beginTransaction();
        //实体类以及主键的值
        student stu = realm.createObject(student.class, 1);
        stu.setName(student.getName());
        stu.setAge(student.getAge());
        //添加课程
        entity.lesson lesson = new lesson();
        lesson.setName("数学");
        lesson.setId(07270001);
        lesson.setNumber(8);
        stu.getLessons().add(lesson);
        //提交事务
        realm.commitTransaction();
        return true;
    }


}
