package com.cheng.recycleviewdemo.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cheng.recycleviewdemo.R;
import com.cheng.recycleviewdemo.bean.RandomNames;
import com.cheng.recycleviewdemo.bean.User;
import com.cheng.recycleviewdemo.databinding.UserItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView 的适配器
 * <li> 数据来源</li>
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyAdapterHolder> {

    private static final int USER_COUNT = 20;
    private List<User> users;

    /**
     * 构造方法：
     * <p/> 作用：
     * 1、初始化数据源：该数据源是，先随机生成User对象，
     * 然后将User对象加入到List集合中，从而形成数据源。
     */
    public UserAdapter() {
        users=new ArrayList<>(USER_COUNT) ;
        for (int i = 0; i < USER_COUNT; i++) {
            User user = new User(RandomNames.nextFirstName(), RandomNames.nextLastName());
            users.add(user);
        }
    }

    //创建ViewHolder视图
    @Override
    public MyAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //填充itemview
        View item = View.inflate(parent.getContext(), R.layout.user_item, null);
        MyAdapterHolder myAdapterHolder = new MyAdapterHolder(item);
        return myAdapterHolder;
    }

    //将对应数据绑定到对应的viewholder上
    @Override
    public void onBindViewHolder(MyAdapterHolder holder, int position) {
        holder.bind(users.get(position));
    }

    //item的数目
    @Override
    public int getItemCount() {
        return USER_COUNT;
    }

    //viewholder：每个条目都是一个viewholder，即view控件的容器
    class MyAdapterHolder extends RecyclerView.ViewHolder{
        UserItemBinding itemBinding;

        //在构造中将数据和view绑定
        public MyAdapterHolder(View itemView) {
            super(itemView);
            /* android.databinding.DataBindingUtil */
            itemBinding = DataBindingUtil.bind(itemView);
        }

        /**
         * 定义一个绑定方法，来执行databinding的set方法
         * @param user
         */
        public void bind(User user){
            itemBinding.setUser(user);

            //return mPreLayoutPosition == NO_POSITION ? mPosition : mPreLayoutPosition;
            //return mPreLayoutPosition == NO_POSITION ? mPosition : mPreLayoutPosition;
            this.getLayoutPosition();
        }
    }
}
