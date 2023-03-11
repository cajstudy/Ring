package com.example.ring;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ring.database.MySQLiteHelper;

public class MoveScan extends AppCompatActivity {
    private ImageView imageView;
    private TextView name,ms,bs,ts,us;
    private SQLiteDatabase sqLiteDatabase;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_scan);
        getSupportActionBar().hide();
        imageView=(ImageView)findViewById(R.id.bbiv);
        name=(TextView) findViewById(R.id.tv_gnm);
        ms=(TextView) findViewById(R.id.tv_gms);
        us=(TextView) findViewById(R.id.tv_gus);
        bs=(TextView) findViewById(R.id.tv_gbs);
        ts=(TextView) findViewById(R.id.tv_gts);
        Intent intent = getIntent();
        //拿到MainActivity传过来的值并返回一个字符串，
        //口令要一致
        String goodsid = intent.getStringExtra("goodsId");
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this);
        //获取sqLiteDatabase对象
        sqLiteDatabase = mySQLiteHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("goods",new String[]{"goodsname","goodsimage","goodsms","goodsus","goodsbs","goodsts","goodsId"},"goodsId=?", new String[]{goodsid},null,null,null);
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