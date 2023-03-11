package com.example.ring.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ring.MainActivity;
import com.example.ring.MoveList;
import com.example.ring.MoveScan;
import com.example.ring.R;
import com.example.ring.ScanActivity;
import com.example.ring.adapter.GoodsAdapter;
import com.example.ring.bean.GoodsBean;
import com.example.ring.database.MySQLiteHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Objects;


public class scanFragment extends Fragment {

    private View view,view2;
    private TextView tvResult;
    private  ImageButton btn;
    private EditText mEditText;
    private ImageView mImageView;
    private ListView mListView;
    private TextView mTextView;
    private SQLiteDatabase db;
    private  ArrayList<GoodsBean> listData;
    private GoodsAdapter adapter;
    Cursor cursor;

    public scanFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_scan, container, false);

        view = inflater.inflate(R.layout.fragment_scan, null);
        btn = (ImageButton)  view.findViewById(R.id.iv_scan);
        tvResult=(TextView) view.findViewById(R.id.tvResult);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                /*以下是启动我们自定义的扫描活动*/
//                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());

                intentIntegrator.setBeepEnabled(true);
                /*设置启动我们自定义的扫描活动，若不设置，将启动默认活动*/
                intentIntegrator.setCaptureActivity(ScanActivity.class);
                intentIntegrator.initiateScan();
//        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void requsetPermission(){
        if (Build.VERSION.SDK_INT>22){
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.CAMERA)!=     PackageManager.PERMISSION_GRANTED){
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.CAMERA},1);

            }else {

            }
        }else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //这里已经获取到了摄像头的权限，想干嘛干嘛了可以

                } else {
                    //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                    Toast.makeText(getActivity(), "请手动打开相机权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                String scanResult = result.getContents();
                //将扫描出的信息显示出来，拿到信息之后做什么那就是你自己的事情了
                tvResult.setText(scanResult);

                Intent intent = new Intent(getActivity(), MoveScan.class);
                //把扫到并解析到的信息(既:字符串)带到详情页面
                intent.putExtra("goodsId", scanResult);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}