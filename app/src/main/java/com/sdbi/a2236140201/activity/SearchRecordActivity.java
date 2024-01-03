package com.sdbi.a2236140201.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sdbi.a2236140201.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


//收支记录页面业务逻辑
public class SearchRecordActivity extends AppCompatActivity {

    ListView listView;
    //数据库
    private static final String DATABASE_NAME = "Test.db";
    private static final String TABLE_NAME = "record";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_MONEY = "money";
    private static final String COLUMN_STATE = "state";
    private SQLiteDatabase sqLiteDatabase = null;

    private void selectData() {
        //自定义查询的sql语句
        String sql;
        sql = "select * from " + TABLE_NAME;
        //将查询到的数据封装到Cursor
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        ArrayList<Map<String, String>> list = new ArrayList<>();
        if (cursor.getCount() == 0) {
            //查无数据则不显示列表
            Toast.makeText(getApplicationContext(), "无数据", Toast.LENGTH_SHORT).show();
        } else {
            //查有数据则显示列表
            listView.setVisibility(View.VISIBLE);
            while (cursor.moveToNext()) {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
                float money = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_MONEY));
                String state = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATE));
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(id));
                map.put("date", date);
                map.put("type", type);
                map.put("money", String.valueOf(money));
                map.put("state", state);
                list.add(map);
            }
            //创建SimpleAdapter
            SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(),
                    list,
                    R.layout.record_item_layout,
                    new String[]{"id", "date", "type", "money", "state"},
                    new int[]{R.id.list_id, R.id.list_date, R.id.list_type, R.id.list_money, R.id.list_state});
            listView.setAdapter(simpleAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_record);
        try {
            //打开数据库，如果是第一次会创建该数据库，模式为MODE_PRIVATE
            sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            //执行创建表的sql语句，虽然每次都调用，但只有首次才创建表
            //执行查询
            listView = findViewById(R.id.searchlistview);//绑定列表
            selectData();
        } catch (SQLException e) {
            Toast.makeText(this, "数据库异常!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}