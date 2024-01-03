package com.sdbi.a2236140201.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sdbi.a2236140201.R;
import com.sdbi.a2236140201.domain.User;
import com.sdbi.a2236140201.utils.DBHelper;

//注册页面逻辑
public class RegisterActivity extends AppCompatActivity {

    private User user;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pref = getSharedPreferences("sex", MODE_PRIVATE);
        final EditText edit_id = findViewById(R.id.edit_id);
        final EditText edit_pwd = findViewById(R.id.edit_pwd);
        // 得到单选组
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        // 注册按键
        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(v1 -> {
            user = new User();
            user.setUserId(edit_id.getText().toString());
            user.setUserPwd(edit_pwd.getText().toString());

            DBHelper dbUserHelper = new DBHelper(getApplicationContext());
            if (dbUserHelper.registerUser(user) > 0) {
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) radioGroup.getChildAt(i);
                    if (rd.isChecked()) {
                        editor = pref.edit();
                        editor.putString("s", rd.getText().toString());
                        editor.apply();
                        break;
                    }
                }

                Intent intent;
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "您已经注册过此账户，请返回登录", Toast.LENGTH_SHORT).show();
            }
        });
    }
}