package com.pieprzyca.dawid.skiapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pieprzyca.dawid.skiapp.R;

/**
 * Created by Dawid on 29.05.2016.
 */
public class ResortLocation extends Fragment {
    private MapView mapView;
    private GoogleMap map;

    public ResortLocation() {
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
        View view = inflater.inflate(R.layout.resortlocation, container, false);
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        //map.setMyLocationEnabled(true);

        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        LatLng jaworzyna = new LatLng(49.420770, 20.925286);
        map.addMarker(new MarkerOptions().position(jaworzyna).title("Stacja Narciarska Jaworzyna Krynicka"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(jaworzyna, 11);
        map.animateCamera(cameraUpdate);


        return view;
    }
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}