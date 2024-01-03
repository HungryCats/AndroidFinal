package com.sdbi.a2236140201.fragment;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.sdbi.a2236140201.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private View view;
    private ListView listView;

    private SQLiteDatabase sqLiteDatabase = null;
    private int selectId = -1;
    EditText edt_date, edt_type, edt_money, edt_state;

    private static final String DATABASE_NAME = "Test.db";
    private static final String TABLE_NAME = "record";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_MONEY = "money";
    private static final String COLUMN_STATE = "state";

    //创建表
    private static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME
            + "(" + COLUMN_ID + " integer primary key autoincrement," + COLUMN_DATE + " text," + COLUMN_TYPE
            + " text," + COLUMN_MONEY + " float," + COLUMN_STATE + " text)";

    //自定义的查询方法
    public void selectData() {
        //遍历整个表
        String sql = "select * from " + TABLE_NAME;
        //把查询数据封装到Cursor
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        ArrayList<Map<String, String>> list = new ArrayList<>();
        // 用while循环遍历Cursor，再把它分别放到map中，最后统一存入list中，便于调用
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
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                list,
                R.layout.record_item_layout,
                new String[]{"id", "date", "type", "money", "state"},
                new int[]{R.id.list_id, R.id.list_date, R.id.list_type, R.id.list_money, R.id.list_state});
        listView = view.findViewById(R.id.recordlistview);
        //绑定适配器
        listView.setAdapter(simpleAdapter);
        //设置ListView单击事件
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ListView tempList = (ListView) parent;

            View mView = tempList.getChildAt(position);
            TextView list_id = mView.findViewById(R.id.list_id);
            TextView list_date = mView.findViewById(R.id.list_date);
            TextView list_type = mView.findViewById(R.id.list_type);
            TextView list_money = mView.findViewById(R.id.list_money);
            TextView list_state = mView.findViewById(R.id.list_state);

            String rid = list_id.getText().toString();
            String date = list_date.getText().toString();
            String type = list_type.getText().toString();
            String money = list_money.getText().toString();
            String state = list_state.getText().toString();

            edt_date.setText(date);
            edt_type.setText(type);
            edt_money.setText(money);
            edt_state.setText(state);
            selectId = Integer.parseInt(rid);

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        try {
            sqLiteDatabase = requireActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
            sqLiteDatabase.execSQL(CREATE_TABLE);
            //执行查询
            selectData();
        } catch (SQLException e) {
            Toast.makeText(getActivity(), "数据库异常!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        edt_date = view.findViewById(R.id.edt_date);
        edt_type = view.findViewById(R.id.edt_type);
        edt_money = view.findViewById(R.id.edt_money);
        edt_state = view.findViewById(R.id.edt_state);

        //新增按键
        Button btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(v -> {
            if (edt_date.getText().toString().equals("") | edt_type.getText().toString().equals("") | edt_money.getText().toString().equals("") | edt_state.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "数据不能为空!", Toast.LENGTH_LONG).show();
                return;
            }

            String date = edt_date.getText().toString();
            String type = edt_type.getText().toString();
            String money = edt_money.getText().toString();
            String state = edt_state.getText().toString();
            //定义添加数据的sql语句
            String sql = "insert into " + TABLE_NAME + "(" + COLUMN_DATE + "," + COLUMN_TYPE + "," + COLUMN_MONEY + "," + COLUMN_STATE + ") " +
                    "values('" + date + "','" + type + "','" + money + "','" + state + "')";
            //执行sql语句
            sqLiteDatabase.execSQL(sql);
            Toast.makeText(getActivity(), "新增数据成功!", Toast.LENGTH_LONG).show();
            //刷新显示列表
            selectData();

            //消除数据
            edt_date.setText("");
            edt_type.setText("");
            edt_money.setText("");
            edt_state.setText("");
        });

        //修改按键
        ImageButton btn_update = view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(v -> {
            //无选择提示
            if (selectId == -1) {
                Toast.makeText(getActivity(), "请选择要修改的行!", Toast.LENGTH_LONG).show();
                return;
            }
            //判断是否有空数据
            if (edt_date.getText().toString().equals("") | edt_type.getText().toString().equals("") | edt_money.getText().toString().equals("") | edt_state.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "数据不能为空!", Toast.LENGTH_LONG).show();
                return;
            }

            String date = edt_date.getText().toString();
            String type = edt_type.getText().toString();
            String money = edt_money.getText().toString();
            String state = edt_state.getText().toString();
            //定义修改数据的sql语句
            String sql = "update " + TABLE_NAME + " set " + COLUMN_DATE + "='" + date + "',type='" + type + "',money='" + money + "',state='" + state + "' where id=" + selectId;
            //执行sql语句
            sqLiteDatabase.execSQL(sql);
            Toast.makeText(getActivity(), "修改数据成功!", Toast.LENGTH_LONG).show();
            //刷新显示列表
            selectData();
            selectId = -1;
            //消除数据
            edt_date.setText("");
            edt_type.setText("");
            edt_money.setText("");
            edt_state.setText("");
        });

        // 删除按键
        /*
           如果返回false那么onItemClick仍然会被调用。而且是先调用onItemLongClick，然后调用onItemClick。
           如果返回true那么click就会被吃掉，onItemClick就不会再被调用了。
        */
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            ListView tempList = (ListView) adapterView;

            View mView = tempList.getChildAt(i);
            TextView list_id = mView.findViewById(R.id.list_id);
            String rid = list_id.getText().toString();
            selectId = Integer.parseInt(rid);

            //定义删除对话框
            AlertDialog dialog = new AlertDialog.Builder(requireActivity()).setTitle("删除操作")
                    .setMessage("确定删除？此操作不可逆，请慎重选择！")
                    .setPositiveButton("确定", (dialog12, which) -> {
                        //定义删除的sql语句
                        String sql = "delete from " + TABLE_NAME + " where id=" + selectId;
                        //执行sql语句
                        sqLiteDatabase.execSQL(sql);
                        //刷新显示列表
                        Toast.makeText(getActivity(), "删除数据成功!", Toast.LENGTH_LONG).show();
                        selectData();
                        selectId = -1;
                        //清除数据
                        edt_date.setText("");
                        edt_type.setText("");
                        edt_money.setText("");
                        edt_state.setText("");
                    }).setNegativeButton("取消", (dialog1, which) -> {
                    }).create();
            dialog.show();
            return true;
        });

        return view;
    }
}