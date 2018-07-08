package com.example.hp.mycampus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.model.Lesson;
import com.example.hp.mycampus.util.InfoUtil;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LoginActivity extends Activity {
    private String username;
    private String password;
    private String code;

    private int mode=0;//网络连接控制
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 0:
                    Toast.makeText(getApplicationContext(),"登陆失败!请检查用户名密码是否正确!",Toast.LENGTH_LONG).show();
                    break;

                case 1:
                    Toast.makeText(getApplicationContext(),"登陆成功!",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(),"教务系统连接失败，请更换网络或选择离线登入！",Toast.LENGTH_LONG).show();
                    break;
            }

        }
    };
    private EditText ed_username;
    private EditText ed_password;
    private EditText ed_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        InfoUtil.getSafeCode();

        String img_path = "data/data/com.example.hp.mycampus/safecode.png";
        Bitmap bmp= BitmapFactory.decodeFile(img_path);

        ImageView imageview=(ImageView) findViewById(R.id.imageView);
        imageview.setImageBitmap(bmp);
        imageview.setOnClickListener(new View.OnClickListener() {
            //设置登入事件
            @Override
            public void onClick(View v) {
                //刷新验证码
                InfoUtil.getSafeCode();
                System.out.println("刷新验证码成功");
                String img_path = "data/data/com.example.hp.mycampus/safecode.png";
                Bitmap bmp= BitmapFactory.decodeFile(img_path);

                ImageView imageview=(ImageView) findViewById(R.id.imageView);
                imageview.setImageBitmap(bmp);
            }
        });
        //找到登入按钮
        Button login = (Button) findViewById(R.id.login_button);
        //Button off_linelogin = (Button) findViewById(R.id.off_linelogin);
        ed_username = (EditText) findViewById(R.id.login_username);
        ed_password = (EditText) findViewById(R.id.login_password);
        ed_code = (EditText) findViewById(R.id.login_code);
        //取到保存的账号密码验证码
        SharedPreferences sp = getSharedPreferences("config", 0);
        String user = sp.getString("username", null);
        String pass = sp.getString("password", null);
        String code = sp.getString("code", null);
        ed_username.setText(user);
        ed_password.setText(pass);
        ed_code.setText(code);
        login.setOnClickListener(new View.OnClickListener() {
            //设置登入事件
            @Override
            public void onClick(View v) {
                //登入逻辑
                if (0==mode) {
                    Toast.makeText(getApplicationContext(), "玩命加载中...", Toast.LENGTH_SHORT).show();
                    mode=1;
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            //通过教务系统模拟登陆 测试账号密码是否正确
                            try {

                                //获取EditText中输入的信息
                                LoginActivity.this.username = ed_username.getText().toString().trim();
                                LoginActivity.this.password = ed_password.getText().toString().trim();
                                LoginActivity.this.code = ed_code.getText().toString().trim();
                                if (InfoUtil.Login(LoginActivity.this.username, LoginActivity.this.password, LoginActivity.this.code)) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    mode=0;
                                    startActivity(intent);
                                    ArrayList<Lesson> lessons = InfoUtil.getLessons();
                                    for (Lesson lesson : lessons)
                                        System.out.println(lesson);
                                    System.out.println("爬虫成功了！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
                                } else {
                                    System.out.println(InfoUtil.getReason());
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                //提示连接超时
                                Message msg = handler.obtainMessage();
                                msg.arg1 = 2;
                                handler.sendMessage(msg);
                                mode=0;
                            }
                        }
                    }.start();
                }


            }
        });
    }

}
