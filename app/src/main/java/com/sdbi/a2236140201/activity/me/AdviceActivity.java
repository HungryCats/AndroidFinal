package com.sdbi.a2236140201.activity.me;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.sdbi.a2236140201.R;
import com.sdbi.a2236140201.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        int[] images = new int[]{R.mipmap.revenue, R.mipmap.look, R.mipmap.statics, R.mipmap.exit};
        String[] titles = new String[]{"消息", "已完成", "待处理", "问题反馈"};
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
                    // 收支查询
                case 1:
                    // 退出登录
                case 2:
                    // 退出APP
                case 3:
                    Toast.makeText(getApplicationContext(), "尽情期待", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}