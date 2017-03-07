package com.itheima.appstore.vm.adapter;

import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.itheima.appstore.Constants;
import com.itheima.appstore.vm.CommonCacheProcess;
import com.itheima.appstore.MyApplication;
import com.itheima.appstore.R;
import com.itheima.appstore.utils.HttpUtils;
import com.itheima.appstore.utils.LogUtils;

import com.itheima.appstore.vm.holder.AppInfoHolder;
import com.itheima.appstore.vm.holder.BaseHolder;
import com.itheima.appstore.vm.holder.NextPagerHolder;
import com.itheima.appstore.vm.holder.SubjectInfoHolder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 通用RecyclerView的数据适配
 */
public abstract class BaseRecyclerViewAdapter<D> extends RecyclerView.Adapter<BaseHolder<D>> {
    protected List<D> datas;
    protected NextPagerHolder nextPagerHolder;

    // 如果加载其他样式的Item我们需要做的工作：
    // 判断具体有那些样式：2种（普通、加载下一页）
    protected static final int NOMAL = 0;
    protected static final int NEXTPAGER = 2;

    public BaseRecyclerViewAdapter(List<D> datas) {
        this.datas = datas;
    }

    /**
     * 获取不同界面需要展示数据的服务器链接
     * @return
     */
    protected abstract String getPath();


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder = null;
        switch (viewType) {
            case NOMAL:
                // 处理通用的项布局加载
                // 两种处理方案：
                // 1、交给子类完成
                // 2、依据getPath来区分不同的Adapter，从而创建对应的Holder

                if(getPath().equals(Constants.Http.SUBJECT)){
                    holder=new SubjectInfoHolder(LayoutInflater.from(
                            parent.getContext()).inflate(getNomalLayoutDesId(), parent,
                            false));
                }else {
                    holder = new AppInfoHolder(LayoutInflater.from(
                            parent.getContext()).inflate(getNomalLayoutDesId(), parent,
                            false));
                }
                break;
            case NEXTPAGER:
                holder = nextPagerHolder = new NextPagerHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.item_loadmore, parent,
                        false), this);

                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case NOMAL:
                D data = getNomalItemData(position);
                holder.setData(data);
                break;
            case NEXTPAGER:
                LogUtils.s("加载下一页数据");
                // 最后一项显现出来，这样就不用判断RecyclerView是否滚动到底部
                holder.setData(NextPagerHolder.LOADING);
                loadNextPagerData();
                break;
        }

    }

    /**
     * 获取通用条目的数据
     * @param position
     * @return
     */
    protected abstract D getNomalItemData(int position);


    /**
     * 获取通用条目布局的id信息
     * @return
     */
    protected abstract int getNomalLayoutDesId();

    /**
     * 加载下一页数据
     *
     */
    public void loadNextPagerData() {

        // 加载本地
        // 是否加载到数据
        // 加载到，展示界面
        // 没加载到，获取网络数据
        // 是否加载到数据
        // 加载到，展示界面
        // 没加载到，重试界面显示

        // 判断是否含有下一页数据
        // 有，显示加载中条目
        // 没有，什么都不显示

        String key = getPath() + "." + datas.size();
        String json = CommonCacheProcess.getLocalJson(key);

        if (json == null) {
            // 加载网络数据
            OkHttpClient client = new OkHttpClient();

            HashMap<String, Object> params = new HashMap<>();
            params.put("index", datas.size());
            final Request request = HttpUtils.getRequest(getPath(), params);

            Call call = HttpUtils.getClient().newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // 显示重试条目
                    nextPagerHolder.setData(NextPagerHolder.ERROR);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // 判断是否获取到数据
                    if (response.code() == 200) {
                        String json = response.body().string();
                        showNextPagerData(json);
                    } else {
                        // 显示重试条目
                        nextPagerHolder.setData(NextPagerHolder.ERROR);
                    }
                }
            });

        } else {
            // 加载本地数据
            showNextPagerData(json);
        }
    }

    /**
     * 解析并显示下一页数据
     * @param json
     */
    protected void showNextPagerData(String json){
        List<D> nextPagerData=getNextPagerData(json);
        if (nextPagerData != null && nextPagerData.size() > 0) {
            // 更新界面
            datas.addAll(nextPagerData);
            SystemClock.sleep(2000);
            // 线程：子线程
            MyApplication.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });

        } else {
            nextPagerHolder.setData(NextPagerHolder.NULL);
        }
    }
    /**
     * 获取下一页数据
     */
    protected abstract List<D> getNextPagerData(String json);
}
