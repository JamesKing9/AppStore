package com.itheima.appstore;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.orm.SugarContext;

import java.util.HashMap;

/**
 * 自定义：Application
 * 记住：去manifest配置一下
 */
public class MyApplication extends Application {
    private static Context context;
    private static Handler handler;
    // 缓存协议数据（json）
    private static HashMap<String, String> protocolCache;
    private static Gson gson = new Gson();
    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static HashMap<String, String> getProtocolCache() {
        return protocolCache;
    }

    public static Gson getGson() {
        return gson;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        protocolCache = new HashMap<>();
        SugarContext.init(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
