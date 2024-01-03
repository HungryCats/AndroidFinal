package com.sdbi.a2236140201.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sdbi.a2236140201.R;
import com.sdbi.a2236140201.domain.User;
import com.sdbi.a2236140201.utils.DBHelper;

import java.util.ArrayList;

//登录页面逻辑
public class MainActivity extends AppCompatActivity {
    EditText edt_id, edt_pwd;

    private SharedPreferences.Editor editor;

    private CheckBox rememberPass;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("checked", MODE_PRIVATE);
        edt_id = findViewById(R.id.edt_uid);
        edt_pwd = findViewById(R.id.edt_upwd);
        rememberPass = findViewById(R.id.remember_pass);
        Button btn_login = findViewById(R.id.btn_login);

        boolean isRemember = pref.getBoolean("checked", false);
        if (isRemember) {
            //将账号和密码都设置到文本框中
            String userId = pref.getString("userId", "");
            String userPwd = pref.getString("userPwd", "");
            edt_id.setText(userId);
            edt_pwd.setText(userPwd);
            rememberPass.setChecked(true);
        }

        //登录按键
        btn_login.setOnClickListener(v -> {
            try {
                String userId = edt_id.getText().toString();
                String userPwd = edt_pwd.getText().toString();
                DBHelper userHelper = new DBHelper(getApplicationContext());
                User user = userHelper.userLogin(userId, userPwd);

                //登录成功跳转对应类型界面
                if (user != null) {
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("checked", true);
                        editor.putString("userId", userId);
                        editor.putString("userPwd", userPwd);
                    } else {
                        editor.apply();
                    }
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    ArrayList<User> list = new ArrayList<>();
                    list.add(user);

                    Intent intent = new Intent(getApplicationContext(), UserCenterActivity.class);
                    intent.putParcelableArrayListExtra("LoginUser", list);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "登录失败，密码错误或账号不存在！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "数据库异常", Toast.LENGTH_SHORT).show();
            }
        });
        //注册按键
        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
    }
}
