package com.popularpenguin.xyzreader.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static AppDatabase db;
    private static List<Article> articleList;

    public abstract ArticleDao dao();

    public static AppDatabase get(Context context) {
        if (db == null) {
            synchronized (LOCK) {
                db = Room.databaseBuilder(context, AppDatabase.class, "db").build();
            }
        }

        return db;
    }

    public static List<Article> getList() {
        if (articleList == null) {
            return new ArrayList<>();
        }

        return articleList;
    }

    public static void setList(@NonNull List<Article> articles) {
        articleList = articles;
    }
}
