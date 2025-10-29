package com.halata.blueapp;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.ScrollView;
import com.airbnb.lottie.LottieAnimationView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    // Set the duration of the splash screen in milliseconds
    private static final int SPLASH_DURATION = 6000; // 3 seconds
    private ScrollView svMain;
    private LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width  = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        svMain = findViewById(R.id.scView);
        svMain.setMinimumHeight(height - 1);

        int[] colors = {0xFFFF0550, 0xFF0110FF}; // Red to Blue
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);
        lottieAnimationView = findViewById(R.id.lottieView);
        lottieAnimationView.playAnimation();



        svMain.setBackground(gd);


        // Use a Handler to post a delayed action for transitioning to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the splash screen duration
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, SPLASH_DURATION);
    }
}