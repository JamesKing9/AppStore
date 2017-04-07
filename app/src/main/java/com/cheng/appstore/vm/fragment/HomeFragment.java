package com.cheng.appstore.vm.fragment;

import android.support.v7.widget.RecyclerView;

import com.cheng.appstore.Constants;
import com.cheng.appstore.MyApplication;
import com.cheng.appstore.model.net.AppInfo;
import com.cheng.appstore.model.net.HomeInfo;
import com.cheng.appstore.utils.HttpUtils;
import com.cheng.appstore.views.RecyclerViewFactory;
import com.cheng.appstore.vm.BaseCallBack;
import com.cheng.appstore.vm.CommonCacheProcess;

import com.cheng.appstore.vm.adapter.HomeAdapter;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

    // 首页联网数据
    private HomeInfo info;
    private String filepath;
    private String key;
    // 展示成功界面
    private RecyclerView recyclerView;

    // 流程：
    // 1、数据加载中
    // 2、耗时操作

    /**
     * 加载成功界面
     */
    protected void showSuccess() {
        recyclerView = RecyclerViewFactory.createVertical();
        recyclerView.setAdapter(new HomeAdapter(info, this.getActivity()));
        pager.changeViewTo(recyclerView);
    }

    /**
     * 耗时操作
     */
    protected void loadData() {
        // 从内存中读取数据:通常的做法是否放入集合中
        // List和Map，优先考虑Map。可以利用Key快速的找到Value信息
        // 由于协议体积比较小，所以我们不考虑删除的动作
        // 由于很多地方都会设计到加载界面，所以我们容器需要放一个公共的环境下

        // 关于Key的处理：（1、home.0。2、url）
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("index", 0);

        key = Constants.Http.HOME + ".0"; /*home.0*/
        /*从手机本地获取json数据*/
        String json = CommonCacheProcess.getLocalJson(key);
        if (json != null) {
            /*如果json 文件数据不为空，则解析json数据*/
            parserJson(json);
            pager.runOnUiThread();
        } else {
            loadNetData(params);
        }


        // 联网获取数据
//                1、创建联网用的客户端
//                2、创建发送请求（get或post，链接，参数）
//                3、发送请求
//                4、结果处理


//                Request.Builder builder = new Request.Builder();
//                builder.get();
//                // http://localhost:8080/GooglePlayServer/home?index=0
//                final HashMap<String, Object> params = new HashMap<String, Object>();
//                params.put("index", 0);
//                String urlParamsByMap = HttpUtils.getUrlParamsByMap(params);
//                String url = Constants.HOST + Constants.HOME + urlParamsByMap;
//                builder.url(url);
//                Request request = builder.build();

        /*
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pager.isReadData = false;
                // 更新界面
                pager.runOnUiThread();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 判断：状态码（200）
                // 判断：服务器回复信息内容
                if (response.code() == 200) {
                    pager.isReadData = true;
                    String jsonString = response.body().string();
                    Gson gson = new Gson();
                    info = gson.fromJson(jsonString, HomeInfo.class);

                    List<AppInfo> list = info.list;
                    List<String> picture = info.picture;
                    if ((list != null && list.size() > 0) || (picture != null && picture.size() > 0)) {
                        pager.isNullData = false;
                    } else {
                        pager.isNullData = true;
                    }

                } else {
                    pager.isReadData = false;
                }

                // 更新界面
                pager.runOnUiThread();
            }
        });
        */
    }

    /**
     * 加载网络数据
     */
    private void loadNetData(HashMap<String, Object> params) {
        Request request = HttpUtils.getRequest(Constants.Http.HOME, params);

        Call call = HttpUtils.getClient()
                .newCall(request);

        // call.download():同步
        // call.enqueue(): 异步
        call.enqueue(new BaseCallBack(pager) {
            @Override
            protected void onSuccess(String json) {
                // 需要在内存存一份文件
                MyApplication.getProtocolCache().put(key, json);
                CommonCacheProcess.cacheToFile(key, json);
                // 缓存后紧接着解析json
                parserJson(json);
            }
        });
    }

    /**
     * 解析 json数据
     * @param json 传入的json 文件
     */
    private void parserJson(String json) {
        pager.isReadData = true;
        /**获得HomeInfo 的实例*/
        info = MyApplication.getGson()
                .fromJson(json, HomeInfo.class);
        List<AppInfo> list = info.list;
        List<String> picture = info.picture;
        if ((list != null && list.size() > 0) || (picture != null && picture.size() > 0)) {
            pager.isNullData = false;
        } else {
            pager.isNullData = true;
        }
    }

}
