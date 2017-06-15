package com.pieprzyca.dawid.skiapp.data;

import org.json.JSONObject;

/**
 * Created by Dawid on 01.06.2016.
 */
public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private String descripiton;
    public String getDescripiton() {
        return descripiton;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getCode() {
        return code;
    }

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temperature = data.optInt("temp");
        descripiton = data.optString("text");
    }
}
