package com.itheima.appstore.vm.holder;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.bumptech.glide.Glide;
import com.itheima.appstore.databinding.ItemDetailInfoBinding;
import com.itheima.appstore.model.net.AppInfo;
import com.itheima.appstore.utils.UIUtils;

/**
 * Created by itheima.
 * 应用详情
 */
public class DetailInfoHolder extends BaseHolder<AppInfo> {

    private final ItemDetailInfoBinding binding;

    public DetailInfoHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void setData(AppInfo data) {
        binding.setApp(data);
        Glide.with(UIUtils.getContext()).load(getImageUrl(data.iconUrl)).into(binding.appDetailInfoIvIcon);
    }
}
