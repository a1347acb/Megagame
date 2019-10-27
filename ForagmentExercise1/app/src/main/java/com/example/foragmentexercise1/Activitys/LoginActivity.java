package com.example.foragmentexercise1.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import com.example.foragmentexercise1.R;
import com.example.foragmentexercise1.Tools.OkHttpFz;
import com.example.foragmentexercise1.Tools.Tools;
import com.example.foragmentexercise1.Tools.Values;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {
    private TextView LoginUid;//账号输入框
    private TextView LoginPwd;//密码输入框
    private Button Login;//登录按钮
    private Button register;//注册按钮
    private CheckBox remember;//记住密码复选框
    private CheckBox automaticRegister;//自动登录复选框
    private ProgressDialog dialog;//登录请求时的加载提示控件
    private String URL = Values.API + "/user_login.do";//账号登录数据请求地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);
        dialog.setMessage("请求中...");
        dialog.setCancelable(false);

        bd();//控件绑定、事件关联

        SharedPreferences preferences = getSharedPreferences("UserMessage",0);
        LoginUid.setText(preferences.getString("UserUid",""));//通过已保存的账号信息初始化账号输入框内容
        LoginPwd.setText(preferences.getString("UserPwd",""));//通过已保存的密码信息初始化密码输入框内容
        remember.setChecked(preferences.getBoolean("RememberIsCheck",false));//通过已保存的记住密码复选框选中状态初始化记住密码复选框选中状态
        automaticRegister.setChecked(preferences.getBoolean("automaticRegister",false));//通过保存的自动登录选项框状态，恢复历史选中状态

        //通过判断自动登录复选框是否选中、保存密码复选框是否选中、密码输入框是否为空，如果满足条件，则直接执行登录请求
        if(automaticRegister.isChecked() && remember.isChecked() && !LoginUid.getText().toString().equals("") && !LoginUid.getText().toString().equals("")){
            new LoginSubmit().execute(URL,LoginUid.getText().toString(),LoginPwd.getText().toString());
        }
        setNavigationBar(false,"用户登录",true);
    }

    /**
     * 控件的绑定，事件的关联
     */
    private void bd(){
        remember = findViewById(R.id.remember);
        automaticRegister = findViewById(R.id.automaticRegister);
        LoginUid = findViewById(R.id.loginUid);
        LoginPwd = findViewById(R.id.loginPwd);
        Login = findViewById(R.id.login);
        //登录按钮点击事件
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用AsyncTask进行网络请求
                new LoginSubmit().execute(URL,LoginUid.getText().toString(),LoginPwd.getText().toString());
            }
        });
        register = findViewById(R.id.login_register);
        //注册按钮点击事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开注册界面
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    /**
     * 进行网络请求的AsyncTask
     */
    class LoginSubmit extends AsyncTask<String,Void,Boolean>{
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();
            if(aBoolean){
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getSharedPreferences(Values.SharedPreferencesName,MODE_PRIVATE);
                //存储账号信息
                preferences.edit().putString(Values.S_UidName,LoginUid.getText().toString()).apply();
                //判断记住密码复选框是否选中
                if(remember.isChecked()){
                    //保存密码信息和复选框选中信息
                    preferences.edit().putString(Values.S_UidPwdName,LoginPwd.getText().toString()).apply();
                    preferences.edit().putBoolean(Values.S_RememberIsCheck,true).apply();
                }else{
                    //判断是否有已保存的密码信息，如果有，则删除
                    if(preferences.getString(Values.S_UidPwdName,null) != null){
                        preferences.edit().remove(Values.S_UidPwdName).apply();
                    }
                    //更改保存密码复选框的选中信息
                    preferences.edit().putBoolean(Values.S_RememberIsCheck,false).apply();
                }
                if(automaticRegister.isChecked()){
                    preferences.edit().putBoolean("automaticRegister",true).apply();
                }else if(preferences.getBoolean("preferences",false)){
                    preferences.edit().remove("preferences").apply();
                }
                startActivity(new Intent(LoginActivity.this,RechargeActivity.class));
                //将登录的用户名保存到Values中
                Values.UserUid = LoginUid.getText().toString();
                finish();
            }else{
                Toast.makeText(LoginActivity.this,"登录失败！",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!Tools.NetWork(LoginActivity.this)){
                Toast.makeText(LoginActivity.this,"未检测到网络",Toast.LENGTH_SHORT).show();
                return false;
            }
            Tools.Log("LoginSubmitParams",strings[0]);
            boolean requestResult = false;

            //登录网络请求时的参数
            String params = String.format("{\"UserName\":\"%s\",\"UserPwd\":\"%s\"}",strings[1],strings[2]);
            OkHttpFz fz = new OkHttpFz(strings[0]);
            fz.setParameter(params);
            String data = fz.Fz();
            try {
                JSONObject jsonObject = new JSONObject(data);
                String result = jsonObject.getString("RESULT");
                Tools.Log("LoginSubmitParams",result);
                if(result.equals("S")){
                    requestResult = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return requestResult;
        }
    }
}
