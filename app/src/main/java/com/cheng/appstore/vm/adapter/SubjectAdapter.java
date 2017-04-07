package com.cheng.appstore.vm.adapter;

import android.support.v7.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.cheng.appstore.Constants;
import com.cheng.appstore.MyApplication;
import com.cheng.appstore.R;
import com.cheng.appstore.model.net.SubjectInfo;

import java.util.List;

/**
 * Created by itheima.
 * 专题界面适配器
 */
public class SubjectAdapter extends BaseRecyclerViewAdapter<SubjectInfo> {

    public SubjectAdapter(List<SubjectInfo> datas) {
        super(datas);
    }

    @Override
    protected String getPath() {
        // 链接
        return Constants.Http.SUBJECT;
    }

    @Override
    protected SubjectInfo getNomalItemData(int position) {
        // 通用条目展示数据（与加载更多进行区别）
        return datas.get(position);
    }

    @Override
    protected int getNomalLayoutDesId() {
        // 通用条目布局
        return R.layout.item_subject;
    }

    @Override
    protected List<SubjectInfo> getNextPagerData(String json) {
        // 解析下一页数据
        return MyApplication.getGson().fromJson(json,new TypeToken<List<SubjectInfo>>(){}.getType());
    }

    @Override
    public int getItemCount() {
        return datas.size()+1;
    }

//    由于含有加载更多条目，所以需要处理getItemViewType

    @Override
    public int getItemViewType(int position) {
        if(datas.size()==position){
            return NEXTPAGER;
        }
        return NOMAL;
    }
}

