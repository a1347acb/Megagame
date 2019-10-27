package com.example.foragmentexercise1.Tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.foragmentexercise1.Datas.recordData;

import java.util.ArrayList;
import java.util.List;

public class SqlistFz extends SQLiteOpenHelper {
    public SqlistFz(@Nullable Context context, @Nullable String name,int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建充值记录信息表
        sqLiteDatabase.execSQL(String.format("create table %s (recordNumber int identity(1,1) primary key," +
                "plateNumber varchar(20)," +
                "money decimal(10,1)," +
                "dateTime varchar(20))",Values.recordTableName));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * 从Sqlist查询充值记录信息
     * @param sql 查询语句
     * @return 返回查询数据，以List<recordData>对象的形式
     */
    public List<recordData> RecordMessageSelect(String sql){
        List<recordData> itemData = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor data = database.rawQuery(sql,null);
        while(data.moveToNext()){
            String plateNumber = data.getString(1);
            double money = data.getInt(2);
            String dateTime = data.getString(3);
            itemData.add(new recordData(plateNumber,money,dateTime));
        }
        data.close();//关闭数据流
        return itemData;
    }

    /**
     * 执行数据Sqlist数据操作
     * @param sql sql语句
     */
    public void Operation(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);//执行数据操作
    }
}
