package com.example.foragmentexercise1.Activitys;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.foragmentexercise1.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 导航页面
 */
public class GuidanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);

        ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.hide();
        }

        //定时2秒，自动跳转到登录界面
        new Timer().schedule(
                new TimerTask(){
                    @Override
                    public void run() {
                        Intent intent = new Intent(GuidanceActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();//销毁当前界面
                    }
                }
        ,2000);
    }
}
