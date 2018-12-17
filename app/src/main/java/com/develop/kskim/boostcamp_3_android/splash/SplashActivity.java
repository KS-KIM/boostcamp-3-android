package com.develop.kskim.boostcamp_3_android.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.develop.kskim.boostcamp_3_android.R;
import com.develop.kskim.boostcamp_3_android.search.SearchActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_TIME = 300;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent intent = new Intent(SplashActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, SPLASH_DISPLAY_TIME);
    }

}
