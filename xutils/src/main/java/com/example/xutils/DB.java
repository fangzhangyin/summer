package com.example.xutils;

import com.example.xutils.entity.person;

import org.xutils.DbManager;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class DB {

    static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("my_db.db") //设置数据库名
            .setDbVersion(1) //设置数据库版本,每次启动应用时将会检查该版本号,
            // 发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
            .setAllowTransaction(true) //设置是否开启事务,默认为false关闭事务
            .setTableCreateListener(new DbManager.TableCreateListener() {
                @Override
                public void onTableCreated(DbManager dbManager, TableEntity<?> tableEntity) {
                }
            })
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    // 开启WAL, 对写入加速提升巨大
                    db.getDatabase().enableWriteAheadLogging();
                }
            })
            // 设置数据库创建时的Listener
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    // TODO: ...
                    // db.addColumn(...);
                    // db.dropTable(...);
                    // ...
                    // or
                    // db.dropDb();
                }
            }); //设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等

    // .setDbDir(null);//设置数据库.db文件存放的目录,默认为包名下databases目录下


    public static boolean add(person person) {
        boolean flag = false;
        DbManager dbManager = x.getDb(daoConfig);
        try {
            List<person> list = new ArrayList<person>();
            list.add(person);
            // ... 加载数据
//            dbManager.save(list); // 保存实体类或者实体类的List到数据库
//            dbManager.saveOrUpdate(list); // 保存或更新实体类或者实体类的List到数据库，根据id对应的数据是否存在
            dbManager.saveBindingId(list); // 保存实体类或实体类的List到数据库，如果该类型的id是自动生成的，则保存完后会给id赋值
            flag = true;
        } catch (DbException e) {

        }
        return flag;
    }

    public static boolean deletebyname(String name) {
        boolean flag = false;
        try {
            DbManager db = x.getDb(daoConfig);
//            db.delete(person.class);//该方法是删除表中的全部数据
//            db.deleteById(person.class, id);//该方法主要是根据表的主键(id)进行单条记录的删除
//            db.delete(person.class, WhereBuilder.b("age", ">", "20"));//根据where语句的条件进行删除操作
//            List<person> findAll = db.selector(person.class).expr("age > 20").findAll();
//            db.delete(findAll);//根据实体bean进行对表里面的一条或多条数据进行删除
            db.delete(person.class, WhereBuilder.b("name", "=", name));
            flag = true;
        } catch (DbException e) {
        }
        return flag;
    }

    public static boolean update(person p) {
        boolean flag = false;
        // 第一种
        try {
            DbManager db = x.getDb(daoConfig);
            db.saveOrUpdate(p);
            flag = true;
        } catch (DbException e) {

        }
        return flag;
    }


    /**
     * db.findById(person.class, 1);//通过主键的值来进行查找表里面的数据
     *             db.findFirst(person.class);//返回当前表里面的第一条数据
     *             List<person> findAll = db.findAll(person.class);//返回当前表里面的所有数据
     *             db.findDbModelAll(new SqlInfo("select * from person where age > 25"));
     *             DbModel model = db.findDbModelFirst(new SqlInfo("select * from person where age > 25"));
     *             model.getString("age");//model相当于游标
     *             List<person> findAll2 = db.selector(person.class).expr("age >10").findAll();//主要是用来进行一些特定条件的查找
     * */
    public static person findbyid(int id) {
        person person=new person();
        DbManager db = x.getDb(daoConfig);
        try {
            person=db.findById(person.class,id);
        } catch (DbException e) {
        }
        return person;
    }

    public static person findbyname(String name) throws DbException {
        person person=new person();
        DbManager db = x.getDb(daoConfig);
        List<person> list=db.selector(com.example.xutils.entity.person.class).expr("name ="+name).findAll();
        if(list.size()==1){
            person=list.get(0);
        }
        return person;
    }

    public static List<person> findall() {
        List<person> list = new ArrayList<person>();
        DbManager db = x.getDb(daoConfig);
        try {
            list = db.findAll(person.class);//返回当前表里面的所有数据
        } catch (DbException e) {
        }
        return list;
    }

}
