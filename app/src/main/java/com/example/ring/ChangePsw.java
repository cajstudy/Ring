package com.example.ring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePsw extends AppCompatActivity {

    Button btnupdate;
    EditText pname,ppwd;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psw);
        userDao = new UserDao(this);

        pname = findViewById(R.id.p_username);
        ppwd = findViewById(R.id.p_password);
        btnupdate = findViewById(R.id.btn_change);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = pname.getText().toString();
                String password = ppwd.getText().toString();
                boolean flag = userDao.updateUser(username,password);
                if(flag){
                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                    intent.setClass(ChangePsw.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}