package com.sdbi.a2236140201.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sdbi.a2236140201.domain.User;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Test.db";
    public static final String TABLE_NAME = "userinfo";
    public static final String COLUMN_USERID = "uid";
    public static final String COLUMN_USERPWD = "upwd";

    //创建数据库语句
    private static final String CREATE_TABLE = "create table if not exists "
            + TABLE_NAME + "(" + COLUMN_USERID + " text not null primary key,"
            + COLUMN_USERPWD + " text not null)";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    // 创建数据库方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 重置数据库方法(先删表，再建表)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table  if exists " + TABLE_NAME);
        db.execSQL(CREATE_TABLE);
    }

    //登录方法
    public User userLogin(String userId, String userPwd) {
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        /*
           query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)方法各参数的含义：
           table：表名。相当于select语句from关键字后面的部分。如果是多表联合查询，可以用逗号将两个表名分开。
           columns：要查询出来的列名。相当于select语句select关键字后面的部分。
           selection：查询条件子句，相当于select语句where关键字后面的部分，在条件子句允许使用占位符“?”
           selectionArgs：对应于selection语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致，否则就会有异常。
           groupBy：相当于select语句group by关键字后面的部分
           having：相当于select语句having关键字后面的部分
           orderBy：相当于select语句order by关键字后面的部分，如：personId desc, age asc;
           limit：指定偏移量和获取的记录数，相当于select语句limit关键字后面的部分。
         */
        // 游标
        @SuppressLint("Recycle") Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_USERID, COLUMN_USERPWD},
                COLUMN_USERID + " =? and " + COLUMN_USERPWD + "=?",
                new String[]{userId, userPwd},
                null,
                null,
                null,
                null);
        if (cursor.getCount() > 0) {
            // 使用 moveToFirst() 定位第一行
            cursor.moveToFirst();
            user = new User();
            user.setUserId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERID)));
            user.setUserPwd(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERPWD)));
        }
        return user;
    }

    // 注册方法
    public long registerUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        // 存储键值对 使用HashMap的泛型形式来进行数据存储的类
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERID, user.getUserId());
        contentValues.put(COLUMN_USERPWD, user.getUserPwd());
        // long android.database.sqlite.SQLiteDatabase.insert(String table, String nullColumnHack, ContentValues values)
        /*
            table: 要插入数据的表的名称
            nullColumnHack：当values参数为空或者里面没有内容的时候，我们insert是会失败的（底层数据库不允许插入一个空行），
                            为了防止这种情况，我们要在这里指定一个列名，
                            到时候如果发现将要插入的行为空行时，就会将你指定的这个列名的值设为null，
                            然后再向数据库中插入。
            values:一个ContentValues对象，类似一个map.通过键值对的形式存储值。
         */
        return db.insert(TABLE_NAME, null, contentValues);

    }
}
