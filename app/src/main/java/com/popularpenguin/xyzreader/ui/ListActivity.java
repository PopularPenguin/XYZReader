package com.popularpenguin.xyzreader.ui;

import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

    private static final String INTENT_EXTRA_ARTICLE = "article";

    @BindView(R.id.rv_list) RecyclerView mRecyclerView;

    private ReaderAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Article> mArticles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        setTransition();

        // TODO: Load from ArticleLoader instead
        mArticles.add(new Article(0,
                "Test Article 1",
                "January 22, 2018",
                "by Plato"));
        mArticles.add(new Article(1,
                "Test Article 2",
                "February 22, 2018",
                "by Issac Newton"));
        mArticles.add(new Article(2,
                "Test Article 3",
                "March 18, 2015",
                "by Mark Twain"));
        mArticles.add(new Article(3,
                "Test Article 4",
                "May 09, 2011",
                "by William Shakespeare"));
        mArticles.add(new Article(4,
                "Test Article 5",
                "December 25, 2010",
                "by H.P. Lovecraft"));
        mArticles.add(new Article(5,
                "Test Article 6",
                "August 30, 2012",
                "by Henry David Thoreau"));
        mArticles.add(new Article(6,
                "Test Article 7",
                "June 29, 2001",
                "by Albert Einstein"));

        setupRecyclerView();

        // TODO: Add Loader (separate class)
    }

    private void setupRecyclerView() {
        mAdapter = new ReaderAdapter(this, mArticles, this);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new GridLayoutManager(this,
                2,
                LinearLayoutManager.VERTICAL,
                false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(Article article) {
        Bundle animation = getTransitionAnimation();

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(INTENT_EXTRA_ARTICLE, article.getId());

        startActivity(intent, animation);
    }

    /** Loader callbacks */
    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle bundle) {
        return new ArticleLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        if (data == null || data.isEmpty()) {
            Snackbar.make(mRecyclerView, "No data to show", Snackbar.LENGTH_LONG).show();

            mRecyclerView.setVisibility(View.GONE);
        }
        else {
            mArticles = data;

            setupRecyclerView();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        // Not implemented
    }
}
