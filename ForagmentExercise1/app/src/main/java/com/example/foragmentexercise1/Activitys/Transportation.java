package com.example.foragmentexercise1.Activitys;

import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.foragmentexercise1.Adapters.DialogMessageItemAdapter;
import com.example.foragmentexercise1.Adapters.TransportationAdapter;
import com.example.foragmentexercise1.Datas.TransportationData;
import com.example.foragmentexercise1.Interfaces.TransportationInterface;
import com.example.foragmentexercise1.R;
import com.example.foragmentexercise1.Tools.OkHttpFz;
import com.example.foragmentexercise1.Tools.Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 公交查询界面
 */
public class Transportation extends BaseActivity implements TransportationInterface {
    private Dialog dialog;
    private DrawerLayout drawer;
    private ListView busMessage;
    private ListView mansion;
    private TransportationAdapter OneTransportationAdapter = null;
    private TransportationAdapter TwoTransportationAdapter = null;
    private carAsyncTask task;
    private Button particulars;
    private ListView messageList;
    private DialogMessageItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);

        bd();

        setNavigationBar(true,"公交查询",false);
        LeftMenuShow(drawer);

        task = new carAsyncTask();
        task.execute(1,2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(task != null && task.getStatus() == AsyncTask.Status.RUNNING){
            task.cancel(true);
        }
    }

    private void bd(){
        drawer = findViewById(R.id.leftMenu);
        busMessage = findViewById(R.id.hospital);
        mansion = findViewById(R.id.mansion);
        particulars = findViewById(R.id.particulars);
        particulars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(Transportation.this);
                View mView = LayoutInflater.from(Transportation.this).inflate(R.layout.transportation_particulars_from,null,false);
                messageList = mView.findViewById(R.id.BusMessageList);
                dialog.setContentView(mView);
                dialog.show();
            }
        });
    }

    @Override
    public void itemShow(String subName) {
        switch(subName){
            case "中医院站":
                busMessage.setAdapter(OneTransportationAdapter);
                break;
            case "联想大厦站":
                mansion.setAdapter(TwoTransportationAdapter);
                break;
        }
    }
    //请求 中医院站、联想大厦站的基本信息
    class carAsyncTask extends AsyncTask<Integer,List<TransportationData>,Void>{
        //请求到数据后更新ListView控件的Adapter
        @Override
        protected void onProgressUpdate(List<TransportationData>... values) {
            super.onProgressUpdate(values);
            if(OneTransportationAdapter == null){
                OneTransportationAdapter = new TransportationAdapter(Transportation.this,"中医院站",values[0]);
            }else{
                OneTransportationAdapter.setData(values[0]);
            }
            busMessage.setAdapter(OneTransportationAdapter);
            if(TwoTransportationAdapter == null){
                TwoTransportationAdapter = new TransportationAdapter(Transportation.this,"联想大厦站",values[1]);
            }else{
                TwoTransportationAdapter.setData(values[1]);
            }
            mansion.setAdapter(TwoTransportationAdapter);
        }

        //异步请求数据（两个数据Adapter的数据同时请求）
        @Override
        protected Void doInBackground(Integer... integers) {
            List<TransportationData> data;
            List<TransportationData> data1;
            while(!isCancelled()){
                OkHttpFz fz = new OkHttpFz(Values.API + "/GetBusStationInfo.do");
                fz.setParameter("{\"UserName\":\""+Values.UserUid+"\",\"BusStationId\":"+integers[0]+"}");
                data = JsonJie(fz.Fz());
                OkHttpFz fz1 = new OkHttpFz(Values.API + "/GetBusStationInfo.do");
                fz1.setParameter("{\"UserName\":\""+Values.UserUid+"\",\"BusStationId\":"+integers[1]+"}");
                data1 = JsonJie(fz1.Fz());
                //查询站台信息
                publishProgress(data,data1);//提交请求到的数据到onProgressUpdate方法中
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        //数据请求、数据解析、打包的方法
        public List<TransportationData> JsonJie(String values){
            List<TransportationData> data = new ArrayList<>();
            try {
                JSONObject GetBusStationInfo = new JSONObject(values);
                if(!GetBusStationInfo.getString("ERRMSG").equals("成功") && GetBusStationInfo.getString("RESULT").equals("S")){
                    Toast.makeText(Transportation.this,"站台信息查询失败！",Toast.LENGTH_SHORT).show();
                    return null;
                }
                JSONArray GetBusStationArray = GetBusStationInfo.getJSONArray("ROWS_DETAIL");
                //遍历请求1路车和2路车的基本信息
                for(int i=0;i<GetBusStationArray.length();i++){
                    JSONObject GetBustationMessage = GetBusStationArray.getJSONObject(i);
                    //获取公交车编号
                    int BusId = GetBustationMessage.getInt("BusId");
                    //获取距离车站的米数
                    int Distance = GetBustationMessage.getInt("Distance");
                    int minute = Distance/(20000/60);//计算出需要多少分钟才能到达站点
                    int numberPeopler;//保存当前公交车承载人数

                    OkHttpFz fz1 = new OkHttpFz(Values.API + "/GetBusCapacity.do");
                    fz1.setParameter("{\"UserName\":\""+Values.UserUid+"\",\"BusId\":\""+BusId+"\"}");
                    JSONObject GetBusCapacity = new JSONObject(fz1.Fz());
                    if(!GetBusCapacity.getString("ERRMSG").equals("成功") && GetBusCapacity.getString("RESULT").equals("S")){
                        Toast.makeText(Transportation.this,"承载人数信息获取失败！",Toast.LENGTH_SHORT).show();
                        return null;
                    }
                    numberPeopler = GetBusCapacity.getInt("BusCapacity");
                    data.add(new TransportationData(BusId,numberPeopler,minute,Distance));
                }
                return data;
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Transportation.this,"数据解析失败！"+e.toString(),Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }
}
