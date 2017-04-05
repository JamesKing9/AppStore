package com.cheng.appstore.vm;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class BaseCallBack implements Callback {
    private CommonPager pager;
    private static Gson gson = new Gson();
    public BaseCallBack(CommonPager pager) {
        this.pager = pager;
    }

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
            pager.isReadData = true; /*告诉系统已经读取到data*/
            String jsonString = response.body().string(); /*okhttp3 获得的请求体的body，因为body是字节数组，所以转化成字符串*/
            onSuccess(jsonString);
        } else {
            pager.isReadData = false;
        }

        // 更新界面
        pager.runOnUiThread();
    }

    /**
     * 处理成功后的接口回调
     * @param json okhttp3获得的请求体的body
     */
    protected abstract void onSuccess(String json);
}
