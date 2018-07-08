package com.example.hp.mycampus.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.util.InfoUtil;

import java.util.List;

import androidx.core.app.ActivityCompat;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ((ImageView) findViewById(R.id.bg)).postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
//                                        }
            }
        }, 3000);
    }
}