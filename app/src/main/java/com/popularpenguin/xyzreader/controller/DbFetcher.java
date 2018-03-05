package com.popularpenguin.xyzreader.controller;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.popularpenguin.xyzreader.data.AppDatabase;
import com.popularpenguin.xyzreader.data.Article;

import java.util.ArrayList;
import java.util.List;

/** Singleton to get the database */
public class DbFetcher {
    private static DbFetcher mFetcher;
    private static AppDatabase mDb;
    private static List<Article> mArticles;

    public static AppDatabase get(Context ctx) {
        if (mFetcher == null) {
            mFetcher = new DbFetcher(ctx);
        }

        return mDb;
    }

    public static List<Article> getList() {
        if (mArticles == null) {
            return new ArrayList<>();
        }

        return mArticles;
    }

    public static void setList(@NonNull List<Article> articles) {
        mArticles = articles;
    }

    private DbFetcher(Context ctx) {
        mDb = Room.databaseBuilder(ctx.getApplicationContext(), AppDatabase.class, "articles")
                .build();
    }
}
