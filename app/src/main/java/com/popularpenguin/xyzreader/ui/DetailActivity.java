package com.popularpenguin.xyzreader.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.popularpenguin.xyzreader.R;
import com.popularpenguin.xyzreader.controller.ReaderPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Activity that displays content */
public class DetailActivity extends ReaderActivity {

    @BindView(R.id.view_pager_detail) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setTransition();

        int position = getIntent().getIntExtra(ListActivity.INTENT_EXTRA_ARTICLE, 0);
        ReaderPagerAdapter pagerAdapter = new ReaderPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.addOnPageChangeListener(pagerAdapter.getPageChangeListener());
    }
}
