package com.example.hp.mycampus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hp.mycampus.R;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private ImageButton select_course;
    private ImageButton select_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置课表查询按钮的监听
        select_course=(ImageButton) findViewById(R.id.imageButton21);
        select_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(intent);
            }

        });

        //设置成绩查询按钮的监听
        select_score=(ImageButton) findViewById(R.id.imageButton20);
        select_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转
                Intent intent = new Intent(MainActivity.this, CourseActivity.class);
                startActivity(intent);
            }
        });
    }
}


