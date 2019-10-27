package com.example.foragmentexercise1.Tools;


import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpFz {
    private String url;
    private boolean isLoop;
    private int loopTime;
    private String parameter;
    public OkHttpFz(String URL){
        this.url = URL;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
        Tools.Log("OkHttpFz_38",parameter);
    }
    public void setLoop(boolean loop) {
        isLoop = loop;
    }

    public void setLoopTime(int loopTime) {
        this.loopTime = loopTime;
    }
    public String Fz(){
        OkHttpClient client = new OkHttpClient();
        Request request = null;
        Response response = null;
        try{
            URL URL = new URL(url);
            if(parameter != null){
                RequestBody body = FormBody.create(MediaType.parse("application/json"),new Gson().toJson(parameter));
                request = new Request.Builder()
                        .url(URL)
                        .post(body)
                        .build();
            }else{
                request = new Request.Builder()
                        .url(URL)
                        .build();
            }
            response = client.newCall(request).execute();
            return response.body().string();
        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * OkHttp异步请求代码封装
     */
    public void YiFz(final Handler handler){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),parameter);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message(-1,e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message(1,response.body().string());
            }

            /**
             * 添加消息到消息队列
             * @param what
             * @param obj
             */
            private void Message(int what,Object obj){
                Message message = Message.obtain();
                message.what = what;
                message.obj = obj;
                handler.sendMessage(message);
            }
        });
    }
}
