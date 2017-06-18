package com.pieprzyca.dawid.skiapp;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.pieprzyca.dawid.skiapp.data.DatabaseConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Dawid on 18.06.2017.
 */
public class FetchFromDatabase {
     public StringRequest getDataFromDatabase(String url, final List<String> stringList, final boolean isFav) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(isFav == true){
                    showJSONwithFavourite(response,stringList);
                }else{
                    showJSON(response, stringList);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(Home.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        return stringRequest;
    }
    private void showJSONwithFavourite(String response, List<String> stringList){
        String name = "";
        String isFavourite = "0";
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(DatabaseConfig.JSON_ARRAY);
            for(int x=0; x<result.length(); x++){
                JSONObject collegeData = result.getJSONObject(x);
                isFavourite = collegeData.getString(DatabaseConfig.KEY_IS_FAVOURITE);
                if(isFavourite.equals("1")){
                    name= collegeData.getString(DatabaseConfig.KEY_NAME);
                    Log.d("Database", name);
                    stringList.add(name);
                }else {
                    Log.d("Database", "isNotFavourtie");
                }
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
    private void showJSON(String response, List<String> stringList){
        String name = "";
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(DatabaseConfig.JSON_ARRAY);
            for(int x=0; x<result.length(); x++){
                JSONObject collegeData = result.getJSONObject(x);
                name= collegeData.getString(DatabaseConfig.KEY_NAME);
                Log.d("Database", name);
                stringList.add(name);
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
}
