package com.popularpenguin.xyzreader.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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

    public Article(long id, String title, String author, String body, String thumbUrl,
                   String photoUrl, double aspectRatio, String date) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.body = body;
        this.thumbUrl = thumbUrl;
        this.photoUrl = photoUrl;
        this.aspectRatio = aspectRatio;
        this.date = parseDate(date);
    }

    /** Parse the date returned from the server into the correct format */
    private static String parseDate(String dateString) {
        try {
            SimpleDateFormat parser =
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss", Locale.US);
            SimpleDateFormat formatter;

            Date date = parser.parse(dateString);
            if (date.before(new Date(0))) {
                 formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            }
            else {
                formatter = new SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.US);
            }

            return formatter.format(date);
        }
        catch (ParseException e) {
            return dateString;
        }
    }

    /** Get a split up list for display in a RecyclerView */
    public List<String> getSplitBody() {
        String[] splitArray = body.split("\r\n\r\n");

        return Arrays.asList(splitArray);
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
        this.date = parseDate(date);
    }

    /** Article builder inner class */
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
            this.date = parseDate(date);

            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
}
