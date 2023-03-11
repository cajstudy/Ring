package com.example.ring;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.ring.database.*;
import com.example.ring.utils.DBUtils;

import java.util.ArrayList;
import java.util.Date;

public class RecordActivity extends Activity implements View.OnClickListener {
    ImageView note_back,delete,note_save;
    TextView note_time,time,t_day,dui;
    EditText content;
    ImageButton note_t,note_r;
    Calendar calendar;
    com.example.ring.database.SQLiteHelper mSQLiteHelper;
    TextView noteName;
    String id;
    int year,month,day,hourOfDay,minute;
    int yy,mm,dd,hh,mn;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        time=(TextView)findViewById(R.id.t_time);
        t_day=(TextView)findViewById(R.id.t_day);
        dui=(TextView)findViewById(R.id.dui);
        note_back = (ImageView) findViewById(R.id.note_back);
        note_time = (TextView)findViewById(R.id.tv_time);
        note_t=(ImageButton)findViewById(R.id.note_t);
        note_r=(ImageButton)findViewById(R.id.note_r);
        content = (EditText) findViewById(R.id.note_content);
        delete = (ImageView) findViewById(R.id.delete);
        note_save = (ImageView) findViewById(R.id.note_save);
        noteName = (TextView) findViewById(R.id.note_name);
        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);
        note_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在屏幕上显示提示 Toast 吐司
                Calendar calendar = Calendar.getInstance();
                hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY);    //得到小时
                minute  = calendar.get(Calendar.MINUTE);            //得到分钟
                new TimePickerDialog(RecordActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //  这个方法是得到选择后的 小时、分钟，分别对应着三个参数 —   hourOfDay、minute
                        time.setText(""+hourOfDay+":"+minute);
                        hh=hourOfDay;
                        mn=minute;
                    }
                }, hourOfDay, minute, true).show();

            }

        });
        note_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在屏幕上显示提示 Toast 吐司
                Calendar mcalendar = Calendar.getInstance();     //  获取当前时间    —   年、月、日
                year = mcalendar.get(Calendar.YEAR);         //  得到当前年
                month = mcalendar.get(Calendar.MONTH);       //  得到当前月
                day = mcalendar.get(Calendar.DAY_OF_MONTH);  //  得到当前日

                new DatePickerDialog(RecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //  这个方法是得到选择后的 年，月，日，分别对应着三个参数 — year、month、dayOfMonth
                        t_day.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");
                        yy=year;
                        mm=(month+1);
                        dd=dayOfMonth;
                    }
                },yy,mm,dd).show();   //  弹出日历对话框时，默认显示 年，月，日

            }
        });
        dui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCalendar(yy,(mm-1),dd,hh,mn);
//                Toast.makeText(RecordActivity.this, yy+" "+mm+" "+dd+" "+hh+" "+mn, Toast.LENGTH_LONG).show();

         }
        });

        initData();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)


//    设置日历提醒

    private void OpenCalendar(int year,int month,int day,int hourOfDay,int minute) {
        Calendar beginTime = Calendar.getInstance();//开始时间
        beginTime.clear();
        beginTime.set(year,month,day,hourOfDay,minute);//2014年1月1日12点0分(注意：月份0-11，24小时制)
        Calendar endTime = Calendar.getInstance();//结束时间
        endTime.clear();
        endTime.set(year,month,day,hourOfDay+1,minute);//2014年2月1日13点30分(注意：月份0-11，24小时制)
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(Uri.parse("content://com.android.calendar/events"))
                .putExtra("beginTime", beginTime.getTimeInMillis())
                .putExtra("endTime", endTime.getTimeInMillis())
                .putExtra("title", "标题")
                .putExtra("description", "地点");
        startActivity(intent);
    }
    //    设置闹钟
    public static boolean setSystemAlarmClock(Context context, String message, int hour, int minute) {
        if (Build.VERSION.SDK_INT < 9) {
            return false;
        }
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minute);
        if (Build.VERSION.SDK_INT >= 11) {
            intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            intent.putExtra(AlarmClock.EXTRA_VIBRATE, true);
        }
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void initData() {
        mSQLiteHelper = new SQLiteHelper(this);
        noteName.setText("添加记录");
        Intent intent = getIntent();
        if(intent!= null){
            id = intent.getStringExtra("id");
            if (id != null){
                noteName.setText("修改记录");
                content.setText(intent.getStringExtra("content"));
                note_time.setText(intent.getStringExtra("time"));
                note_time.setVisibility(View.VISIBLE);
            }
        }
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_back:
                finish();
                break;
            case R.id.delete:
                content.setText("");
                break;
            case R.id.note_save:
                String noteContent=content.getText().toString().trim();
                if (id != null){//修改操作
                    if (noteContent.length()>0){
                        if (mSQLiteHelper.updateData(id, noteContent, DBUtils.getTime())){
                            showToast("修改成功");
                            setResult(2);
                            finish();
                        }else {
                            showToast("修改失败");
                        }
                    }else {
                        showToast("修改内容不能为空!");
                    }
                }else {
                    //向数据库中添加数据
                    if (noteContent.length()>0){
                        if (mSQLiteHelper.insertData(noteContent, DBUtils.getTime())){
                            showToast("保存成功");
                            setResult(2);
                            finish();
                        }else {
                            showToast("保存失败");
                        }
                    }else {
                        showToast("修改内容不能为空!");
                    }
                }
                break;
        }
    }
    public void showToast(String message){
        Toast.makeText(RecordActivity.this,message,Toast.LENGTH_SHORT).show();
    }
    // 设置闹钟
    public void setAlarm(String content, int hour, int minute){
        ArrayList<Integer> testDays = new ArrayList<>();
        testDays.add(Calendar.MONDAY);//周一
        testDays.add(Calendar.TUESDAY);//周二
        testDays.add(Calendar.WEDNESDAY);//周三
        testDays.add(Calendar.THURSDAY);
        testDays.add(Calendar.FRIDAY);//
        testDays.add(Calendar.SATURDAY);
        testDays.add(Calendar.SUNDAY);//


        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
        alarmIntent.putExtra(AlarmClock.EXTRA_DAYS,testDays); // 时间
        alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE,content); // 标题
        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR,hour); // 小时
        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES,minute); // 分钟
        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);//设置闹钟时不显示系统闹钟界面
        alarmIntent.putExtra(AlarmClock.EXTRA_VIBRATE,true);//设置闹钟响时震动
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(alarmIntent);
    }

    // 取消闹钟
    public void dismissAlarm(String content){
        if (Build.VERSION.SDK_INT < 23){
            Toast.makeText(this,"手机版本过低,需手动取消闹钟",
                    Toast.LENGTH_SHORT).show();
            Intent alarmIntent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
            this.startActivity(alarmIntent);
        }else {
            Intent alarmIntent = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
            alarmIntent.putExtra(AlarmClock.ALARM_SEARCH_MODE_LABEL,content);
            this.startActivity(alarmIntent);
        }
    }
}
