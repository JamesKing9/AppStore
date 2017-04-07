package com.cheng.appstore.vm.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cheng.appstore.Constants;
import com.cheng.appstore.utils.HttpUtils;

import java.util.HashMap;

/**
 *
 */
public abstract class BaseHolder<D> extends RecyclerView.ViewHolder{
    public BaseHolder(View itemView) {
        super(itemView);
    }

    /**
     * 为Holder设置数据
     * @param data
     */
    public abstract void setData(D data);

    /**
     * 获取图片资源链接
     * @param name
     * @return
     */
    protected String getImageUrl(String name){
        StringBuffer iconUrlBuffer = new StringBuffer(Constants.Http.HOST);
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        iconUrlBuffer.append(Constants.Http.IMAGE).append(HttpUtils.getUrlParamsByMap(params));
        return iconUrlBuffer.toString();
    }
}
