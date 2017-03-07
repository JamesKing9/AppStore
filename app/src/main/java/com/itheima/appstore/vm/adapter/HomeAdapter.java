package com.itheima.appstore.vm.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.itheima.appstore.Constants;
import com.itheima.appstore.MyApplication;
import com.itheima.appstore.R;
import com.itheima.appstore.model.net.AppInfo;
import com.itheima.appstore.model.net.HomeInfo;
import com.itheima.appstore.utils.HttpUtils;
import com.itheima.appstore.utils.UIUtils;
import com.itheima.appstore.vm.holder.BaseHolder;
import com.itheima.appstore.vm.holder.NextPagerHolder;

import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class HomeAdapter extends BaseRecyclerViewAdapter<AppInfo> {
    private HomeInfo info;// info.list
    private FragmentActivity activity;


    public HomeAdapter(HomeInfo info, FragmentActivity activity) {
        super(info.list);
        this.info = info;
        this.activity = activity;
    }

    // 如果加载其他样式的Item我们需要做的工作：
    // 判断具体有那些样式：2种+1种（加载下一页）
    private static final int CAROUSEL = 1;

    // 具体的操作内容：
    // 1、添加getItemViewType，依据position去判断当前条目的样式
    // 2、修改onCreateViewHolder，依据样式加载不同layout
    // 3、修改onBindViewHolder，依据样式绑定不同数据

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder = super.onCreateViewHolder(parent, viewType);

        if (viewType == CAROUSEL) {
            holder = new CarouselHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.fragment_home_carousel, parent,
                    false));
        }

        return holder;
    }

    @Override
    protected int getNomalLayoutDesId() {
        return R.layout.item_appinfo;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return CAROUSEL;
        } else if (position == (datas.size() + 1)) {
            return NEXTPAGER;
        } else {
            return NOMAL;
        }
    }


    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        switch (holder.getItemViewType()) {
            case CAROUSEL:
                // 轮播
                holder.setData(info.picture);
                break;
        }

    }

    @Override
    protected AppInfo getNomalItemData(int position) {
        return datas.get(position - 1);
    }


    @Override
    protected String getPath() {
        return Constants.Http.HOME;
    }

    @Override
    protected List<AppInfo> getNextPagerData(String json) {
        HomeInfo nextPager = MyApplication.getGson().fromJson(json, HomeInfo.class);
        if (nextPager != null) {
            return nextPager.list;
        } else {
            nextPagerHolder.setData(NextPagerHolder.NULL);
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return datas.size() + 1 + 1;// 轮播   下一页
    }

    /**
     * 轮播使用的Holder
     */
    class CarouselHolder extends BaseHolder<List<String>> {

        private final SliderLayout sliderLayout;

        public CarouselHolder(View itemView) {
            super(itemView);
            sliderLayout = (SliderLayout) itemView;

            // 高度的获取：保持图片的宽高比例不变
            // 181/480
            // 读取屏幕的宽度

            Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
            int height = defaultDisplay.getWidth() * 181 / 480;// 像素
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.px2Dip(height));
            sliderLayout.setLayoutParams(layoutParams);
        }

        public void setData(List<String> data) {
            sliderLayout.removeAllSliders();
            for (String item : data) {

                // 创建Item项
                // 添加到sliderLayout
                DefaultSliderView sliderView = new DefaultSliderView(UIUtils.getContext());

                // http://localhost:8080/GooglePlayServer/image?name=
                StringBuffer iconUrlBuffer = new StringBuffer(Constants.Http.HOST);
                HashMap<String, Object> params = new HashMap<>();
                params.put("name", item);
                iconUrlBuffer.append(Constants.Http.IMAGE).append(HttpUtils.getUrlParamsByMap(params));

                sliderView.image(iconUrlBuffer.toString());
                sliderLayout.addSlider(sliderView);
            }

        }
    }
}