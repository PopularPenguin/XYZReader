package com.popularpenguin.xyzreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.popularpenguin.xyzreader.R;
import com.popularpenguin.xyzreader.controller.DbFetcher;
import com.popularpenguin.xyzreader.controller.ReaderPagerAdapter;
import com.popularpenguin.xyzreader.controller.TextAdapter;
import com.popularpenguin.xyzreader.data.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment implements ReaderPagerAdapter.FragmentLifecycle {

    @BindView(R.id.iv_detail) ImageView mPhotoView;
    @BindView(R.id.rv_content_detail) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_detail) Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar_detail) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.fab_share) FloatingActionButton fab;

    private DetailActivity mActivity;
    private Article mArticle;

    public static DetailFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ListActivity.INTENT_EXTRA_ARTICLE, position);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        setRetainInstance(true);

        ButterKnife.bind(this, view);

        mActivity = (DetailActivity) getActivity();

        // set up toolbar for first time
        if (mActivity.getSupportActionBar() == null) {
            setupToolbar();
        }

        // The article's position in the list
        int position = getArguments().getInt(ListActivity.INTENT_EXTRA_ARTICLE);
        mArticle = DbFetcher.getList().get((position));

        Picasso.with(getContext())
                .load(mArticle.getPhotoUrl())
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .into(mPhotoView);

        mCollapsingToolbarLayout.setTitle(mArticle.getTitle());

        fab.setOnClickListener(v -> shareArticle(mArticle));

        setupRecyclerView(mArticle.getSplitBody());

        return view;
    }

    private void setupToolbar() {
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onPauseFragment() {
        // not implemented
    }

    @Override
    public void onResumeFragment() {
        // set up toolbar again when fragment is paged to and resumed
        setupToolbar();
    }

    /**
     * Set the RecyclerView on the article's body
     */
    private void setupRecyclerView(List<String> body) {
        TextAdapter adapter = new TextAdapter(mActivity, body);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    /**
     * Share the title and author of the article
     */
    private void shareArticle(Article article) {
        Intent chooser = ShareCompat.IntentBuilder.from(mActivity)
                .setType("text/plain")
                .setText(String.format("%s - %s", article.getTitle(), article.getAuthor()))
                .getIntent();

        Intent intent = Intent.createChooser(chooser, getString(R.string.action_share));

        startActivity(intent);
    }
}
