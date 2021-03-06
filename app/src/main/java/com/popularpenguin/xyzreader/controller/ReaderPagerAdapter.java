package com.popularpenguin.xyzreader.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.popularpenguin.xyzreader.data.AppDatabase;
import com.popularpenguin.xyzreader.ui.DetailFragment;

import java.util.ArrayList;
import java.util.List;

/** The pager adapter for DetailActivity */
public class ReaderPagerAdapter extends FragmentStatePagerAdapter {

    private OnPageChangeListener mListener;
    private List<DetailFragment> mFragmentList = new ArrayList<>();

    public ReaderPagerAdapter(FragmentManager fm) {
        super(fm);

        mListener = new OnPageChangeListener();

        int size = AppDatabase.getList().size();
        for (int i = 0; i < size; i++) {
            mFragmentList.add(DetailFragment.newInstance(i));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return AppDatabase.getList().size();
    }

    public OnPageChangeListener getPageChangeListener() {
        return mListener;
    }

    /** Class needed to fix up button functionality when swiping to a new view page */
    // https://looksok.wordpress.com/2013/11/02/viewpager-with-detailed-fragment-lifecycle-onresumefragment-including-source-code/
    public class OnPageChangeListener implements ViewPager.OnPageChangeListener {

        int currentPosition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int newPosition) {
            FragmentLifecycle fragmentShown =
                    (FragmentLifecycle) ReaderPagerAdapter.this.getItem(newPosition);
            fragmentShown.onResumeFragment();

            FragmentLifecycle fragmentHidden =
                    (FragmentLifecycle) ReaderPagerAdapter.this.getItem(currentPosition);
            fragmentHidden.onPauseFragment();

            currentPosition = newPosition;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public interface FragmentLifecycle {
        void onPauseFragment();
        void onResumeFragment();
    }
}
