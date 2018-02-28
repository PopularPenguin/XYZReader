package com.popularpenguin.xyzreader.data;

/** Class representing an individual article */
public class Article {

    private long mId;
    private String mTitle;
    private String mDate;
    private String mAuthor;
    private String mThumbPath;
    private String mPhotoPath;
    private String mBody;

    public Article(long id, String title, String date, String author) {
        mId = id;
        mTitle = title;
        mDate = date;
        mAuthor = author;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getThumbPath() {
        return mThumbPath;
    }

    public void setThumbPath(String thumbPath) {
        mThumbPath = thumbPath;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    /** Artile builder inner class */
    public class Builder {

        public Article mArticle;

        public Builder(Article article) {
            mArticle = article;
        }

        public Article addBody(String body) {
            mArticle.setBody(body);

            return mArticle;
        }
    }
}
