package com.sdbi.a2236140201.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.sdbi.a2236140201.R;
import com.sdbi.a2236140201.utils.IntroductoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class IntroductoryActivity extends AppCompatActivity {
    private ViewPager mViewPage;
    private List<View> viewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        initView();
        initAdapter();
        initStart();
    }

    /**
     * 设置第3个引导页的textView文本的点击事件
     */
    private void initStart() {
        Button mButton = viewList.get(2).findViewById(R.id.btn_open);
        mButton.setOnClickListener(view -> {
            startActivity(new Intent(IntroductoryActivity.this, MainActivity.class));
            IntroductoryActivity.this.finish();
        });
    }

    /**
     * 适配器
     */
    private void initAdapter() {
        IntroductoryAdapter adapter = new IntroductoryAdapter(viewList);
        mViewPage.setAdapter(adapter);
    }

    /**
     * viewPager和3个引导
     */
    private void initView() {
        mViewPage = findViewById(R.id.introductory_viewPager);
        viewList = new ArrayList<>();
        viewList.add(getView(R.layout.introductory_a));
        viewList.add(getView(R.layout.introductory_b));
        viewList.add(getView(R.layout.introductory_c));
    }

    private View getView(int resId) {
        return LayoutInflater.from(this).inflate(resId, null);
    }
}