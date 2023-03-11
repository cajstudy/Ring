package com.example.ring;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import com.example.ring.database.MySQLiteHelper;

import java.io.ByteArrayOutputStream;

public class DataBase {
    private Bitmap bit;
    private SQLiteDatabase sqLiteDatabase;
    private  static  final  String INSERT_USER_DATA="insert into user(username,password) values('root','1234')";
    private  static  final  String INSERT_GOODS_DATA="insert into goods(goodimage,goodsname,goodsId,goodsms,goodsus,goodsbs,goodsts) values(R.drawable.add,'c++程序设计','9787508399362','复试参考书','学习','无','无')";
    //    goods(gid,goodsname,goodsId,goodsms,goodsus,goodsbs,goodsts)
    //SQLiteDatabase对象封装了所有SQLite的增删改查语句的操作方法，让开发者直接调用就行


    public DataBase(Context context){
        //初始化刚刚写的MySQLiteHelper对象
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(context);
        //获取sqLiteDatabase对象
        sqLiteDatabase = mySQLiteHelper.getWritableDatabase();
    }

    /**
     * 插入一条记录进入user表
     * insert into user("username","password") values("zhangsan","123456");
     * */
    public void insertGoodData(){
        ContentValues values = new ContentValues();
        values.put("goodsimage", R.drawable.cshu);
        values.put("goodsname","c++程序设计");
        values.put("goodsId","9787508399362");
        values.put("goodsms","考研复试参考书");
        values.put("goodsus","好好学");
        values.put("goodsbs","无");
        values.put("goodsts","无");
        //第一个是表名，第二个null，第三个是相当于sql插入语句的values
        //id用于判断是否插入成功： 如果大于0则表示插入了至少一条数据，否则插入失败
        long id = sqLiteDatabase.insert("goods",null,values);
    }
    public void insertUserData(String username,String password){
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        //第一个是表名，第二个null，第三个是相当于sql插入语句的values
        //id用于判断是否插入成功： 如果大于0则表示插入了至少一条数据，否则插入失败
        long id = sqLiteDatabase.insert("user",null,values);
    }

    }



