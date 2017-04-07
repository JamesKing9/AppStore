package com.cheng.appstore.vm.holder;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by itheima.
 * 详情界面折叠动画管理
 */
public class DetailFoldAnimationManager {

    /**
     * 属性动画
     * 1、数据加载完成后每个控件都有自己的完整高度，展示给用户的是折叠之后的高度。
     * a)高度值：完整、折叠
     * 2、动画处理：
     * a)展开
     * i.由折叠高度变换成完整高度，高度变换动画；
     * ii.箭头由向下状态旋转180度变为向下，旋转动画；
     * b)合并
     * i.由完整高度变换成折叠高度，高度变换动画；
     * ii.箭头由向上状态旋转180度变为向上，旋转动画；
     */

    private static DetailFoldAnimationManager instance=new DetailFoldAnimationManager();

    public static DetailFoldAnimationManager getInstance() {
        return instance;
    }

    public static void setInstance(DetailFoldAnimationManager instance) {
        DetailFoldAnimationManager.instance = instance;
    }

    // 高度值：完整、折叠
    public int wholeHeight;
    public int foldHeight;

    public boolean isOpen;

    /**
     * 属性动画使用两个类完成：
     * 1、ValueAnimator:目标View中没有属性（是否含有setter）
     * 2、ObjectAnimator:目标View中有属性
     */

    public void doHeight(View target,int wholeHeight,int foldHeight){
        if(isOpen) {
            ObjectAnimator.ofInt(target,"height",wholeHeight,foldHeight);
        }else {
            ObjectAnimator.ofInt(target,"height",foldHeight,wholeHeight);
        }
        isOpen=!isOpen;
    }

//    public static void doRotation(View target,float start, float end)


}
