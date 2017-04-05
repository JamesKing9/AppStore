package com.cheng.appstore.vm.fragment;

import android.support.v4.widget.NestedScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.cheng.appstore.Constants;
import com.cheng.appstore.MyApplication;
import com.cheng.appstore.utils.HttpUtils;
import com.cheng.appstore.utils.UIUtils;
import com.cheng.appstore.views.FlowLayout;
import com.cheng.appstore.vm.BaseCallBack;
import com.cheng.appstore.vm.CommonCacheProcess;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by itheima.
 */
public class HotFragment extends BaseFragment {
    private String key;
    private List<String> hots;

    @Override
    protected void showSuccess() {
        NestedScrollView scrollView = new NestedScrollView(UIUtils.getContext());

        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
        for(String item:hots){
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(item);

            int padding = UIUtils.dip2Px(5);
            tv.setPadding(padding,padding,padding,padding);

            flowLayout.addView(tv);
        }
        scrollView.addView(flowLayout);
        pager.changeViewTo(scrollView);
    }

    @Override
    protected void loadData() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("index", 0);

        key = Constants.Http.HOT + ".0";
        String json = CommonCacheProcess.getLocalJson(key);
        if (json != null) {
            parserJson(json);
            pager.runOnUiThread();
        } else {
            loadNetData(params);
        }
    }

    private void loadNetData(HashMap<String, Object> params) {

        Request request = HttpUtils.getRequest(Constants.Http.HOT, params);

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

        hots = MyApplication.getGson().fromJson(json, new TypeToken<List<String>>() {
        }.getType());

        if (hots != null && hots.size() > 0) {
            pager.isNullData = false;
        } else {
            pager.isNullData = true;
        }
    }
}
