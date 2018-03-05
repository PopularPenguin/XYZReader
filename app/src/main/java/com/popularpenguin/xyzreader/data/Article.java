package com.popularpenguin.xyzreader.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/** Class representing an individual article */
@Entity(tableName = "articles")
public class Article {
    @PrimaryKey
    private long id;

    private String title;
    private String author;
    private String body;
    private String thumbUrl;
    private String photoUrl;
    private double aspectRatio;
    private String date;

    Article(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.author = b.author;
        this.body = b.body;
        this.thumbUrl = b.thumbUrl;
        this.photoUrl = b.photoUrl;
        this.aspectRatio = b.aspectRatio;
        this.date = b.date;
    }

    // TODO: Remove this constructor later
    public Article(long id, String title, String date, String author) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.author = author;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }
    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }
    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    /** Artile builder inner class */
    public static class Builder {
        private long id;
        private String title;
        private String author;
        private String body;
        private String thumbUrl;
        private String photoUrl;
        private double aspectRatio;
        private String date;

        public Builder id(long id) {
            this.id = id;

            return this;
        }

        public Builder title(String title) {
            this.title = title;

            return this;
        }

        public Builder author(String author) {
            this.author = author;

            return this;
        }

        public Builder body(String body) {
            this.body = body;

            return this;
        }

        public Builder thumbUrl(String url) {
            thumbUrl = url;

            return this;
        }

        public Builder photoUrl(String url) {
            photoUrl = url;

            return this;
        }

        public Builder aspectRatio(double ratio) {
            aspectRatio = ratio;

            return this;
        }

        public Builder date(String date) {
            this.date = date;

            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
}
