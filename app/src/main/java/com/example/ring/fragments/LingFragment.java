package com.example.ring.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ring.ClearEditText;
import com.example.ring.MoveList;
import com.example.ring.MoveSearch;
import com.example.ring.R;
import com.example.ring.adapter.GoodsAdapter;
import com.example.ring.bean.GoodsBean;
import com.example.ring.database.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;


public class LingFragment extends Fragment {
    TextView tv_name,tv_ms,tv_us;
    ImageView imageView;
    ListView list_view;
    private SQLiteDatabase db;
    MySQLiteHelper helper;
    private ArrayList<GoodsBean> listData;
    private GoodsAdapter adapter;

    private EditText et_search;
    private ListView user_list;

    private ArrayList<GoodsBean> mDatas = new ArrayList<>();
    private GoodsAdapter mUserAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ling, container, false);
    }
    @SuppressLint("Range")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        tv_name = (TextView)getActivity().findViewById(R.id.tv_name);
        tv_ms = (TextView)getActivity().findViewById(R.id.tv_ms);
        tv_us = (TextView)getActivity().findViewById(R.id.tv_us);
        imageView=(ImageView)getActivity().findViewById(R.id.image);

        list_view = (ListView)getActivity().findViewById(R.id.list_view);
        listData = new ArrayList<GoodsBean>();
        //创建MySQLiteHelper实例
        helper = new MySQLiteHelper(getActivity());
        //得到数据库
        db = helper.getWritableDatabase();
        //查询数据
        Cursor cursor= db.query("goods",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            GoodsBean diary = new GoodsBean();
            diary.setGoodsname(cursor.getString(cursor.getColumnIndex("goodsname")));
            diary.setGoodsms(cursor.getString(cursor.getColumnIndex("goodsms")));
            diary.setGoodsus(cursor.getString(cursor.getColumnIndex("goodsus")));
            diary.setImage(cursor.getInt(cursor.getColumnIndex("goodsimage")));
            listData.add(diary);
        }
        list_view.setSelection(ListView.FOCUS_DOWN);
        adapter = new GoodsAdapter(this.getActivity(),listData);
        list_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list_view.setSelection(ListView.FOCUS_DOWN);
            }
        },500);

            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(getActivity(), MoveList.class);
            TextView textView = view.findViewById(R.id.tv_name);
            intent.putExtra("goodsname", textView.getText().toString());
            startActivity(intent);
        }
    });
//        initView();
        et_search = (EditText) getActivity().findViewById(R.id.et_search);
        ImageButton btn = (ImageButton)getActivity().findViewById(R.id.btnEdit2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edText = et_search.getText().toString();
//                Toast.makeText(getActivity(), "" + edText, Toast.LENGTH_LONG).show();
                MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(getActivity());
                //获取sqLiteDatabase对象
                db = mySQLiteHelper.getWritableDatabase();
                Cursor cursor = db.query("goods", new String[]{"goodsname", "goodsimage", "goodsms", "goodsus", "goodsbs", "goodsts", "goodsId"}, "goodsname" + "  LIKE ? ",
                        new String[]{"%" + edText + "%"}, null, null, null);
                if (cursor.moveToFirst()) {
                    Intent intent = new Intent(getActivity(), MoveSearch.class);
                    //接收EditText输入的数据
                    //通过Intent传递数据
                    intent.putExtra("goodsname", edText);
                    //开启Intent
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), "数据库无此数据,请重新输入关键词", Toast.LENGTH_LONG).show();

                }
            }
        });





    }
    /*
     *模糊查询数据 并显示在ListView列表上
     * */
    private void initView() {
        et_search = (EditText) getActivity().findViewById(R.id.et_search);
        user_list = (ListView) getActivity().findViewById(R.id.list_view);
        initListView();
        intiEditView();
    }

    private void intiEditView() {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mAdapter.getFilter().filter(s);

                mUserAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initListView() {
//        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
//        user_list.setAdapter(mAdapter);

        mUserAdapter = new GoodsAdapter(getActivity(),mDatas);
        user_list.setAdapter(mUserAdapter);
    }


}