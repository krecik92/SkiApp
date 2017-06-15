package com.pieprzyca.dawid.skiapp.data;

import org.json.JSONObject;

/**
 * Created by Dawid on 01.06.2016.
 */
public class Units implements JSONPopulator{
    private String temperature;
    public String getTemperature() {
        return temperature;
    }
    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
