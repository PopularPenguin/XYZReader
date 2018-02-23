package com.popularpenguin.xyzreader;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

/** Activity that displays content */
public class DetailActivity extends ReaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTransition();

        addFragment();
    }

    private void addFragment() {
        Bundle args = getIntent().getExtras();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.detail_container, DetailFragment.newInstance(args))
                .commit();
    }
}
