package com.lzy.alphaindicatorview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lzy.widget.AlphaIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        AlphaIndicator alphaIndicator = (AlphaIndicator) findViewById(R.id.alphaIndicator);
        alphaIndicator.setViewPager(viewPager);
    }

    private class MainAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private String[] titles = {//
                "第一页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//
                "第二页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//
                "第三页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度", //
                "第四页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度"};

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(TextFragment.newInstance(titles[0]));
            fragments.add(TextFragment.newInstance(titles[1]));
            fragments.add(TextFragment.newInstance(titles[2]));
            fragments.add(TextFragment.newInstance(titles[3]));
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
