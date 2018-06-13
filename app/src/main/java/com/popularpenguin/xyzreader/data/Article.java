package com.popularpenguin.xyzreader.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
    private String thumb;
    private String photo;
    @SerializedName("aspect_ratio")
    private float aspectRatio;
    @SerializedName("published_date")
    private String date;

    public Article() { }

    public Article(long id, String title, String author, String body, String thumbUrl,
                   String photoUrl, float aspectRatio, String date) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.body = body;
        this.thumb = thumbUrl;
        this.photo = photoUrl;
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
                 formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
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

    public String getThumb() {
        return thumb;
    }
    public void setThumb(String thumbUrl) {
        this.thumb = thumbUrl;
    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photoUrl) {
        this.photo = photoUrl;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }
    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getDate() {
        return parseDate(date);
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%d, %s, %s, %s, %s, %s, %f, %s",
                id, title, author, body.substring(0, 9) + "...",
                thumb, photo, aspectRatio, date);
    }
}
