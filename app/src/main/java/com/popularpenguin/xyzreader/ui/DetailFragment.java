package com.popularpenguin.xyzreader.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularpenguin.xyzreader.R;
import com.popularpenguin.xyzreader.controller.DbFetcher;
import com.popularpenguin.xyzreader.data.Article;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    @BindView(R.id.iv_detail) ImageView mPhotoView;
    @BindView(R.id.tv_title_detail) TextView mTitleView;
    @BindView(R.id.tv_content_detail) TextView mContentView;

    private long mId; // The article's id
    private Article mArticle;

    public static DetailFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(ListActivity.INTENT_EXTRA_ARTICLE, id);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        setRetainInstance(true);

        ButterKnife.bind(this, view);

        mId = getArguments().getLong(ListActivity.INTENT_EXTRA_ARTICLE);
        new LoadDbTask().execute();

        // Toolbar toolbar = view.findViewById(R.id.toolbar);
        // ((ReaderActivity) getActivity()).setSupportActionBar(toolbar);

        FloatingActionButton fab = view.findViewById(R.id.fab_share);
        fab.setOnClickListener(v ->
                Snackbar.make(v, "Add a share action", 1000).show());

        return view;
    }

    // TODO: Fix collapsing toolbar to show options menu at top while collapsed
    // https://www.journaldev.com/13927/android-collapsingtoolbarlayout-example

    private class LoadDbTask extends AsyncTask<Void, Void, Article> {

        @Override
        protected Article doInBackground(Void... voids) {
            return DbFetcher.get(getContext())
                    .getArticleDao()
                    .loadById(mId);
        }

        @Override
        protected void onPostExecute(Article article) {
            mArticle = article;

            Picasso.with(getContext())
                    .load(mArticle.getPhotoUrl())
                    .placeholder(R.drawable.error)
                    .error(R.drawable.error)
                    .into(mPhotoView);

            mTitleView.setText(mArticle.getTitle());
            mContentView.setText(mArticle.getBody());
        }
    }
}
