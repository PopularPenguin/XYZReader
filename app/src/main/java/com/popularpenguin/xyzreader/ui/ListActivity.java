package com.popularpenguin.xyzreader.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Surface;

import com.popularpenguin.xyzreader.R;
import com.popularpenguin.xyzreader.controller.ArticleLoader;
import com.popularpenguin.xyzreader.controller.ReaderAdapter;
import com.popularpenguin.xyzreader.data.Article;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Activity that displays the article list */
public class ListActivity extends ReaderActivity implements
        ReaderAdapter.ReaderAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<List<Article>> {

    public static final String INTENT_EXTRA_ARTICLE = "article_id";

    @BindView(R.id.swipe_list) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.toolbar_list) Toolbar mToolbar;
    @BindView(R.id.app_bar_list) AppBarLayout mAppBar;
    @BindView(R.id.collapsing_toolbar_list) CollapsingToolbarLayout mCollapsingToolbarLayout;

    private RecyclerView mRecyclerView; // Bind later in setupRecyclerView()
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

        getSupportLoaderManager().initLoader(0, null, this);

        mRefreshLayout.setOnRefreshListener(() -> {
            getSupportLoaderManager().restartLoader(0, null, this);

            if (mRecyclerView != null) {
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
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
                }
                else if (isShowing) {
                    mCollapsingToolbarLayout.setTitle(" ");
                    isShowing = false;
                }
            }
        });
    }

    private void setupRecyclerView() {
        if (mRecyclerView != null) {
            return;
        }

        mRecyclerView = findViewById(R.id.rv_list);
        ReaderAdapter adapter = new ReaderAdapter(this, mArticles, this);
        mRecyclerView.setAdapter(adapter);

        int orientation = getWindowManager().getDefaultDisplay().getRotation();
        int spanCount;

        if (orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180) {
            spanCount = 2; // in portrait orientation RecyclerView has 2 columns
        }
        else {
            spanCount = 3; // in landscape orientation RecyclerView has 3 columns
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,
                spanCount,
                LinearLayoutManager.VERTICAL,
                false);

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

    /** Loader callbacks */
    @NonNull
    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle bundle) {
        return new ArticleLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Article>> loader, List<Article> data) {
        if (data != null && !data.isEmpty()) {
            mArticles = data;

            setupRecyclerView();

            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Article>> loader) {
        // Not implemented
    }
}
