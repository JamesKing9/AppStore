package com.cheng.appstore.vm.fragment;

import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.cheng.appstore.Constants;
import com.cheng.appstore.MyApplication;
import com.cheng.appstore.utils.HttpUtils;
import com.cheng.appstore.utils.UIUtils;
import com.cheng.appstore.views.StellarMap;
import com.cheng.appstore.vm.BaseCallBack;
import com.cheng.appstore.vm.CommonCacheProcess;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by itheima.
 */
public class RecommendFragment extends BaseFragment {
    private String key;
    private List<String> recommends;

    private StellarMap.Adapter adapter=new StellarMap.Adapter() {
        @Override
        public int getCount() {
            return recommends.size();
        }

        @Override
        protected View getView(int index, View convertView) {
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(recommends.get(index));
            return tv;
        }
    };

    @Override
    protected void showSuccess() {
        StellarMap stellarMap = new StellarMap(getContext());
        stellarMap.setAdapter(adapter);
        pager.changeViewTo(stellarMap);
    }

    @Override
    protected void loadData() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("index", 0);

        key = Constants.Http.RECOMMEND + ".0";
        String json = CommonCacheProcess.getLocalJson(key);
        if (json != null) {
            parserJson(json);
            pager.runOnUiThread();
        } else {
            loadNetData(params);
        }
    }

    private void loadNetData(HashMap<String, Object> params) {

        Request request = HttpUtils.getRequest(Constants.Http.RECOMMEND, params);

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

        recommends = MyApplication.getGson().fromJson(json, new TypeToken<List<String>>() {
        }.getType());

        if (recommends != null && recommends.size() > 0) {
            pager.isNullData = false;
        } else {
            pager.isNullData = true;
        }
    }
}
