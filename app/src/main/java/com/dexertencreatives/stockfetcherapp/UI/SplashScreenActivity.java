package com.dexertencreatives.stockfetcherapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;

import com.dexertencreatives.stockfetcherapp.R;


public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        View mContentView = findViewById(R.id.fullscreen_content);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, HomePageActivity.class));
            finish();
        }, 2000);
    }
}
