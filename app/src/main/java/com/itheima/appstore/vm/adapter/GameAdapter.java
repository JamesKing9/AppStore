package com.itheima.appstore.vm.adapter;

import com.google.gson.reflect.TypeToken;
import com.itheima.appstore.Constants;
import com.itheima.appstore.MyApplication;
import com.itheima.appstore.R;
import com.itheima.appstore.model.net.AppInfo;

import java.util.List;

/**
 * Created by itheima.
 * 游戏界面的数据适配器
 */
public class GameAdapter extends BaseRecyclerViewAdapter<AppInfo> {

    public GameAdapter(List<AppInfo> apps) {
        super(apps);
//        this.datas=apps;// 不用去单独写改行代码
    }

    @Override
    protected String getPath() {
        return Constants.Http.GAME;
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
        return MyApplication.getGson().fromJson(json,new TypeToken<List<AppInfo>>(){}.getType());
    }

    @Override
    public int getItemCount() {
        return datas.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一项需要显示加载更多的话，有两种不同的type，需要处理getItemViewType
        if(position==datas.size()){
            return NEXTPAGER;
        }
        return NOMAL;
    }
}
