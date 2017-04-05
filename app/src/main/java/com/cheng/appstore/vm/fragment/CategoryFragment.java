package com.cheng.appstore.vm.fragment;

import android.support.v7.widget.RecyclerView;

import com.cheng.appstore.Constants;
import com.cheng.appstore.MyApplication;
import com.cheng.appstore.model.net.CategoryInfo;
import com.cheng.appstore.utils.HttpUtils;
import com.cheng.appstore.views.RecyclerViewFactory;
import com.cheng.appstore.vm.BaseCallBack;
import com.cheng.appstore.vm.CommonCacheProcess;
import com.cheng.appstore.vm.adapter.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by itheima.
 */
public class CategoryFragment extends BaseFragment {
    private String key;
    private List<CategoryInfo> categories;
    private RecyclerView recyclerView;

    @Override
    protected void showSuccess() {
        recyclerView = RecyclerViewFactory.createVertical();
        recyclerView.setAdapter(new CategoryAdapter(categories));
        pager.changeViewTo(recyclerView);
    }

    @Override
    protected void loadData() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("index", 0);

        key = Constants.Http.CATEGORY + ".0";
        String json = CommonCacheProcess.getLocalJson(key);
        if (json != null) {
            parserJson(json);
            pager.runOnUiThread();
        } else {
            loadNetData(params);
        }
    }

    private void loadNetData(HashMap<String, Object> params) {

        Request request = HttpUtils.getRequest(Constants.Http.CATEGORY, params);

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

        try {
            categories = new ArrayList<>();
            JSONArray array1=new JSONArray(json);
            for(int i=0;i<array1.length();i++) {
                JSONObject object = array1.getJSONObject(i);
                // 标题项处理
                String title = object.getString("title");
                CategoryInfo titleItem=new CategoryInfo();
                titleItem.title=title;
                titleItem.isTitle=true;
                categories.add(titleItem);

                // 分类项处理
                JSONArray infos = object.getJSONArray("infos");
                for(int j=0;j<infos.length();j++){
                    JSONObject infosJSONObject = infos.getJSONObject(j);
                    /**
                     * name1	休闲
                     name2	棋牌
                     name3	益智
                     url1	image/category_game_0.jpg
                     url2	image/category_game_1.jpg
                     url3	image/category_game_2.jpg
                     */
                    CategoryInfo item=new CategoryInfo();
                    item.url1=infosJSONObject.getString("url1");
                    item.url2=infosJSONObject.getString("url2");
                    item.url3=infosJSONObject.getString("url3");

                    item.name1=infosJSONObject.getString("name1");
                    item.name2=infosJSONObject.getString("name2");
                    item.name3=infosJSONObject.getString("name3");

                    categories.add(item);
                }
            }


            if (categories != null && categories.size() > 0) {
                pager.isNullData = false;
            } else {
                pager.isNullData = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            pager.isReadData = false;
        }
    }
}
