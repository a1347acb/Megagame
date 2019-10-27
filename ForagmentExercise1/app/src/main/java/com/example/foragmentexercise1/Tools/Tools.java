package com.example.foragmentexercise1.Tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 打印Log.e()级别的日志
 */
public class Tools {
    public static void Log(String name,String Value){
        Log.e(name,Value);
    }

    /**
     * 判断网络是否可用
     * @param context 上下文
     * @return 返回网络可用状态
     */
    public static boolean NetWork(Context context){
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
