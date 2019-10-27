package com.example.foragmentexercise1.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.foragmentexercise1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 父类Activity
 */
public class BaseActivity extends AppCompatActivity {
    private ImageView navigationBarLeftImageTitle;//导航栏左端菜单栏弹出按钮
    private TextView navigationBarCenterTextTitle;//导航栏中间文字标题
    private LinearLayout navigationBarRightTitle;//导航栏右端网路设置
    private ListView view;//左侧滑动菜单菜单内容展示控件
    protected void setNavigationBar(boolean leftImageTitle, String centerTextTitle, boolean rightTitle){
        getSupportActionBar().hide();//隐藏系统默认导航栏

        bd();//绑定控件和事件的方法
        navigationBarLeftImageTitle.setVisibility(leftImageTitle ? View.VISIBLE : View.GONE);
        navigationBarCenterTextTitle.setText(centerTextTitle);
        navigationBarRightTitle.setVisibility(rightTitle ? View.VISIBLE : View.GONE);
    }

    /**
     * 左边滑动菜单
     * @param drawer 滑动菜单对象
     */
    public void LeftMenuShow(final DrawerLayout drawer){
        drawer.setScrimColor(Color.TRANSPARENT);//去除侧滑时的阴影

        List<String> menuItems = new ArrayList<>();
        menuItems.add("账户管理");
        menuItems.add("公交查询");
        menuItems.add("环境指标");
        view.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,menuItems));
        navigationBarLeftImageTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        //如果点击了第一项，则跳转到账户管理界面
                        startActivity(new Intent(BaseActivity.this,RechargeActivity.class));
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 1:
                        startActivity(new Intent(BaseActivity.this,Transportation.class));
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 2:
                        startActivity(new Intent(BaseActivity.this,Environment.class));
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 控件绑定
     */
    private void bd(){
        navigationBarLeftImageTitle = findViewById(R.id.navigationBarLeftImageTitle);
        navigationBarCenterTextTitle = findViewById(R.id.navigationBarCenterTextTitle);
        navigationBarRightTitle = findViewById(R.id.navigationBarRightTitle);
        view = findViewById(R.id.Menu);
    }
}
