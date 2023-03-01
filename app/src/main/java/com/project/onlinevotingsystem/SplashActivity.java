package com.project.onlinevotingsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable runnable = () -> {
        Intent ab = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(ab);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView = findViewById(R.id.logo);
        AnimationSet animationSet = new AnimationSet(true);
        Thread animation = new Thread(new Runnable() {
            @Override
            public void run() {


                // Reduce the size of the image
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(1000);
                scaleAnimation.setStartOffset(100);
                animationSet.addAnimation(scaleAnimation);

                // Move the image to a specific position

            }
        });
        Thread animation2 = new Thread(new Runnable() {
            @Override
            public void run() {
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -0.5f);
                translateAnimation.setDuration(1000);
                translateAnimation.setStartOffset(1500);
                animationSet.addAnimation(translateAnimation);
                imageView.startAnimation(animationSet);
            }
        });

        Thread postdelay = new Thread(() -> handler.postDelayed(runnable,1900));

        animation.start();
        animation2.start();
        postdelay.start();

    }
}