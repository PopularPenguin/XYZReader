package com.popularpenguin.xyzreader;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.popularpenguin.xyzreader.data.AppDatabase;
import com.popularpenguin.xyzreader.data.Article;
import com.popularpenguin.xyzreader.data.ArticleDao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/** Test the database's base functionality */
@RunWith(AndroidJUnit4.class)
public class RoomDbTest {
    private ArticleDao mArticleDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mArticleDao = mDb.getArticleDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void addArticleAndRead() throws Exception {
        Article article = new Article.Builder()
                .id(0)
                .title("Lord of the Rings")
                .author("J.R.R. Tolkien")
                .body("Insert long body of text here...")
                .thumbUrl("...")
                .photoUrl("...")
                .aspectRatio(1.0)
                .date("July 29, 1954")
                .build();

        mArticleDao.insert(article);
        Article byId = mArticleDao.loadById(0);

        assertThat(byId.getTitle(), equalTo(article.getTitle()));
    }
}
