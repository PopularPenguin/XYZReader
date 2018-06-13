package com.popularpenguin.xyzreader.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/** Room's DAO for articles */
@Dao
public interface ArticleDao {
    // TODO: Wrap in LiveData
    @Query("SELECT * FROM articles")
    List<Article> getAll();

    @Query("SELECT * FROM articles WHERE id = :articleId LIMIT 1")
    Article loadById(long articleId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertArticles(List<Article> articles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertArticlesReplace(List<Article> articles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Article article);

    @Update
    int updateArticles(List<Article> articles);

    @Delete
    int deleteArticles(List<Article> articles);
}
