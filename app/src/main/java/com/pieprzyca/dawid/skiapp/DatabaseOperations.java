package com.pieprzyca.dawid.skiapp;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pieprzyca.dawid.skiapp.arrayAdapters.ResortInfoAdapter;
import com.pieprzyca.dawid.skiapp.data.DatabaseConfig;
import com.pieprzyca.dawid.skiapp.data.ResortData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dawid on 18.06.2017.
 * Klasa definiująca wszystkie operacje na bazie danych z poziomu aplikacji. W większości wykorzystująca biblioteke Volley.
 */
public class DatabaseOperations {
    public static void fetchResortsFromDatabase(Context context, String url, final String userId, final ResortInfoAdapter adapter) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AddResortDataObjectToAdapter(response, adapter);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: React on error response
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private static void AddResortDataObjectToAdapter(String response, ResortInfoAdapter adapter) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(DatabaseConfig.JSON_ARRAY);
            for (int x = 0; x < result.length(); x++) {
                JSONObject collegeData = result.getJSONObject(x);
                ResortData resortData = new ResortData( collegeData.getInt(DatabaseConfig.SKI_R_ID),
                    collegeData.getString(DatabaseConfig.SKIRESORTS_KEY_NAME),
                    collegeData.getString(DatabaseConfig.SKIRESORTS_KEY_ADDRESS));
                adapter.add(resortData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void postNewReview(final Context context, String url, final String skiResortId, final String review, final Float ratingBar) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Log.d("RESULT: ", "SUCCESS");
                } else {
                    Log.d("RESULT: ", "FAILED");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Error
                Log.d("onErrorResponse: ", "FAILED");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("skiResortId", skiResortId);
                params.put("review", review);
                params.put("rating", String.valueOf(ratingBar));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public static void pullFavouriteResortInfo(final Context context, String url, final String skiResortId, final String userId, final MenuItem item) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    setFavouriteIcon(response, item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Error
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("skiResortId", skiResortId);
                params.put("userId", userId);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public static void addFavouriteResort(final Context context, String url, final String skiResortId, final String userId, final MenuItem item) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Log.d("RESULT ADD FAV: ", "SUCCESS");
                    item.setIcon(R.drawable.ic_star_white_24dp);
                } else {
                    Log.d("RESULT REMOVE FAV: ", "FAILED");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Error
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("skiResortId", skiResortId);
                params.put("userId", userId);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public static void deleteFavouriteResort(final Context context, String url, final String skiResortId, final String userId, final MenuItem item) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    Log.d("RESULT ADD FAV: ", "SUCCESS");
                    item.setIcon(R.drawable.ic_star_border_white_24dp);
                } else if (response.contains("failed")) {
                    Log.d("RESULT REMOVE FAV: ", "FAILED");
                } else {
                    Log.d("RESULT REMOVE FAV: ", "FAILED AFTER LOOP");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Error
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("skiResortId", skiResortId);
                params.put("userId", userId);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private static void setFavouriteIcon(String response, MenuItem item) throws JSONException {
        JSONObject jsonResponse = new JSONObject(response);
        if (jsonResponse.getBoolean("success")) {
            Log.d("RESULT FAV: ", "SUCCESS");
            item.setIcon(R.drawable.ic_star_white_24dp);
        } else {
            Log.d("RESULT FAV: ", "FAILED");
            item.setIcon(R.drawable.ic_star_border_white_24dp);
        }
    }
}
