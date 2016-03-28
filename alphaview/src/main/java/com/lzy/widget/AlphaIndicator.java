package com.lzy.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2015/9/16
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AlphaIndicator extends LinearLayout {

    private ViewPager viewPager;
    private List<AlphaView> alphaViews = new ArrayList<>();
    /**
     * 子View的数量
     */
    private int childCount;
    /**
     * 当前的条目索引
     */
    private int currentItem = 0;

    public AlphaIndicator(Context context) {
        this(context, null);
    }

    public AlphaIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlphaIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        init();
    }

    private void init() {
        if (viewPager == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        childCount = getChildCount();
        if (viewPager.getAdapter().getCount() != childCount) {
            throw new IllegalArgumentException("LinearLayout的子View数量必须和ViewPager条目数量一致");
        }
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i) instanceof AlphaView) {
                AlphaView alphaView = (AlphaView) getChildAt(i);
                alphaViews.add(alphaView);
                //设置点击监听
                alphaView.setOnClickListener(new MyOnClickListener(i));
            } else {
                throw new IllegalArgumentException("AlphaIndicator的子View必须是AlphaView");
            }
        }
        //对ViewPager添加监听
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        alphaViews.get(currentItem).setIconAlpha(1.0f);
    }

    private class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //滑动时的透明度动画
            if (positionOffset > 0) {
                alphaViews.get(position).setIconAlpha(1 - positionOffset);
                alphaViews.get(position + 1).setIconAlpha(positionOffset);
            }
            //滑动时保存当前按钮索引
            currentItem = position;
        }
    }

    private class MyOnClickListener implements OnClickListener {

        private int currentIndex;

        public MyOnClickListener(int i) {
            this.currentIndex = i;
        }

        @Override
        public void onClick(View v) {
            //点击前先重置所有按钮的状态
            resetState();
            alphaViews.get(currentIndex).setIconAlpha(1.0f);
            //不能使用平滑滚动，否者颜色改变会乱
            viewPager.setCurrentItem(currentIndex, false);
            //点击是保存当前按钮索引
            currentItem = currentIndex;
        }
    }

    /**
     * 重置所有按钮的状态
     */
    private void resetState() {
        for (int i = 0; i < childCount; i++) {
            alphaViews.get(i).setIconAlpha(0);
        }
    }

    private static final String STATE_INSTANCE = "instance_state";
    private static final String STATE_ITEM = "state_item";

    /**
     * @return 当View被销毁的时候，保存数据
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_ITEM, currentItem);
        return bundle;
    }

    /**
     * @param state 用于恢复数据使用
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            currentItem = bundle.getInt(STATE_ITEM);
            //重置所有按钮状态
            resetState();
            //恢复点击的条目颜色
            alphaViews.get(currentItem).setIconAlpha(1.0f);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
        } else {
            super.onRestoreInstanceState(state);
        }
    }
}
