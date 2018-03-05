package com.popularpenguin.xyzreader.controller;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.popularpenguin.xyzreader.data.Article;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    private Context ctx;

    public ArticleLoader(Context ctx) {
        super(ctx);

        this.ctx = ctx;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        List<Article> articles = NetworkUtils.getArticles(ctx);
        DbFetcher.get(ctx)
                .getArticleDao()
                .insertArticlesReplace(articles);

        ctx = null;

        return articles;
    }
}
