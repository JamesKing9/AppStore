package com.itheima.appstore.vm.fragment;

import android.support.v7.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.itheima.appstore.Constants;
import com.itheima.appstore.MyApplication;
import com.itheima.appstore.model.net.AppInfo;
import com.itheima.appstore.utils.HttpUtils;
import com.itheima.appstore.views.RecyclerViewFactory;
import com.itheima.appstore.vm.BaseCallBack;
import com.itheima.appstore.vm.CommonCacheProcess;

import com.itheima.appstore.vm.adapter.AppAdapter;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by itheima.
 * 应用
 */
public class AppFragment extends BaseFragment {
    // 展示成功界面
    private RecyclerView recyclerView;
    private List<AppInfo> apps;
    private String key;


    protected void showSuccess() {
        recyclerView = RecyclerViewFactory.createVertical();
        recyclerView.setAdapter(new AppAdapter(apps));
        pager.changeViewTo(recyclerView);
    }

    protected void loadData() {
        // 联网获取数据
//                1、创建联网用的客户端
//                2、创建发送请求（get或post，链接，参数）
//                3、发送请求
//                4、结果处理

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("index", 0);

        key = Constants.Http.APP + ".0";
        String json = CommonCacheProcess.getLocalJson(key);
        if (json != null) {
            parserJson(json);
            pager.runOnUiThread();
        } else {
            loadNetData(params);
        }
    }

    private void loadNetData(HashMap<String, Object> params) {

        Request request = HttpUtils.getRequest(Constants.Http.APP, params);

        Call call = HttpUtils.getClient().newCall(request);
        // call.download():同步
        // 异步
        call.enqueue(new BaseCallBack(pager) {
            @Override
            protected void onSuccess(String json) {
                // 需要在内存存一份文件
                MyApplication.getProtocolCache().put(key, json);
                CommonCacheProcess.cacheToFile(key, json);
                parserJson(json);
            }
        });
    }

    private void parserJson(String json) {
        pager.isReadData = true;

        apps = MyApplication.getGson().fromJson(json, new TypeToken<List<AppInfo>>() {
        }.getType());

        if (apps != null && apps.size() > 0) {
            pager.isNullData = false;
        } else {
            pager.isNullData = true;
        }
    }

}
