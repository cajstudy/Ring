package com.example.ring;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ring.bean.UserBean;
import com.example.ring.database.MySQLiteHelper;

/**
 * 数据库访问对象
 * DAO:DATEBASE ACCESS OBJECT
 * 是控制层于数据库交互的中间层，用于做数据库的增删改查具体实现
 * */
public class UserDao {
    //SQLiteDatabase对象封装了所有SQLite的增删改查语句的操作方法，让开发者直接调用就行
    private SQLiteDatabase sqLiteDatabase;


    public UserDao(Context context){
        //初始化刚刚写的MySQLiteHelper对象
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(context);
        //获取sqLiteDatabase对象
        sqLiteDatabase = mySQLiteHelper.getWritableDatabase();

    }

    /**
     * 插入一条记录进入user表
     * insert into user("username","password") values("zhangsan","123456");
     * */
    public boolean insertUser(String username,String password){
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        //第一个是表名，第二个null，第三个是相当于sql插入语句的values
        //id用于判断是否插入成功： 如果大于0则表示插入了至少一条数据，否则插入失败
        long id = sqLiteDatabase.insert("user",null,values);
        return id>0?true:false;
    }
    /**
     * 查询记录
     * select * from user where username = "zhangsan";
     * */
    @SuppressLint("Range")
    public UserBean querryUser(String username, String password){

        Cursor cursor = sqLiteDatabase.query("user",new String[]{"username","password"},"username=? and password=?",new String[]{username,password},null,null,null);

        UserBean userBean = new UserBean();
        while (cursor.moveToNext()){
            //userBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            userBean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            userBean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            Log.e("tag",userBean.getId()+"|"+userBean.getUsername()+"|"+userBean.getPassword());
        }
        return userBean;
    }

    /**
     * 修改数据库表记录
     * update user set password = 123123 where username = zhangsan
     * */
    public boolean updateUser(String username,String newpassword){
        boolean flag;
        ContentValues values = new ContentValues();
        values.put("password",newpassword);
        long id = sqLiteDatabase.update("user",values,"username = ? ",new String[]{username});
        flag = id > 0?true : false;
        return flag;
    }

    /**
     * 删除
     * delete from user where username = "xxx"
     * */
    public boolean deleteUser(String username){
        long id = sqLiteDatabase.delete("user","username = ?",new String[]{username});
        return id>0?true:false;
    }


//    /**
//     * 插入一条记录进入user表
//     * insert into user("username","password") values("zhangsan","123456");
//     * */
//    public boolean insertGoods(String goodsname,String goodsId,String goodsms,String goodsus,String goodsbs,String goodsts){
//        ContentValues values = new ContentValues();
//        values.put("goodsname",goodsname);
//        values.put("goodsId",goodsId);
//        values.put("goodsms",goodsms);
//        values.put("goodsus",goodsus);
//        values.put("goodsbs",goodsbs);
//        values.put("goodsts",goodsts);
//
//        //第一个是表名，第二个null，第三个是相当于sql插入语句的values
//        //id用于判断是否插入成功： 如果大于0则表示插入了至少一条数据，否则插入失败
//        long id = sqLiteDatabase.insert("goods",null,values);
//        return id>0?true:false;
//    }
//
//    /**
//     * 查询记录
//     * select * from user where username = "zhangsan";
//     * */
//    @SuppressLint("Range")
//    public GoodsBean querryGoods(String goodsname, String goodsId){
//
//        Cursor cursor = sqLiteDatabase.query("goods",new String[]{"goodsname"," goodsId"},"goodsname=? and goodsId=?",new String[]{goodsname,goodsId},null,null,null);
//
//        GoodsBean goodsBean = new GoodsBean();
//        while (cursor.moveToNext()){
//            //userBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
//            goodsBean.setGoodsname(cursor.getString(cursor.getColumnIndex("goodsname")));
//            goodsBean.setGoodsId(cursor.getString(cursor.getColumnIndex("goodsId")));
//            Log.e("tag",goodsBean.getgId()+"|"+goodsBean.getGoodsname()+"|"+goodsBean.getGoodsId());
//        }
//        return goodsBean;
//    }
//
//
//    /**
//     * 删除
//     * delete from user where username = "xxx"
//     * */
//    public boolean deleteGoods(String goodsname){
//        long id = sqLiteDatabase.delete("goods","goodsname = ?",new String[]{goodsname});
//        return id>0?true:false;
//    }

}