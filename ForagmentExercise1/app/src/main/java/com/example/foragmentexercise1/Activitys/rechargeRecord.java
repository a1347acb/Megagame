package com.example.foragmentexercise1.Activitys;


import android.os.Bundle;
import android.widget.ListView;

import com.example.foragmentexercise1.Adapters.RecordAdapter;
import com.example.foragmentexercise1.Datas.recordData;
import com.example.foragmentexercise1.R;
import com.example.foragmentexercise1.Tools.SqlistFz;
import com.example.foragmentexercise1.Tools.Values;

import java.util.List;

/**
 * 充值记录
 */
public class rechargeRecord extends BaseActivity {
    private ListView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_record);

        setNavigationBar(false,"充值记录",false);
        bd();//控件绑定、事件绑定

        SqlistFz fz = new SqlistFz(this, Values.SqlistName,1);
        List<recordData> data = fz.RecordMessageSelect("select * from "+Values.recordTableName);//从Sqlist数据库中查询所有充值记录信息;
        RecordAdapter adapter = new RecordAdapter(this,data);
        view.setAdapter(adapter);//ListView绑定数据源
    }
    private void bd(){
        view = findViewById(R.id.record);
    }
}
