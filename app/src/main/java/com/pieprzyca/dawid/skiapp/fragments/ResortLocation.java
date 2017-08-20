package com.pieprzyca.dawid.skiapp.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pieprzyca.dawid.skiapp.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dawid on 29.05.2016.
 *
 * Framgent odpowiadający za wyświetlania mapy z znacznikiem lokalizacji.
 */
public class ResortLocation extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment supportMapFragment;
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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fragmentManager = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        if (supportMapFragment == null) {
            supportMapFragment = new SupportMapFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.map, supportMapFragment);
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }
        supportMapFragment.getMapAsync(this);
    }

    /**
     *  getLocationFromAddress()
     *  This method get resort name from database and return LatLng object for it.
     */
    private LatLng getLocationFromAddress() {
        Geocoder coder = new Geocoder(getContext());
        LatLng localization = new LatLng(0.0, 0.0);
        List<Address> addressList;
        try {
            if (getActivity().getIntent().getStringExtra("resortAddress") == null) {
                addressList = null;
            } else {
                addressList = coder.getFromLocationName(getActivity().getIntent().getStringExtra("resortAddress"), 3);
            }

            if(addressList == null)
                return null;
            Log.d("ADDRESS LIST: ", addressList.toString());
            Address location = addressList.get(0);
            localization = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localization;
    }

    @Override
    public void onResume() {
        supportMapFragment.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (supportMapFragment != null) {
            getChildFragmentManager().beginTransaction().remove(supportMapFragment).commitAllowingStateLoss();
        }
        supportMapFragment = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        supportMapFragment.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.getUiSettings().setMyLocationButtonEnabled(false);
        //map.setMyLocationEnabled(true);
        MapsInitializer.initialize(this.getActivity());
        LatLng localization = getLocationFromAddress();

        // Updates the location and zoom of the MapView
        assert localization != null;
        map.addMarker(new MarkerOptions().position(localization).title(getActivity().getIntent().getStringExtra("resortName")));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(localization, 11);
        map.animateCamera(cameraUpdate);
    }
}