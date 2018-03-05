package com.popularpenguin.xyzreader.ui;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.popularpenguin.xyzreader.R;
import com.popularpenguin.xyzreader.controller.ReaderPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Activity that displays content */
public class DetailActivity extends ReaderActivity {

    @BindView(R.id.view_pager_detail) ViewPager mViewPager;

    private ReaderPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setTransition();

        int position = getIntent().getIntExtra(ListActivity.INTENT_EXTRA_ARTICLE, 0);
        mPagerAdapter = new ReaderPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(mPagerAdapter.getPageChangeListener());
        mViewPager.setCurrentItem(position);

        /*
        int position = getIntent().getIntExtra(ListActivity.INTENT_EXTRA_ARTICLE, -1);
        addFragment(R.id.detail_container, DetailFragment.newInstance(position));
        */
    }

    // TODO: Delete menu methods and layout files if I don't use them
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.action_share:
                // TODO: Delete this code and point to real share action
                Toast.makeText(this, "Add a share action", Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
