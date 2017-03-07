package com.itheima.appstore.vm.holder;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.itheima.appstore.Constants;
import com.itheima.appstore.R;
import com.itheima.appstore.databinding.ItemAppinfoBinding;
import com.itheima.appstore.databinding.ItemSubjectBinding;
import com.itheima.appstore.model.net.AppInfo;
import com.itheima.appstore.model.net.SubjectInfo;
import com.itheima.appstore.utils.HttpUtils;
import com.itheima.appstore.utils.ImageUtils;
import com.itheima.appstore.utils.UIUtils;

import java.util.HashMap;

/**
 * Created by itheima.
 * 应用条目
 */
public class SubjectInfoHolder extends BaseHolder<SubjectInfo> {

    ItemSubjectBinding binding;

    public SubjectInfoHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
    }

    public void setData(SubjectInfo data) {
        binding.setSubject(data);
        // http://localhost:8080/GooglePlayServer/image?name=
        StringBuffer iconUrlBuffer = new StringBuffer(Constants.Http.HOST);
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", data.url);
        iconUrlBuffer.append(Constants.Http.IMAGE).append(HttpUtils.getUrlParamsByMap(params));

        ImageUtils.loadIntoUseFitWidth(UIUtils.getContext(), iconUrlBuffer.toString(), R.drawable.ic_default, binding.itemSubjectIvIcon);
    }


}
