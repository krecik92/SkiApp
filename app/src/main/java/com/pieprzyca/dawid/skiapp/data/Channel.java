package com.pieprzyca.dawid.skiapp.data;

import org.json.JSONObject;

/**
 * Created by Dawid on 01.06.2016.
 */
public class Channel implements JSONPopulator {
    private Item item;
    private Units units;

    public Units getUnits() {
        return units;
    }

    public Item getItem() {
        return item;
    }
    @Override
    public void populate(JSONObject data) {
        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));
    }
}
