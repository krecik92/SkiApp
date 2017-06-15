package com.pieprzyca.dawid.skiapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pieprzyca.dawid.skiapp.R;

/**
 * Created by Dawid on 29.05.2016.
 */
public class SkiRouteMap extends Fragment {

    public SkiRouteMap() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.skiroutemap, container, false);
    }

}