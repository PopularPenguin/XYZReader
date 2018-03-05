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

    public static final String INTENT_EXTRA_ARTICLE = "article_id";

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

        getSupportLoaderManager().initLoader(0, null, this);

        setupRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: Solve crash when resuming activity after back is pressed
        getSupportLoaderManager().restartLoader(0, null, this);
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
