package com.pieprzyca.dawid.skiapp;

import android.content.Context;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dawid on 18.06.2017.
 */
public class DatabaseOperations {
    public static void fetchResortInfoFromDatabase(Context context, String url, final ResortInfoAdapter adapter) {
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
                });
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
}
