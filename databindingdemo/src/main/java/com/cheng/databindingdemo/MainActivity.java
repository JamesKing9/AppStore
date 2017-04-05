package com.cheng.databindingdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cheng.databindingdemo.databinding.ActivityMainBinding;
import com.cheng.databindingdemo.model.bean.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // Binding有一个设置User的入口，setUser
        ActivityMainBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        User user=new User("itheima");
        binding.setUser(user);
    }
}
