package com.popularpenguin.xyzreader.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.popularpenguin.xyzreader.data.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/** Network utility class to check for Internet connectivity and parsing JSON from the server */
class NetworkUtils {

    private static final String JSON_URL = "https://go.udacity.com/xyz-reader-json";

    /**
     * Check for a network connection
     * @param ctx The context
     * @return is there network connectivity?
     */
    static boolean isConnected(Context ctx) {
        // Check if there is an active network connection
        ConnectivityManager cm = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getActiveNetworkInfo();

        return info != null && info.isConnectedOrConnecting();
    }

    /**
     * Fetch the articles from the network
     * @param ctx The context
     * @return The list of articles to display
     */
    static List<Article> getArticles(Context ctx) {
        // Check for a network connection, if there is none, return an empty list
        if (!isConnected(ctx)) {
            return new ArrayList<>();
        }

        List<Article> articles = null;

        try {
            articles = parseJSON(getJson(JSON_URL));
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return articles;
    }

    /**
     * Request JSON from server and return them as a String
     * @param url The HTTP URL
     * @return JSON String from the URL
     * @throws IOException
     */
    private static String getJson(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    /**
     * @param jsonString The String returned from getJson(String url)
     * @return A list of Article objects parsed from the JSON
     * @throws JSONException
     */
    private static List<Article> parseJSON(String jsonString) throws JSONException {
        JSONArray results = new JSONArray(jsonString);

        List<Article> articleList = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {
            JSONObject o = results.getJSONObject(i);

            Article article = new Article.Builder()
                    .id(o.getLong("id"))
                    .title(o.getString("title"))
                    .author(o.getString("author"))
                    .body(o.getString("body"))
                    .thumbUrl(o.getString("thumb"))
                    .photoUrl(o.getString("photo"))
                    .aspectRatio(o.getDouble("aspect_ratio"))
                    .date(o.getString("published_date"))
                    .build();

            articleList.add(article);
        }

        return articleList;
    }
}
