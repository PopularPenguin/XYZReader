package com.popularpenguin.xyzreader.controller;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.popularpenguin.xyzreader.data.AppDatabase;

/** Singleton to get the database */
public class DbFetcher {
    private static DbFetcher mFetcher;
    private static AppDatabase mDb;

    public static AppDatabase get(Context ctx) {
        if (mFetcher == null) {
            mFetcher = new DbFetcher(ctx);
        }

        return mDb;
    }

    private DbFetcher(Context ctx) {
        mDb = Room.databaseBuilder(ctx.getApplicationContext(), AppDatabase.class, "articles")
                .build();
    }
}
