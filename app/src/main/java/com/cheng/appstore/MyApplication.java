package com.cheng.appstore;

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
    // 缓存协议数据（json）（protocol,（数据传递的）协议）
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
        //Sugar 初始化相应的动作
        SugarContext.init(this);
    }

    /**
     * This method is for use in emulated process environments.  It will
     * never be called on a production Android device, where processes are
     * removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     */
    //Sugar 结束相应的动作
    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
