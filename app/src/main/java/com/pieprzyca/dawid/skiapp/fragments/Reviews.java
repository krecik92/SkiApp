package com.pieprzyca.dawid.skiapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.pieprzyca.dawid.skiapp.R;

import com.pieprzyca.dawid.skiapp.arrayAdapters.ReviewsAdapter;
import com.pieprzyca.dawid.skiapp.data.DatabaseConfig;
import com.pieprzyca.dawid.skiapp.data.ReviewsInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawid on 29.05.2016.
 */
public class Reviews extends ListFragment {
    ListView listView;
    ReviewsAdapter adapter;
    ArrayList<ReviewsInfo> ratingAndReviewsList = new ArrayList<>();
    public Reviews() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.reviews, container, false);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        listView = (ListView)rootView.findViewById(android.R.id.list);
        adapter = new ReviewsAdapter(getActivity(), android.R.layout.simple_list_item_1, ratingAndReviewsList);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        new DownloadSkiResortReviewsAndRatings().execute(DatabaseConfig.REVIEW_RATING_REQUEST_URL);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        adapter.clear();
        super.onDestroyView();
    }

    private class DownloadSkiResortReviewsAndRatings extends AsyncTask<String, Integer, Long> {
        @Override
        protected Long doInBackground(String... params) {
            RequestReviews requestReviews = new RequestReviews(getActivity().getIntent().getStringExtra("skiResortId") , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray result = jsonObject.getJSONArray(DatabaseConfig.JSON_ARRAY);
                        for(int arrayElement = 0; arrayElement != result.length(); arrayElement++) {
                            JSONObject element = result.getJSONObject(arrayElement);
                            ReviewsInfo reviewsInfo = new ReviewsInfo(element.getString("review"), element.getString("date"), element.getString("rating"));
                            adapter.add(reviewsInfo);
                        }
                        //adapter.addAll(ratingAndReviewsList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO: Add display information about failed data fetch
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(requestReviews);
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {}
    }
}