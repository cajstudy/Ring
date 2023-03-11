package com.example.ring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ring.bean.UserBean;
import com.example.ring.fragments.MyFragment;

public class LoginActivity extends AppCompatActivity {
    TextView register,changepsd;
    Button btnlogin,btnregist,btnupdate,btndelete;
    EditText lname,lpwd,lChange;
    UserDao userDao;
    MyFragment a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao = new UserDao(this);
        setContentView(R.layout.activity_login);
        register = findViewById(R.id.lg_register);
        changepsd =findViewById(R.id.chang_psd);
        lname = findViewById(R.id.l_username);
        lpwd = findViewById(R.id.l_password);
        btnlogin = findViewById(R.id.btn_login);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        changepsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(LoginActivity.this,ChangePsw.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lusername = lname.getText().toString();
                String lpassword = lpwd.getText().toString();
                UserBean userBean = userDao.querryUser(lusername,lpassword);
                if(TextUtils.isEmpty(userBean.getUsername())){
                    Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                    intent.putExtra("name",lusername);
                    intent.setClass(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                }
            }
        });

    }
}