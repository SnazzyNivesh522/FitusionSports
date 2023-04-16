package com.example.fitusionsports;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation splashanimation= AnimationUtils.loadAnimation(this,R.anim.splashanimation);
        ImageView splashimage=findViewById(R.id.splash_image);
        ProgressBar splashprogressbar=findViewById(R.id.splash_progress_bar);
        splashimage.startAnimation(splashanimation);
        new Handler().postDelayed(() -> {
            splashprogressbar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(SplashActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        },4000);
    }
}