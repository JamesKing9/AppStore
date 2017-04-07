package com.cheng.appstore.vm.holder;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.TextView;

import com.cheng.appstore.databinding.ItemDetailDesBinding;
import com.cheng.appstore.model.net.AppInfo;

/**
 * 描述	      ${TODO}
 */
public class DetailDesHolder extends BaseHolder<AppInfo> {

    private boolean isClose = true;
    private int wholeHeight=0;
    private AppInfo info;

    private ItemDetailDesBinding binding;

    public DetailDesHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDetailDesHeight(true);
            }
        });
        binding.appDetailDesTvDes.setLines(5);
    }

    private void changeDetailDesHeight(boolean b) {
        wholeHeight=getTextViewHeight(binding.appDetailDesTvDes);
        binding.appDetailDesTvDes.setHeight(wholeHeight);
    }

    private int getTextViewHeight(TextView tv) {
        int count = tv.getLineCount();
        int contentHeight=tv.getLineHeight()*count;
        int padding = tv.getPaddingTop() + tv.getPaddingBottom();
        return contentHeight + padding;
    }

    @Override
    public void setData(AppInfo data) {
        this.info=data;
        binding.setApp(data);

    }
    
}
