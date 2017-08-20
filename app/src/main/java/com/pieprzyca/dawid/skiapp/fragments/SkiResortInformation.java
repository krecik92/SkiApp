package com.pieprzyca.dawid.skiapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pieprzyca.dawid.skiapp.R;

/**
 * Created by Dawid on 29.05.2016.
 */
public class SkiResortInformation extends Fragment {
    String[] openAndClose = {"otwarty", "zamknięty"};
    private TextView statusText;
    private TextView numberOfRoutes;
    private TextView numberOfLifts;
    private TextView snow;
    public SkiResortInformation() {
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

        View view =  inflater.inflate(R.layout.skiresortinformation, container, false);
        statusText = (TextView)view.findViewById(R.id.changesStatus);
        statusText.setText(openAndClose[1]);

        if(statusText.getText() == "otwarty")
            statusText.setTextColor(Color.GREEN);
        else{
            statusText.setTextColor(Color.RED);
        }

        numberOfRoutes = (TextView) view.findViewById(R.id.numberOfRoutes);
        numberOfRoutes.setText("9");
        numberOfLifts = (TextView) view.findViewById(R.id.numberOfLifts);
        numberOfLifts.setText("8");
        snow = (TextView) view.findViewById(R.id.snow);
        snow.setText("0 cm");
        return view;
    }
}