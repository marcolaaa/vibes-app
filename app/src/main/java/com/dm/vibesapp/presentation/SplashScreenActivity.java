package com.dm.vibesapp.presentation;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.dm.vibesapp.R;
import com.dm.vibesapp.presentation.quotes.QuotesActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(QuotesActivity.createInstance(getApplicationContext()));
            }
        }, 2000);
    }

}
