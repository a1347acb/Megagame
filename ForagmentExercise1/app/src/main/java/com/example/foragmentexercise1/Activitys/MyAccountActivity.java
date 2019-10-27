package com.example.foragmentexercise1.Activitys;

import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.foragmentexercise1.R;

//我的信息界面
public class MyAccountActivity extends BaseActivity {
    private DrawerLayout drawer;
    private TextView yMoney;
    private TextView rMoney;
    private Spinner plateNumber;
    private Button Select;
    private Button recharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        bd();
        //加载头部导航栏
        setNavigationBar(true,"个人账户",false);
        //加载左侧滑动菜单
        LeftMenuShow(drawer);
    }
    private void bd(){
        drawer = findViewById(R.id.drawerLayout);
        yMoney = findViewById(R.id.yMoney);
        rMoney = findViewById(R.id.rMoney);
        plateNumber = findViewById(R.id.plateNumber);
        Select = findViewById(R.id.select);
        recharge = findViewById(R.id.recharge);
    }
}
