package com.cheng.appstore.vm.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheng.appstore.R;
import com.cheng.appstore.model.net.SafeInfo;
import com.cheng.appstore.utils.ImageUtils;
import com.cheng.appstore.utils.UIUtils;

import java.util.List;

/**
 * 创建者     itheima
 * 版权       传智播客.黑马程序员
 * 描述	      ${TODO}
 */
public class DetailSafeHolder extends BaseHolder<List<SafeInfo>> implements View.OnClickListener {


    ImageView mAppDetailSafeIvArrow;
    LinearLayout mAppDetailSafePicContainer;
    LinearLayout mAppDetailSafeDesContainer;

    private boolean isClose = true;

    private int wholeHeight = 0;

    public DetailSafeHolder(View itemView) {
        super(itemView);
        mAppDetailSafeIvArrow = (ImageView) itemView.findViewById(R.id.app_detail_safe_iv_arrow);
        mAppDetailSafePicContainer = (LinearLayout) itemView.findViewById(R.id.app_detail_safe_pic_container);
        mAppDetailSafeDesContainer = (LinearLayout) itemView.findViewById(R.id.app_detail_safe_des_container);

        itemView.setOnClickListener(this);

        ViewGroup.LayoutParams layoutParams = mAppDetailSafeDesContainer.getLayoutParams();
        layoutParams.height = 0;
        mAppDetailSafeDesContainer.setLayoutParams(layoutParams);
    }


    @Override
    public void onClick(View v) {

        if (isClose) {
            // 处理高度渐变的动画

//        ObjectAnimator animator=ObjectAnimator.ofInt(mAppDetailSafeDesContainer,"height",0,mAppDetailSafeDesContainer.getMeasuredHeight());
            setWholeHeight();
            changeSafeDesContainer(0,wholeHeight);
            // 处理角度渐变的动画
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 0, 180);
            objectAnimator.setDuration(300).start();
        }else{
            // 处理高度渐变的动画

            setWholeHeight();
            changeSafeDesContainer(wholeHeight,0);
            // 处理角度渐变的动画
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 180, 0);
            objectAnimator.setDuration(300).start();
        }
        isClose=!isClose;

    }

    private void changeSafeDesContainer(int start,int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start,end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = mAppDetailSafeDesContainer.getLayoutParams();
                params.height = value;
                mAppDetailSafeDesContainer.setLayoutParams(params);
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    private void setWholeHeight() {
        if (wholeHeight == 0) {
            mAppDetailSafeDesContainer.measure(0, 0);
            wholeHeight = mAppDetailSafeDesContainer.getMeasuredHeight();
        }
    }


    @Override
    public void setData(List<SafeInfo> datas) {
        for (int i = 0; i < datas.size(); i++) {
            SafeInfo safeInfo = datas.get(i);

            /*---------------  往mAppDetailSafePicContainer 容器动态加载孩子 ---------------*/
            ImageView ivIcon = new ImageView(UIUtils.getContext());
            ivIcon.setLayoutParams(new LinearLayout.LayoutParams(UIUtils.dip2Px(50), ViewGroup.LayoutParams.WRAP_CONTENT));
            //图片的加载
//            Picasso.with(UIUtils.getContext()).load(getImageUrl(safeInfo.safeUrl)).into(ivIcon);
            ImageUtils.loadIntoUseFitWidth(UIUtils.getContext(), getImageUrl(safeInfo.safeUrl), R.drawable.ic_default, ivIcon);

            mAppDetailSafePicContainer.addView(ivIcon);

            /*---------------  往mAppDetailSafeDesContainer 容器动态加载孩子 ---------------*/
            LinearLayout line = new LinearLayout(UIUtils.getContext());
            ImageView ivDesIcon = new ImageView(UIUtils.getContext());
            ivDesIcon.setLayoutParams(new LinearLayout.LayoutParams(UIUtils.dip2Px(18), ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView tvDesNote = new TextView(UIUtils.getContext());

            // 效果调整
            line.setGravity(Gravity.CENTER_VERTICAL);
            tvDesNote.setSingleLine(true);//设置单行效果
            if (safeInfo.safeDesColor == 0) {
                tvDesNote.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
            } else {
                tvDesNote.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
            }


            //设置数据
            tvDesNote.setText(safeInfo.safeDes);
            ImageUtils.loadIntoUseFitWidth(UIUtils.getContext(), getImageUrl(safeInfo.safeDesUrl), R.drawable.ic_default, ivDesIcon);

            line.addView(ivDesIcon);
            line.addView(tvDesNote);

            mAppDetailSafeDesContainer.addView(line);
        }
    }
}
