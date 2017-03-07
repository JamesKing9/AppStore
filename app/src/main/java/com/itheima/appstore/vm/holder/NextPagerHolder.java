package com.itheima.appstore.vm.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.itheima.appstore.MyApplication;
import com.itheima.appstore.R;
import com.itheima.appstore.vm.adapter.BaseRecyclerViewAdapter;


/**
 * Created by itheima.
 * 加载更多
 */
public class NextPagerHolder extends BaseHolder<Integer> {
    public static final int LOADING = 1;
    public static final int ERROR = -1;
    public static final int NULL = 0;

    LinearLayout loading;
    LinearLayout retry;

    public NextPagerHolder(View itemView, final BaseRecyclerViewAdapter adapter) {
        super(itemView);
        loading = (LinearLayout) itemView.findViewById(R.id.item_loadmore_container_loading);
        retry = (LinearLayout) itemView.findViewById(R.id.item_loadmore_container_retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.loadNextPagerData();
                setData(LOADING);
            }
        });
    }

    public void setData(final Integer state) {
        MyApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);
                retry.setVisibility(View.GONE);
                switch (state) {
                    case LOADING:
                        loading.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        retry.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

}