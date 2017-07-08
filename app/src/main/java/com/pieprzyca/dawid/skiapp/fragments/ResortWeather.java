package com.pieprzyca.dawid.skiapp.fragments;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pieprzyca.dawid.skiapp.R;
import com.pieprzyca.dawid.skiapp.data.Channel;
import com.pieprzyca.dawid.skiapp.data.Item;
import com.pieprzyca.dawid.skiapp.service.WheatherServiceCallback;
import com.pieprzyca.dawid.skiapp.service.YahooWheatherService;

/**
 * Created by Dawid on 29.05.2016.
 */
public class ResortWeather extends Fragment implements WheatherServiceCallback {

    private ImageView wheatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private YahooWheatherService service;
    private ProgressDialog dialog;

    public ResortWeather() {
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
        View view =  inflater.inflate(R.layout.resortwheather, container, false);
        wheatherIconImageView = (ImageView)view.findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView)view.findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView)view.findViewById(R.id.conditionTextView);
        locationTextView = (TextView)view.findViewById(R.id.locationTextView);

        service = new YahooWheatherService(this);
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.show();

        service.refreshWheather(getActivity().getIntent().getStringExtra("resortName") + ", Polska");
        return view;
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();
        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/icon" + item.getCondition().getCode(), null, getContext().getPackageName());

        @SuppressWarnings("deprecation")
        Drawable wheatherIconDrawable = getResources().getDrawable(resourceId);

        wheatherIconImageView.setImageDrawable(wheatherIconDrawable);
        temperatureTextView.setText(item.getCondition().getTemperature()+"\u00B0"+channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescripiton());
        locationTextView.setText(service.getLocation());

    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}