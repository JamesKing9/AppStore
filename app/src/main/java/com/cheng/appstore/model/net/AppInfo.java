package com.cheng.appstore.model.net;

import java.util.List;

/**
 * 应用信息封装
 */
public class AppInfo {

    /**
     * id : 1525490
     * name : 有缘网
     * packageName : com.youyuan.yyhl
     * iconUrl : app/com.youyuan.yyhl/icon.jpg
     * stars : 4
     * size : 3876203
     * downloadUrl : app/com.youyuan.yyhl/com.youyuan.yyhl.apk
     * des : 产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松
     */

    public long id;
    public String name;
    public String packageName;
    public String iconUrl;
    public float stars;
    public long size;
    public String downloadUrl;
    public String des;


    public String author;//有缘网
    public String date;//"2015-06-10"
    public String version;//	1.1.0605.0
    public String downloadNum;//	40万+
    public List<SafeInfo> safe;
    public List<String> screen;

    @Override
    public String toString() {
        return "AppInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
