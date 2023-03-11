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

public class MoveSearch extends AppCompatActivity {
    private ImageView imageView;
    private TextView name,ms,bs,ts,us;
    private SQLiteDatabase sqLiteDatabase;

    @SuppressLint("Range")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_search);
        getSupportActionBar().hide();
        imageView=(ImageView)findViewById(R.id.cciv);
        name=(TextView) findViewById(R.id.tv_gne);
        ms=(TextView) findViewById(R.id.tv_cgm);
        us=(TextView) findViewById(R.id.tv_cus);
        bs=(TextView) findViewById(R.id.tv_cbs);
        ts=(TextView) findViewById(R.id.tv_cts);
        Intent intent = getIntent();
        //拿到MainActivity传过来的值并返回一个字符串，
        //口令要一致
        String goodsname = intent.getStringExtra("goodsname");

        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this);
        //获取sqLiteDatabase对象
        sqLiteDatabase = mySQLiteHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("goods", new String[]{"goodsname","goodsimage","goodsms","goodsus","goodsbs","goodsts","goodsId"}, "goodsname"+"  LIKE ? ",
                new String[] { "%" + goodsname+ "%" }, null, null, null);
        cursor.moveToFirst();
            name.setText(cursor.getString(cursor.getColumnIndex("goodsname")));
            us.setText(cursor.getString(cursor.getColumnIndex("goodsus")));
            ms.setText(cursor.getString(cursor.getColumnIndex("goodsms")));
            bs.setText(cursor.getString(cursor.getColumnIndex("goodsbs")));
            ts.setText(cursor.getString(cursor.getColumnIndex("goodsts")));
            imageView.setImageResource(cursor.getInt(cursor.getColumnIndex("goodsimage")));
//            Toast.makeText(this, "" + cursor.getString(cursor.getColumnIndex("goodsId")), Toast.LENGTH_LONG).show();
            cursor.close();


    }

}