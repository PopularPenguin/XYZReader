package com.popularpenguin.xyzreader.controller;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.popularpenguin.xyzreader.ui.DetailFragment;

/** The pager adapter for DetailActivity */
public class ReaderPagerAdapter extends FragmentStatePagerAdapter {

    private Cursor mCursor;

    public ReaderPagerAdapter(FragmentManager fm, Cursor cursor) {
        super(fm);

        mCursor = cursor;
    }

    @Override
    public Fragment getItem(int position) {
        // mCursor.moveToPosition(position);
        return new DetailFragment();
    }

    @Override
    public int getCount() {
        return 0;
        // return (mCursor != null) ? mCursor.getCount() : 0;
    }
}
