//输入日期    获取当前日期    计算差值    显示差值

package com.example.hp.mycampus.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.mycampus.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class countDown extends Activity {
    private EditText mEditText;
    private EditText mEditText1;
    private Button update;






    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);

        mEditText1 = (EditText) findViewById(R.id.matter1);
        mEditText = (EditText) findViewById(R.id.time1);

        //选择日期
        mEditText.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });

        //按钮更新倒计时天数
        update=(Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    String endTime = getEndTime();
                    Date date1 = format.parse(nowTime());
                    Date date2 = format.parse(endTime);
                    int days= differentDaysByMillisecond(date1,date2);

                    Toast.makeText(countDown.this,"还剩"+days+"天", Toast.LENGTH_LONG).show();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });


        //恢复文本
        String matter1 = load();
        String time1 = load2();
        if(!TextUtils.isEmpty(matter1)){
            mEditText1.setText(matter1);
            mEditText1.setSelection(matter1.length());
            mEditText.setText(time1);
            mEditText.setSelection(time1.length());
        }
    }
    //重写onDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText1 = mEditText.getText().toString();
        String inputText2 = mEditText1.getText().toString();
        save2(inputText1);
        save(inputText2);
    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(countDown.this, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                countDown.this.mEditText.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }



//    public static String GetDeleteShort(String str){
//            String s = str.replace("-", "");
//            return s;
//    }

    //获取系统当前日期
    public String nowTime(){

//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        String date=sdf.format(new java.util.Date());
//        return date;
        Date date = new Date();

        String year = String.format("%tY", date);

        String month = String.format("%tB", date);

        String day = String.format("%te", date);

        //Boolean a = month.equals("July");
        if (month.equals("July")){
            month="07";
            return year+"-"+month+"-"+day;
        }else return "qiao ni ma ";
    }


    //计算相隔天数
    public static int differentDaysByMillisecond(Date date1,Date date2){
        /*
         * 通过getTime方法可以将一个日期类型转换为long类型的毫秒值
         */
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }


    //获取框内日期字符串
    private String getEndTime(){
        EditText EditText=(EditText)findViewById(R.id.time1);
        String endTime=EditText.getText().toString();
        return endTime;
    }

    //将输入内容存储的方法
    public void save(String inputText){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("data",Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void save2(String inputText){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("data2",Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //恢复文本的方法
    public String load(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine()) != null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(reader != null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return content.toString();
    }
    public String load2(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("data2");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine()) != null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(reader != null){
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return content.toString();
    }



}
