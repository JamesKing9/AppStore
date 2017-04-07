package com.cheng.appstore.vm.adapter;

import com.google.gson.reflect.TypeToken;
import com.cheng.appstore.Constants;
import com.cheng.appstore.MyApplication;
import com.cheng.appstore.R;
import com.cheng.appstore.model.net.AppInfo;

import java.util.List;

/**
 * Created by itheima.
 * 应用界面的适配器
 */
public class AppAdapter extends BaseRecyclerViewAdapter<AppInfo> {
    public AppAdapter(List<AppInfo> apps) {
        super(apps);
    }


    // 具体的操作内容：
    // 1、添加getItemViewType，依据position去判断当前条目的样式
    // 2、修改onCreateViewHolder，依据样式加载不同layout
    // 3、修改onBindViewHolder，依据样式绑定不同数据


    @Override
    public int getItemViewType(int position) {
        // 最后一项是加载更多
        // 其他的项是应用信息展示
        if (position == datas.size()) {
            return NEXTPAGER;
        }
        return NOMAL;
    }



    @Override
    protected String getPath() {
        return Constants.Http.APP;
    }

    @Override
    protected AppInfo getNomalItemData(int position) {
        return datas.get(position);
    }

    @Override
    protected int getNomalLayoutDesId() {
        return R.layout.item_appinfo;
    }

    @Override
    protected List<AppInfo> getNextPagerData(String json) {
        return MyApplication.getGson().fromJson(json, new TypeToken<List<AppInfo>>() {
        }.getType());
    }


    @Override
    public int getItemCount() {
        return datas.size() + 1;// 轮播   下一页
    }
}