package com.cheng.appstore.utils;

import com.cheng.appstore.Constants;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * <p>自己封装的网络处理类</p>
 *
 */
public class HttpUtils {

    /**这样操作是为了确保 OkHttpClient 实例只有一个，因为 static修饰*/
    private static OkHttpClient client = new OkHttpClient();
    /**获得OkHttpClient 的单例*/
    public static OkHttpClient getClient() {
        return client;
    }

    /**
     * <p>GET 请求 ：具体使用哪种请求是和server端的哥们商量的；</p>
     * <li>1# url: http://localhost:8080/GooglePlayServer/home?index=0</li>
     * @param path server端资源的分类的相对路径
     * @param params 拼接 url 所需要的参数
     * @return
     */
    public static Request getRequest(String path, Map<String, Object> params) {
        StringBuffer urlBuffer = new StringBuffer(Constants.Http.HOST); /*1、先放入 服务器主机 ip */
        urlBuffer.append(path); /*资源的分类的相对路径*/
        urlBuffer.append(getUrlParamsByMap(params));
        /*获得 url: http://localhost:8080/GooglePlayServer/home?index=0*/
        String url = urlBuffer.toString();

        /*okhttp-3.4.1*/
        Request request
                = new Request.Builder()
                .get()
                .url(url)
                .build();
        return request;
    }

    /*index=0*/

    /**
     * 拼接分页的 uri
     * <li>Uri: ?index=0</li>
     * @param map
     * @return ?index=0
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer("?"); /*1、添加引用符‘？’*/
        for (Map.Entry<String, Object> entry:  /*foreach：遍历map中的entry实体元素*/
        map.entrySet()){
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&"); /*2、添加分隔符‘&’*/
        }

        String s = sb.toString(); /*将字符串缓冲池拼接成字符串*/
        if (s.endsWith("&")) {
            /*去除结尾的分隔符‘&’*/
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
