package com.cheng.databindingdemo.model.bean;

/**
 * 布局文件中控件和属性绑定的实体bean
 */
public class User {
    public String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
