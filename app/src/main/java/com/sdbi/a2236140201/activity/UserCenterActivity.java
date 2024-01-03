package com.sdbi.a2236140201.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.sdbi.a2236140201.R;
import com.sdbi.a2236140201.fragment.HomeFragment;
import com.sdbi.a2236140201.fragment.MeFragment;
import com.sdbi.a2236140201.fragment.ToFragment;

public class UserCenterActivity extends AppCompatActivity {

    // 创建3个 Fragment
    private HomeFragment homeFragment;
    private ToFragment toFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        // 初始化控件
        BottomNavigationView navigationView = findViewById(R.id.main_bottom_nv);
        // 默认选中首页
        selectedFragment(0);

        //main_bottom_nv tab点击切换
        navigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                selectedFragment(0);
            } else if (item.getItemId() == R.id.tong) {
                selectedFragment(1);
            } else if (item.getItemId() == R.id.me) {
                selectedFragment(2);
            }
            return true;
        });
    }

    private void selectedFragment(int position) {
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);

        // 首页
        if (position == 0) {
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
                transaction.add(R.id.content, homeFragment);
            } else {
                transaction.show(homeFragment);
            }
            // 统计
        } else if (position == 1) {
            if (toFragment == null) {
                toFragment = new ToFragment();
                transaction.add(R.id.content, toFragment);
            } else {
                transaction.show(toFragment);
            }
            // 我的
        } else {
            if (meFragment == null) {
                meFragment = new MeFragment();
                transaction.add(R.id.content, meFragment);
            } else {
                transaction.show(meFragment);
            }
        }
        //最后设置提交 注意：这句话一定不能少!!!
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (toFragment != null) {
            transaction.hide(toFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
    }
}
