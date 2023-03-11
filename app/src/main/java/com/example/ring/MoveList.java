package com.example.ring;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ring.bean.GoodsBean;
import com.example.ring.bean.UserBean;
import com.example.ring.database.MySQLiteHelper;

public class MoveList extends Activity {
    private ImageView imageView;
    private TextView name,ms,bs,ts,us;
    private SQLiteDatabase sqLiteDatabase;


    @SuppressLint("Range")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_list);
        imageView=(ImageView)findViewById(R.id.iv);
        name=(TextView) findViewById(R.id.tv_gn);
        ms=(TextView) findViewById(R.id.tv_gm);
        us=(TextView) findViewById(R.id.tv_us);
        bs=(TextView) findViewById(R.id.tv_bs);
        ts=(TextView) findViewById(R.id.tv_ts);
        Bundle bundle=getIntent().getExtras();
        String goodsname=bundle.getString("goodsname");

        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this);
        //获取sqLiteDatabase对象
        sqLiteDatabase = mySQLiteHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("goods",new String[]{"goodsname","goodsimage","goodsms","goodsus","goodsbs","goodsts","goodsId"},"goodsname=?", new String[]{goodsname},null,null,null);
        cursor.moveToFirst();

        name.setText(cursor.getString(cursor.getColumnIndex("goodsname")));
        us.setText(cursor.getString(cursor.getColumnIndex("goodsus")));
        ms.setText(cursor.getString(cursor.getColumnIndex("goodsms")));
        bs.setText(cursor.getString(cursor.getColumnIndex("goodsbs")));
        ts.setText(cursor.getString(cursor.getColumnIndex("goodsts")));
        imageView.setImageResource(cursor.getInt(cursor.getColumnIndex("goodsimage")));



//        Toast.makeText(this, ""+cursor.getString(cursor.getColumnIndex("goodsId")), Toast.LENGTH_LONG).show();

        cursor.close();


    }

}