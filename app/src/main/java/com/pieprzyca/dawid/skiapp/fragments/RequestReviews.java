package com.pieprzyca.dawid.skiapp.fragments;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.pieprzyca.dawid.skiapp.data.DatabaseConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dawid on 17.04.2017.
 */
public class RequestReviews extends StringRequest{
    private Map<String,String> params;

    public RequestReviews(String skiResortId, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, DatabaseConfig.REVIEW_RATING_REQUEST_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("skiResortId", skiResortId);
    }

    /**
     * getParams()
     * @return params from labels.
     */
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
