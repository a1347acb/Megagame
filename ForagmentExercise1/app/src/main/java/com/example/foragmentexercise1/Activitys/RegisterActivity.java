package com.example.foragmentexercise1.Activitys;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foragmentexercise1.R;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity {
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setNavigationBar(false,"注  册",false);
        bd();
    }
    private void bd(){
        cancel = findViewById(R.id.registerCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
