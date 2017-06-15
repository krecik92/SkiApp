package com.pieprzyca.dawid.skiapp.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.pieprzyca.dawid.skiapp.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Dawid on 01.06.2016.
 */
public class YahooWheatherService {
    private WheatherServiceCallback callback;
    private String location;
    private Exception error;
    public YahooWheatherService(WheatherServiceCallback callback) {
        this.callback = callback;
    }
    public String getLocation(){
        return location;
    }
    public void refreshWheather(final String l){
        this.location = l;
        new AsyncTask<String, Void, String>(){
            @Override
            protected String doInBackground(String... strings){
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'", strings[0]);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    return result.toString();
                } catch (Exception e){
                    error = e;
                }

                return null;
            }
            @Override
            protected void onPostExecute(String s){
                if(s == null && error != null){
                    callback.serviceFailure(error);
                    return;
                }
                try {
                    JSONObject data = new JSONObject(s);
                    JSONObject queryResult = data.optJSONObject("query");
                    int count = queryResult.optInt("count");
                    if(count == 0){
                        callback.serviceFailure(new LocationWeatherException("No wheather information found for" + location));
                        return;
                    }
                    Channel channel = new Channel();
                    channel.populate(queryResult.optJSONObject("results").optJSONObject("channel"));

                    callback.serviceSuccess(channel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(location);
    }
}
class LocationWeatherException extends Exception{
    public LocationWeatherException(String detailMessage){
        super(detailMessage);
    }
}
