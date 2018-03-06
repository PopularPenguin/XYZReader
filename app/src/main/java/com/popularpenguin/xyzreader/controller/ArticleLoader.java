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

    /* If there network connectivity, fetch the articles and rewrite the database
    Else, load the data from the current database */
    @Override
    public List<Article> loadInBackground() {
        Context ctx = getContext();
        List<Article> articles;

        if (NetworkUtils.isConnected(getContext())) {
            articles = NetworkUtils.getArticles(ctx);

            DbFetcher.get(ctx)
                    .getArticleDao()
                    .insertArticlesReplace(articles);
        }
        else {
            articles = DbFetcher.get(ctx)
                    .getArticleDao()
                    .getAll();
        }

        DbFetcher.setList(articles); // add ArrayList of articles into singleton

        return articles;
    }
}
