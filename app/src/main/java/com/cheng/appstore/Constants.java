package com.cheng.appstore;

/**
 * <p>存放一些常量</p>
 * <li>1 存放“Url ”常量</li>
 */
public class Constants {

    /**“Url ”常量*/
    public static final class Http {

        /**## 服务器主机 ip ##**/
        /**服务器在电脑上,直接ip访问*/
//        public static final String HOST = "http://192.168.0.103:8080/GooglePlayServer/";
        /**服务器在手机上*/
//        public static final String HOST = "http://127.0.0.1:8080/GooglePlayServer/";
        /**服务器在电脑上,android模拟器访问*/
//        public static final String HOST = "http://10.0.2.2:8080/GooglePlayServer/";
        /**服务器在电脑上,genymotion模拟器访问*/
        public static final String HOST = "http://10.0.3.2:8080/GooglePlayServer/";

        /**## 主机资源的分类 ##**/
        public static final String HOME = "home";
        public static final String APP = "app";
        public static final String GAME = "game";
        public static final String SUBJECT = "subject";
        public static final String CATEGORY = "category";
        public static final String RECOMMEND = "recommend";
        public static final String HOT = "hot";
        public static final String IMAGE = "image";
        public static final String DETAIL = "detail";
        public static final String DOWNLOAD = "download";
    }

    public static final long DURATION=5*60*1000;// 时间间隔5分钟
}
