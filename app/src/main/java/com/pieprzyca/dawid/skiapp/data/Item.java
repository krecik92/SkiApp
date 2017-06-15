package com.pieprzyca.dawid.skiapp.data;

import org.json.JSONObject;

/**
 * Created by Dawid on 01.06.2016.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }
    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
