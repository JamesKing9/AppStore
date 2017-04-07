package com.cheng.appstore.vm;

import com.cheng.appstore.Constants;
import com.cheng.appstore.MyApplication;
import com.cheng.appstore.utils.FileUtils;
import com.cheng.appstore.utils.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by itheima.
 * 数据缓存流程处理类
 */
public class DataCacheProcess {
    // 从内存中获取数据
    // 从本地文件中获取数据
    // 从网络获取数据（专门的流程进行处理）
    // 数据缓存：内存数据缓存、文件数据缓存

    /**
     * 从本地
     * @param key
     * @return
     */
    public static String getDataFromLocal(String key){
        String json = DataCacheProcess.getDataFromMemory(key);
        if (json == null) {
            json = DataCacheProcess.getDataFromFile(key);
        }
        return json;
    }

    /**
     * 从内存中获取数据
     *
     * @param key
     * @return
     */
    public static String getDataFromMemory(String key) {
        return MyApplication.getProtocolCache().get(key);
    }

    /**
     * 从本地文件中获取数据
     * @param key
     * @return
     */
    public static String getDataFromFile(String key) {
        String filepath = FileUtils.getDir("json");
        File file = new File(filepath, key);
        String json = null;

        if (file.exists()) {
            // 如何判断文件是否过时？
            // 创建文件的时间，当前系统时间，获取两者时间差，与设置好的过时时间进行比对
            // 约定文件的存储格式及内容：
            // 文件的第一行存放系统当前时间
            // 文件的第二行存放Json字符串

            try {
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String timeStr = bufferedReader.readLine();// 系统当前时间
                long createTime = Long.valueOf(timeStr);
                long currentTime = System.currentTimeMillis();

                if ((currentTime - createTime) < Constants.DURATION) {
                    json = bufferedReader.readLine();
                    // 需要在内存存一份文件
                    cacheMemory(key,json);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return json;
    }

    /**
     * 缓存到内存
     * @param key
     * @param json
     */
    public static void cacheMemory(String key,String json){
        MyApplication.getProtocolCache().put(key, json);
    }

    /**
     * 缓存数据在文件中
     * @param key
     * @param json
     */
    public static void cacheFile(String key,String json){
        String filepath = FileUtils.getDir("json");
        File file=new File(filepath,key);
        try {
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(System.currentTimeMillis()+"");
            bufferedWriter.newLine();
            bufferedWriter.write(json);
            bufferedWriter.flush();
            IOUtils.close(bufferedWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
