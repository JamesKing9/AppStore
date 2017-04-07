package com.cheng.appstore.vm;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by itheima.
 */
public abstract class ItemHolder<T> extends RecyclerView.ViewHolder {
    protected ViewDataBinding bind;

    public ItemHolder(View itemView) {
        super(itemView);
        bind = DataBindingUtil.bind(itemView);
    }

    public abstract void setData(T t);
}
