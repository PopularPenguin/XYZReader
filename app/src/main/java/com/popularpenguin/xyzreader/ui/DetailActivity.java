package com.popularpenguin.xyzreader.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.popularpenguin.xyzreader.R;
import com.popularpenguin.xyzreader.controller.ReaderPagerAdapter;

/** Activity that displays content */
public class DetailActivity extends ReaderActivity {

    private ViewPager mViewPager;
    private ReaderPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTransition();

        /*
        mPagerAdapter = new ReaderPagerAdapter(getSupportFragmentManager(), null);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mPagerAdapter);
        */

        addFragment(R.id.detail_container, new DetailFragment());
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
