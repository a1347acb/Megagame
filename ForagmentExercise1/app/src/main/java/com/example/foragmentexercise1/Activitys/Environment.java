package com.example.foragmentexercise1.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.foragmentexercise1.R;
import com.example.foragmentexercise1.Tools.OkHttpFz;
import com.example.foragmentexercise1.Tools.Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 道路
 */
public class Environment extends BaseActivity {
    private FrameLayout temperature;//温度
    private TextView temperatureValue;//温度值
    private FrameLayout humidity;//湿度
    private TextView humidityValue;//湿度值
    private FrameLayout illumination;//光照
    private TextView illuminationValue;//光照值
    private FrameLayout CO2;//CO2
    private TextView CO2Value;//CO2值
    private FrameLayout PM2_5;//PM2.5
    private TextView PM2_5Value;//PM2.5值
    private FrameLayout state;//道路状况
    private TextView stateValue;//道路拥挤度
    private DrawerLayout drawer;
    private Timer timer;

    private Handler handler1 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    try {
                        JSONObject object = new JSONObject(msg.obj.toString());
                        if(object.getString("ERRMSG").equals("成功")){
                            if(object.getString("RESULT").equals("S")){
                                temperatureValue.setText(object.getString("temperature"));
                                if(object.getInt("temperature") < 25)
                                    temperature.setBackgroundColor(getResources().getColor(R.color.environment_Item_background_warn));
                                else
                                    temperature.setBackgroundColor(getResources().getColor(R.color.environment_Item_background));
                                humidityValue.setText(object.getString("humidity"));
                                if(object.getInt("humidity") < 30)
                                    humidity.setBackgroundColor(getResources().getColor(R.color.environment_Item_background_warn));
                                else
                                    humidity.setBackgroundColor(getResources().getColor(R.color.environment_Item_background));
                                illuminationValue.setText(object.getString("LightIntensity"));
                                if(object.getInt("LightIntensity") < 25)
                                    illumination.setBackgroundColor(getResources().getColor(R.color.environment_Item_background_warn));
                                else
                                    illumination.setBackgroundColor(getResources().getColor(R.color.environment_Item_background));
                                CO2Value.setText(object.getString("co2"));
                                if(object.getInt("co2") > 1000)
                                    CO2.setBackgroundColor(getResources().getColor(R.color.environment_Item_background_warn));
                                else
                                    CO2.setBackgroundColor(getResources().getColor(R.color.environment_Item_background));
                                PM2_5Value.setText(object.getString("pm2.5"));
                                if(object.getInt("pm2.5") > 200)
                                    PM2_5.setBackgroundColor(getResources().getColor(R.color.environment_Item_background_warn));
                                else
                                    PM2_5.setBackgroundColor(getResources().getColor(R.color.environment_Item_background));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case -1:
                    break;
                case 1:
                    try {
                        JSONObject object = new JSONObject(msg.obj.toString());
                        if(object.getString("ERRMSG").equals("成功")){
                            if(object.getString("RESULT").equals("S")){
                                stateValue.setText(object.getString("Status"));
                                if(object.getInt("Status") < 4)
                                    state.setBackgroundColor(getResources().getColor(R.color.environment_Item_background_warn));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);

        bd();//控件绑定、事件关联
        setNavigationBar(true,"环境指标",false);
        LeftMenuShow(drawer);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                NetWork();
            }
        },0,3000);

    }
    private void NetWork(){
        OkHttpFz fz1 = new OkHttpFz(Values.API + "/GetAllSense.do");
        String parameter1 = String.format("{\"UserName\":\"%s\"}",Values.UserUid);
        fz1.setParameter(parameter1);
        fz1.YiFz(handler1);
        OkHttpFz fz2 = new OkHttpFz(Values.API + "/GetRoadStatus.do");
        String parameter2 = String.format("{\"UserName\":\"%s\",\"RoadId\":%s}",Values.UserUid,1);
        fz2.setParameter(parameter2);
        fz2.YiFz(handler2);
    }
    private void bd(){
        temperature = findViewById(R.id.temperature);
        temperatureValue = findViewById(R.id.temperatureValue);
        humidity = findViewById(R.id.humidity);
        humidityValue = findViewById(R.id.humidityValue);
        illumination = findViewById(R.id.illumination);
        illuminationValue = findViewById(R.id.illuminationValue);
        CO2 = findViewById(R.id.CO2);
        CO2Value = findViewById(R.id.CO2Value);
        PM2_5 = findViewById(R.id.PM2_5);
        PM2_5Value = findViewById(R.id.PM2_5Value);
        state = findViewById(R.id.State);
        stateValue = findViewById(R.id.StateValue);

        drawer = findViewById(R.id.drawerLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();//关闭定时器
    }
}
