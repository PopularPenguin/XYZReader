package com.popularpenguin.xyzreader.controller;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.popularpenguin.xyzreader.data.AppDatabase;
import com.popularpenguin.xyzreader.data.Article;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Network utility class to check for Internet connectivity and parsing JSON from the server
 */
public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String JSON_URL = "https://go.udacity.com/xyz-reader-json";
    private static final String TEST_URL =
            "https://raw.githubusercontent.com/TNTest/xyzreader/master/data.json";

    /**
     * Check for a network connection
     *
     * @param ctx The context
     * @return is there network connectivity?
     */
    public static boolean isConnected(Context ctx) {
        // Check if there is an active network connection
        ConnectivityManager cm = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getActiveNetworkInfo();

        return info != null && info.isConnectedOrConnecting();
    }

    public static void fetchArticles(Context ctx, ReaderAdapter adapter) {
        if (!isConnected(ctx)) {
            AppExecutors.get().diskIO().execute(() -> {
                List<Article> articles = AppDatabase.get(ctx).dao().getAll();
                AppDatabase.setList(articles);
                adapter.setArticles(articles);
                adapter.notifyDataSetChanged();
            });

            return;
        }

        RequestQueue queue = Volley.newRequestQueue(ctx);
        StringRequest request = new StringRequest(Request.Method.GET,
                JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Article> articles = parseJSON(response);
                        adapter.setArticles(articles);
                        adapter.notifyDataSetChanged();
                        AppDatabase.setList(articles);

                        AppExecutors.get()
                                .diskIO()
                                .execute(() -> AppDatabase.get(ctx)
                                        .dao()
                                        .insertArticlesReplace(articles));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // ...
            }
        });

        queue.add(request);
    }

    /**
     * @param jsonString The String returned from getJson(String url)
     * @return A list of Article objects parsed from the JSON
     */
    private static List<Article> parseJSON(String jsonString) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<Article>>(){}.getType();
        Collection<Article> articles = gson.fromJson(jsonString, collectionType);

        return new ArrayList<>(articles);
    }
}
