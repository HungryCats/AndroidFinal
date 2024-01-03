package com.sdbi.a2236140201.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sdbi.a2236140201.R;

public class InitiateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate);
        TextView countDownText = findViewById(R.id.tv_count_down);
        new CountDownTimer(3000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                // 每秒更新一次UI，显示剩余时间
                // 比如显示倒计时效果
                int time = (int) millisUntilFinished;
                countDownText.setText(time / 1000 + " 跳过");
            }

            public void onFinish() {
                // 倒计时结束时跳转到主界面
                Intent intent = new Intent(InitiateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}