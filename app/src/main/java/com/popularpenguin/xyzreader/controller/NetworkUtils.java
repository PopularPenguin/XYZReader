package com.popularpenguin.xyzreader.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.popularpenguin.xyzreader.data.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
                List<Article> articles = DbFetcher.get(ctx).getArticleDao().getAll();
                DbFetcher.setList(articles);
                adapter.setArticles(articles);
            });

            return;
        }

        RequestQueue queue = Volley.newRequestQueue(ctx);
        StringRequest request = new StringRequest(Request.Method.GET,
                JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Article> articles = parseJSON(response);
                            adapter.setArticles(articles);
                            DbFetcher.setList(articles);

                            AppExecutors.get()
                                    .diskIO()
                                    .execute(() -> DbFetcher.get(ctx)
                                            .getArticleDao()
                                            .insertArticlesReplace(articles));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
     * @throws JSONException
     */
    private static List<Article> parseJSON(String jsonString) throws JSONException {
        JSONArray results = new JSONArray(jsonString);

        List<Article> articleList = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {
            JSONObject o = results.getJSONObject(i);

            float aspectRatio = Float.parseFloat(o.getString("aspect_ratio"));

            Article article = new Article.Builder()
                    .id(o.getLong("id"))
                    .title(o.getString("title"))
                    .author(o.getString("author"))
                    .body(o.getString("body"))
                    .thumbUrl(o.getString("thumb"))
                    .photoUrl(o.getString("photo"))
                    .aspectRatio(aspectRatio)
                    .date(o.getString("published_date"))
                    .build();

            articleList.add(article);
        }

        return articleList;
    }
}
