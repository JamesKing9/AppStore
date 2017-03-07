package com.itheima.appstore.views;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itheima.appstore.utils.UIUtils;

/**
 * 创建RecyclerView工厂
 */
public class RecyclerViewFactory {

    /**
     * 获得垂直方向滚动的 view 容器
     * @return
     */
    public static RecyclerView createVertical(){
        RecyclerView recyclerView = new RecyclerView(UIUtils.getContext());
        recyclerView.setLayoutManager(
                new LinearLayoutManager(UIUtils.getContext(), LinearLayoutManager.VERTICAL, false));
        return recyclerView;
    }
}
