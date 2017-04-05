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
 * 缓存读取数据的处理
 */
public class CommonCacheProcess {

    /**
     * 从手机本地获取json数据；
     * 包括：内存、本地文件
     *
     * @param key
     * @return
     */
    public static String getLocalJson(String key) {
        String json = getFromMemory(key);
        if (json == null) {
            json = getFromFile(key);
        }
        return json;
    }

    /**
     * 从内存中读取数据
     *
     * @param key
     * @return
     */
    public static String getFromMemory(String key) {
        return MyApplication.getProtocolCache().get(key);
    }

    /**
     * 从文件中读取数据
     *
     * @param key
     * @return
     */
    public static String getFromFile(String key) {
        // 读取本地文件
        // 应用的缓存文件会存放在两个地方：
        // 1、data/data/包名/DOWNLOAD_INFO_HASHMAP/
        // 2、sdcard/Android/data/包名/
        // 存放原则：1中空间比较宝贵，如果文件大的话不建议存储其中。

        String filepath = FileUtils.getDir("json");
        File file = new File(filepath, key);// String parent, String child
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
                    String json = bufferedReader.readLine();
                    // 需要在内存存一份文件
                    MyApplication.getProtocolCache().put(key, json);

                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        return MyApplication.getProtocolCache().get(key);
    }

    /**
     * 缓存网络数据到本地文件中
     * @param key
     * @param json
     */
    public static void cacheToFile(String key, String json) {

        String filepath = FileUtils.getDir("json");
        // 在本地文件中存储一份
        File file = new File(filepath, key);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(System.currentTimeMillis() + "");
            bufferedWriter.newLine();
            bufferedWriter.write(json);
            bufferedWriter.flush();
            IOUtils.close(bufferedWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
