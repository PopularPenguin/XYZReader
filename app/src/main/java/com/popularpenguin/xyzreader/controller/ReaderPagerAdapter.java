package com.popularpenguin.xyzreader.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.popularpenguin.xyzreader.ui.DetailFragment;

/** The pager adapter for DetailActivity */
public class ReaderPagerAdapter extends FragmentStatePagerAdapter {

    private OnPageChangeListener mListener;

    public ReaderPagerAdapter(FragmentManager fm) {
        super(fm);

        mListener = new OnPageChangeListener();
    }

    @Override
    public Fragment getItem(int position) {
        return DetailFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return DbFetcher.getList().size();
    }

    public OnPageChangeListener getPageChangeListener() {
        return mListener;
    }

    public class OnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
