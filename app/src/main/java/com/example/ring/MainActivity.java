package com.example.ring;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ring.fragments.MyFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;


import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Button btnlogin,btnregist,btnupdate,btndelete,back;
    UserDao userDao;
    GoodsDao goodsDao;
    DataBase dataBase;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //接收数据，注意getStringExtra()中的字符串必须和putExtra()中的第一个字符串保持一致
        Intent intent=getIntent();
        name = intent.getStringExtra("name");
//        Toast.makeText(this, name+"1111", Toast.LENGTH_LONG).show();



//        导航栏
        getSupportActionBar().hide();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,R.id.navigation_edit,R.id.navigation_view,R.id.navigation_user)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
//        登陆注册功能
        SQLiteStudioService.instance().start(this);
        userDao = new UserDao(this);
        goodsDao = new GoodsDao(this);
        dataBase =new DataBase(this);
        btnlogin = findViewById(R.id.btn_login);
        btnregist = findViewById(R.id.btn_regist);

        dataBase.insertGoodData();
        userDao.insertUser("caj","1234");
        dataBase.insertUserData("root","1234");
        goodsDao.insertGoods(R.drawable.xifashui,"红吕洗发水","8801042513433","清洁头发洗发露","清洁，护发","2年","无");
        goodsDao.insertGoods(R.drawable.muyulu,"澳宝星空秘境沐浴露","6917921049633","沐浴露","清洁身体","3年","无");
        goodsDao.insertGoods(R.drawable.ximiannai,"旁氏米粹洗面奶","6902088421330","洗面奶","洗脸","2年","便宜大碗");
        goodsDao.insertGoods(R.drawable.doudou,"痘痘贴","8809324921181","消灭豆豆","贴于患处","1年","无");
        goodsDao.insertGoods(R.drawable.lvjian,"绿箭无糖薄荷糖","6923450605929","坚实型压片糖果","清新口气","12个月","黑加仑口味");
        goodsDao.insertGoods(R.drawable.naifen,"蒙牛羊奶粉","6940187266308","调制乳粉","冲泡","1年","无");
        goodsDao.insertGoods(R.drawable.yansuan,"盐酸左氧氟沙星胶囊","83567720010547794884","本品为硬胶囊，内容为类白色或淡黄色粉末或颗粒","治疗成年人由细菌引起的感染","36个月","详见说明书");
        goodsDao.insertGoods(R.drawable.lvlei,"氯雷他定片","81768930196422650459","每袋装2.6克","口服。一次1~2袋，一日两次","12个月","密封，阴凉处保存");
        goodsDao.insertGoods(R.drawable.liushen,"驱蚊花露水","6970667220148","驱蚊","按压喷口使花露水喷出","2年","净含量195Ml");
        goodsDao.insertGoods(R.drawable.shentiru,"澳宝西柚身体乳","6917921056686","身体乳","沐浴后涂抹全身","12个月","柚子风味");
        goodsDao.insertGoods(R.drawable.huangpishu,"考研英语历年真题","9787572214523","备考刷题","学习","无","上架建议：国内考试/考研");
        goodsDao.insertGoods(R.drawable.hand,"隆力奇护手霜","6900077003475","营养滋润皮肤，有效对抗皮肤干燥","洗手后，均匀涂于手部皮肤","2年","含精致蛇油，维他命E等多种营养成分");
        goodsDao.insertGoods(R.drawable.meiji,"美即皙白清润面膜","6923700940985","含美白专利成分，集淡斑，美白，保湿三重功效","将膜布朝里，轻敷于清洁后的面部肌肤","2年","奶样精华搭配奶皮般的膜布。焕白祛斑作用肌底，肌肤如牛奶般白皙润滑");
        goodsDao.insertGoods(R.drawable.hanshu,"韩束玻尿酸水库保湿面膜","6972322420580","玻尿酸层层浸透，提升肌肤保湿力","静享10分钟面膜渗透滋养","2年","25Ml一片");
        goodsDao.insertGoods(R.drawable.yanying,"完美日记眼影盘","8801042513433","化妆","涂于眼部，提亮眼皮","2年","无");

    }
    public String getTitles(){
        return this.name;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode,resultCode,intent);
        getSupportFragmentManager().getFragments();
        if (getSupportFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment mFragment : fragments) {
                mFragment.onActivityResult(requestCode, resultCode, intent);
            }
        }
    }
    public void click1(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("通知");
        builder.setMessage("是否确定要退出");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("成功退出");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("还在线上");
            }
        });
        //一样要show
        builder.show();

    }

}