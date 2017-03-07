package com.itheima.appstore.vm.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.itheima.appstore.R;
import com.itheima.appstore.utils.ImageUtils;
import com.itheima.appstore.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 创建者     itheima
 * 版权       传智播客.黑马程序员
 * 描述	      ${TODO}
 */
public class DetailPicHolder extends BaseHolder<List<String>> {


    LinearLayout mAppDetailPicIvContainer;

    public DetailPicHolder(View itemView) {
        super(itemView);
        mAppDetailPicIvContainer = (LinearLayout) itemView.findViewById(R.id.app_detail_pic_iv_container);
    }

    @Override
    public void setData(List<String> datas) {
        for (int i = 0; i < datas.size(); i++) {
            String url = datas.get(i);

            //宽度已知-->屏幕的1/3
            int screenWidth = UIUtils.getResources().getDisplayMetrics().widthPixels;
            screenWidth = screenWidth - UIUtils.dip2Px(18);
            int width = screenWidth / 3;

            ImageView iv = new ImageView(UIUtils.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i != 0) {
                params.leftMargin = UIUtils.dip2Px(4);
            }
            iv.setLayoutParams(params);
            //图片的加载
            ImageUtils.loadIntoUseFitWidth(UIUtils.getContext(), getImageUrl(url), R.drawable.ic_default, iv);

            mAppDetailPicIvContainer.addView(iv);
        }
    }


}
