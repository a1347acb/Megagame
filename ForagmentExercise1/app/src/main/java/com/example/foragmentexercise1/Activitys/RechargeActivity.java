package com.example.foragmentexercise1.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragmentexercise1.Adapters.oneListAdapter;
import com.example.foragmentexercise1.Datas.oneListData;
import com.example.foragmentexercise1.Datas.recordData;
import com.example.foragmentexercise1.Interfaces.RechargeInterfaces;
import com.example.foragmentexercise1.R;
import com.example.foragmentexercise1.Tools.OkHttpFz;
import com.example.foragmentexercise1.Tools.SqlistFz;
import com.example.foragmentexercise1.Tools.Tools;
import com.example.foragmentexercise1.Tools.Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 账户管理
 */
public class RechargeActivity extends BaseActivity implements RechargeInterfaces {
    private TextView batchRecharge;//批量充值
    private TextView RechargeRecord;//充值记录
    private AlertDialog dialog;//请求时屏蔽操作
    private DrawerLayout drawerLayout;//左拉菜单
    private ProgressDialog hintDialog;//充值窗口弹窗对象
    private ListView list;//信息显示List列表
    private String UserName;//用户名
    private List<recordData> recordMessage = new ArrayList<>();
    private oneListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        bd();
        setNavigationBar(true,"账户管理",false);
        LeftMenuShow(drawerLayout);

        mAsyncTask task = new mAsyncTask();
        task.execute();
    }
    private void bd(){
        list = findViewById(R.id.uidManageBalanceShow);
        drawerLayout = findViewById(R.id.leftMenu);
        batchRecharge = findViewById(R.id.batchRecharge);
        batchRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plateNumber(adapter.getPlateNumberList());
            }
        });
        RechargeRecord = findViewById(R.id.RechargeRecord);
        RechargeRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //充值记录逻辑代码
                startActivity(new Intent(RechargeActivity.this,rechargeRecord.class));//打开充值记录界面
            }
        });
        hintDialog = new ProgressDialog(this);
        hintDialog.setMessage("加载中...");
    }

    //回调方法，回调车牌信息
    @Override
    public void plateNumber(final Map<Integer,String> plateNumber) {
        View mView = LayoutInflater.from(RechargeActivity.this).inflate(R.layout.recharge_view,null,false);
        TextView plate = mView.findViewById(R.id.plateNumber);
        for(String values : plateNumber.values()){
            plate.append(values+"\r\t");//将车牌信息附加到文本控件中
        }
        final EditText editMoney = mView.findViewById(R.id.rechargeFormMoney);
        Button recharge = mView.findViewById(R.id.rechargeFormRecharge);
        //充值按钮点击事件
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double money = Double.valueOf(editMoney.getText().toString());//获取输入的充值金额
                for(Integer values : plateNumber.keySet()){
                    rechargeAsyncTask tase = new rechargeAsyncTask();
                    tase.execute(values,money,UserName);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date = new Date();
                    Tools.Log("recordMessage",format.format(date));
                    recordMessage.add(new recordData(plateNumber.get(values),money,format.format(date)));
                }
            }
        });
        Button cancel = mView.findViewById(R.id.rechargeFormCancel);
        //取消按点击事件
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();//关闭充值窗口
            }
        });
        dialog = new AlertDialog.Builder(RechargeActivity.this)
                .setView(mView)
                .show();//打开充值窗口
    }
    //获取账户信息
    class mAsyncTask extends AsyncTask<Void,Void,List<oneListData>>{
        @Override
        protected List<oneListData> doInBackground(Void... voids) {
            List<oneListData> data = new ArrayList<>();
            SharedPreferences preferences = getSharedPreferences(Values.SharedPreferencesName,0);
            UserName = preferences.getString(Values.S_UidName,"");
            OkHttpFz fz = new OkHttpFz(Values.API + "/GetCarInfo.do");
            fz.setParameter(String.format("{\"UserName\":\"%s\"}",preferences.getString("UserUid","")));
            try {
                JSONObject object = new JSONObject(fz.Fz());
                //判断是否请求到数据，如果没有请求到数据则返回空对象
                if(!object.getString("ERRMSG").equals("成功")){
                    return data;
                }
                JSONArray  array = object.getJSONArray("ROWS_DETAIL");
                for(int i=1;i<=object.length();i++){
                    Tools.Log("dataSize",String.valueOf(data.size()));
                    JSONObject object1 = array.getJSONObject(i);
                    String carnumber = object1.getString("carnumber");//车牌
                    String carbrand = object1.getString("carbrand");//小车品牌
                    int carImage;
                    if(carbrand.equals("baoma"))
                        carImage = getResources().getIdentifier("car3","drawable",RechargeActivity.this.getPackageName());
                    else if(carbrand.equals("jili"))
                        carImage = getResources().getIdentifier("car1","drawable",RechargeActivity.this.getPackageName());
                    else if(carbrand.equals("bengchi"))
                        carImage = getResources().getIdentifier("car4","drawable",RechargeActivity.this.getPackageName());
                    else if(carbrand.equals("dazhon"))
                        carImage = getResources().getIdentifier("car2","drawable",RechargeActivity.this.getPackageName());
                    else
                        carImage = getResources().getIdentifier("car1","drawable",RechargeActivity.this.getPackageName());//设置默认图片
                    String pcardid = object1.getString("pcardid");//身份证号
                    String userName = "";//车主姓名
                    int money;//余额

                    OkHttpFz fz1 = new OkHttpFz(Values.API + "/GetSUserInfo.do");
                    fz1.setParameter("{\"UserName\":\""+UserName+"\"}");
                    JSONObject object2 = new JSONObject(fz1.Fz());
                    if(object2.getString("ERRMSG").equals("成功")){
                        JSONArray array1 = object2.getJSONArray("ROWS_DETAIL");
                        for(int j=0;j<array1.length();j++){
                            JSONObject object3 = array1.getJSONObject(j);
                            Tools.Log("pcardid",pcardid);
                            Tools.Log("pcardid2",object3.getString("pcardid"));
                            if(object3.getString("pcardid").equals(pcardid)){
                                userName = object3.getString("pname");
                                break;
                            }
                        }
                    }else{
                        userName = "";
                    }
                    OkHttpFz fz2 = new OkHttpFz(Values.API + "/GetCarAccountBalance.do");
                    fz2.setParameter("{\"CarId\":"+i+",\"UserName\":\""+UserName+"\"}");
                    JSONObject object3 = new JSONObject(fz2.Fz());
                    if(object3.getString("ERRMSG").equals("成功")){
                        money = object3.getInt("Balance");
                    }else{
                        money = 0;
                    }
                    data.add(new oneListData(i,carImage,carnumber,userName,money));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hintDialog.show();
        }

        @Override
        protected void onPostExecute(List<oneListData> carMessageData) {
            super.onPostExecute(carMessageData);
            list.setAdapter(adapter = new oneListAdapter(RechargeActivity.this,carMessageData,RechargeActivity.this));
            hintDialog.dismiss();
        }
    }
    //进行指定账户余额充值
    class rechargeAsyncTask extends AsyncTask<Object,Void,Boolean>{
        /**
         * 接收三个参数，第一个是小车编号，第二个是充值金额，第三个是用户名
         */
        @Override
        protected Boolean doInBackground(Object... objects) {
            OkHttpFz fz = new OkHttpFz(Values.API + "/SetCarAccountRecharge.do");
            fz.setParameter(String.format("{\"CarId\":%s,\"Money\":%s,\"UserName\":\"%s\"}",objects[0],objects[1],objects[2]));
            try {
                JSONObject object = new JSONObject(fz.Fz());
                //判断是否充值成功
                if(object.getString("RESULT").equals("S") && object.getString("ERRMSG").equals("成功")){
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hintDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            hintDialog.dismiss();
            //刷新数据
            if(aBoolean){
                dialog.dismiss();
                Toast.makeText(RechargeActivity.this,"充值成功！",Toast.LENGTH_SHORT).show();
                for(recordData data : recordMessage){
                    sqlAdd(data.getPlateNumber(),data.getMoney(),data.getDateTime());
                }
                mAsyncTask task = new mAsyncTask();
                task.execute();
            }else{
                Toast.makeText(RechargeActivity.this,"充值失败！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 向Sqlist数据库添加充值记录信息
     * @param plateNumber 车牌信息
     * @param money 充值金额信息
     * @param dateTime 充值时间信息
     */
    private void sqlAdd(String plateNumber,double money,String dateTime){
        String sql = String.format("insert into %s(plateNumber,money,dateTime) values('%s',%s,'%s')",Values.recordTableName,plateNumber,money,dateTime);
        Tools.Log("SQL",sql);
        SqlistFz fz = new SqlistFz(RechargeActivity.this,Values.SqlistName,1);
        fz.Operation(sql);
    }
}
