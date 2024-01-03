package com.sdbi.a2236140201.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sdbi.a2236140201.R;
import com.sdbi.a2236140201.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


//用户个人中心页面逻辑
public class UserCenterActivity extends AppCompatActivity {
    ArrayList<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        SharedPreferences sharedPreferences = getSharedPreferences("sex", Context.MODE_PRIVATE);
        String sex = sharedPreferences.getString("s", "");

        Intent intent = getIntent();
        list = intent.getParcelableArrayListExtra("LoginUser");
        // list != null
        User user = Objects.requireNonNull(list).get(0);
        final String username = user.getUserId();
        TextView tv_welcome = findViewById(R.id.tv_welcome);
        tv_welcome.setText(String.format("%s,%s", username, sex));

        int[] images = new int[]{R.mipmap.revenue, R.mipmap.look, R.mipmap.statics, R.mipmap.exit};
        String[] titles = new String[]{"收支管理", "查看收支", "退出登录", "用户信息"};
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", images[i]);
            map.put("title", titles[i]);
            mapList.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, mapList, R.layout.grid_item, new String[]{"image", "title"}, new int[]{R.id.btn_img, R.id.btn_title});
        GridView gridView = findViewById(R.id.grid_view);
        gridView.setAdapter(adapter);

        // gridview的item的点击事件
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                // 收支管理
                case 0:
                    Intent intent1 = new Intent(getApplicationContext(), ManageActivity.class);
                    startActivity(intent1);
                    break;
                // 收支查询
                case 1:
                    Intent intent2 = new Intent(getApplicationContext(), SearchRecordActivity.class);
                    startActivity(intent2);
                    break;
                // 退出登录
                case 2:
                    Intent intent12 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent12);
                    break;
                // 退出APP
                case 3:
                    Toast.makeText(getApplicationContext(), "尽情期待", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}
