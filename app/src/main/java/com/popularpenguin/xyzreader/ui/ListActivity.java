package com.popularpenguin.xyzreader.ui;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;

import com.popularpenguin.xyzreader.R;
import com.popularpenguin.xyzreader.controller.NetworkUtils;
import com.popularpenguin.xyzreader.controller.ReaderAdapter;
import com.popularpenguin.xyzreader.data.Article;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity that displays the article list
 */
public class ListActivity extends ReaderActivity implements
        ReaderAdapter.ReaderAdapterOnClickHandler {

    public static final String INTENT_EXTRA_ARTICLE = "article_id";

    @BindView(R.id.swipe_list)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.toolbar_list)
    Toolbar mToolbar;
    @BindView(R.id.app_bar_list)
    AppBarLayout mAppBar;
    @BindView(R.id.collapsing_toolbar_list)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private RecyclerView mRecyclerView; // Bind later in setupRecyclerView()
    private ReaderAdapter mAdapter;
    private List<Article> mArticles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ButterKnife.bind(this);

        // set the toolbar
        mToolbar = findViewById(R.id.toolbar_list);
        setSupportActionBar(mToolbar);

        setTransition();

        setRecyclerView();

        mRefreshLayout.setOnRefreshListener(() -> {
            if (mAdapter != null && NetworkUtils.isConnected(this)) {
                NetworkUtils.fetchArticles(this, mAdapter);
            } else {
                Snackbar.make(mRefreshLayout, R.string.snackbar, Snackbar.LENGTH_LONG)
                        .show();
            }

            mRefreshLayout.setRefreshing(false);
        });

        // Display text on app bar when it is totally collapsed
        // https://stackoverflow.com/questions/31662416/show-collapsingtoolbarlayout-title-only-when-collapsed
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShowing = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = mAppBar.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    String appName = getResources().getString(R.string.app_name);
                    mCollapsingToolbarLayout.setTitle(appName);
                    isShowing = true;
                } else if (isShowing) {
                    mCollapsingToolbarLayout.setTitle(" ");
                    isShowing = false;
                }
            }
        });
    }

    private void setRecyclerView() {
        if (mRecyclerView != null) {
            return;
        }

        mRecyclerView = findViewById(R.id.rv_list);
        mAdapter = new ReaderAdapter(this, mArticles, this);
        mRecyclerView.setAdapter(mAdapter);

        // populate the adapter with articles fetched from the network
        NetworkUtils.fetchArticles(this, mAdapter);
        if (!NetworkUtils.isConnected(this)) {
            Snackbar.make(mRecyclerView, R.string.snackbar, Snackbar.LENGTH_LONG)
                    .show();
        }

        int orientation = getWindowManager().getDefaultDisplay().getRotation();
        int spanCount;

        if (orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180) {
            spanCount = 2; // in portrait orientation RecyclerView has 2 columns
        } else {
            spanCount = 3; // in landscape orientation RecyclerView has 3 columns
        }

        RecyclerView.LayoutManager layoutManager =
                new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(int position) {
        Bundle animation = getTransitionAnimation();

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(INTENT_EXTRA_ARTICLE, position);

        startActivity(intent, animation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            AboutDialog.show(this);
        }

        return true;
    }
}
