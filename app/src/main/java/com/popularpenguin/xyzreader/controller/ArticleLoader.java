package com.popularpenguin.xyzreader.controller;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.popularpenguin.xyzreader.data.Article;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    public ArticleLoader(Context ctx) {
        super(ctx);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        return NetworkUtils.getArticles(getContext());
    }
}
