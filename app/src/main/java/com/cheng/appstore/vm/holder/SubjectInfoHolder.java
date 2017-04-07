package com.cheng.appstore.vm.holder;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.cheng.appstore.Constants;
import com.cheng.appstore.R;
import com.cheng.appstore.databinding.ItemSubjectBinding;
import com.cheng.appstore.model.net.SubjectInfo;
import com.cheng.appstore.utils.HttpUtils;
import com.cheng.appstore.utils.ImageUtils;
import com.cheng.appstore.utils.UIUtils;

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
