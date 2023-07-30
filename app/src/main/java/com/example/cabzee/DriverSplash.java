package com.example.cabzee;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class DriverSplash extends AppCompatActivity {
    LottieAnimationView laview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(DriverSplash.this,Driver_Registration.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                laview.setAnimation(R.raw.driver);
                laview.playAnimation();
                laview.setRepeatCount(ValueAnimator.INFINITE);
                finish();


            }
        },5000);
        getSupportActionBar();
        setContentView(R.layout.activity_driver_splash);
    }

}
