package com.cheng.recycleviewdemo.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cheng.recycleviewdemo.R;
import com.cheng.recycleviewdemo.bean.Randoms;
import com.cheng.recycleviewdemo.bean.User;
import com.cheng.recycleviewdemo.databinding.UserItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyAdapterHolder> {

    private static final int USER_COUNT = 10;
    private List<User> users;

    public UserAdapter() {
        users=new ArrayList<>(USER_COUNT) ;
        for (int i = 0; i < USER_COUNT; i++) {
            User user = new User(Randoms.nextFirstName(), Randoms.nextLastName());
            users.add(user);
        }
    }

    @Override
    public MyAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item=View.inflate(parent.getContext(), R.layout.user_item,null);
        MyAdapterHolder myAdapterHolder=new MyAdapterHolder(item);
        return myAdapterHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapterHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return USER_COUNT;
    }

    class MyAdapterHolder extends RecyclerView.ViewHolder{
        UserItemBinding itemBinding;
        public MyAdapterHolder(View itemView) {
            super(itemView);
            itemBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(User user){
            itemBinding.setUser(user);

            //return mPreLayoutPosition == NO_POSITION ? mPosition : mPreLayoutPosition;
            //return mPreLayoutPosition == NO_POSITION ? mPosition : mPreLayoutPosition;
            this.getLayoutPosition();
        }
    }
}
