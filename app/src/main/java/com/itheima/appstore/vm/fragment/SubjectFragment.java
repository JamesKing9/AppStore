package com.itheima.appstore.vm.fragment;

import android.support.v7.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.itheima.appstore.Constants;
import com.itheima.appstore.MyApplication;
import com.itheima.appstore.model.net.SubjectInfo;
import com.itheima.appstore.utils.HttpUtils;
import com.itheima.appstore.views.RecyclerViewFactory;
import com.itheima.appstore.vm.BaseCallBack;
import com.itheima.appstore.vm.CommonCacheProcess;
import com.itheima.appstore.vm.adapter.SubjectAdapter;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by itheima.
 */
public class SubjectFragment extends BaseFragment {
    private String key;
    private List<SubjectInfo> subjects;
    private RecyclerView recyclerView;

    @Override
    protected void showSuccess() {
        recyclerView = RecyclerViewFactory.createVertical();
        recyclerView.setAdapter(new SubjectAdapter(subjects));
        pager.changeViewTo(recyclerView);
    }

    @Override
    protected void loadData() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("index", 0);

        key = Constants.Http.SUBJECT + ".0";
        String json = CommonCacheProcess.getLocalJson(key);
        if (json != null) {
            parserJson(json);
            pager.runOnUiThread();
        } else {
            loadNetData(params);
        }
    }

    private void loadNetData(HashMap<String, Object> params) {

        Request request = HttpUtils.getRequest(Constants.Http.SUBJECT, params);

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

        subjects = MyApplication.getGson().fromJson(json, new TypeToken<List<SubjectInfo>>() {
        }.getType());

        if (subjects != null && subjects.size() > 0) {
            pager.isNullData = false;
        } else {
            pager.isNullData = true;
        }
    }
}
