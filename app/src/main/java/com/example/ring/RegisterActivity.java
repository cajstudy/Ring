package com.example.ring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Button btnlogin,btnregist,btnupdate,btndelete;
    EditText rname,rpwd,rChange;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDao = new UserDao(this);
        rname =findViewById(R.id.r_username);
        rpwd = findViewById(R.id.r_password);
        btnlogin = findViewById(R.id.btn_login);
        btnregist = findViewById(R.id.btn_regist);

        btnregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Rusername = rname.getText().toString();
                String Rpassword = rpwd.getText().toString();
                if (Rusername != null && Rusername.length() != 0 && Rpassword != null && Rpassword.length() != 0) {

                    boolean flag = userDao.insertUser(Rusername, Rpassword);
                    if (flag) {
                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                        intent.setClass(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}