package com.sanbong.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sanbong.R;

/**
 * Created by poliveira on 11/03/2015.
 */
public class MyMapFragment extends Fragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {
    public static final String TAG = "stats";
    GoogleMap map;
    MapFragment  fragment;
    Location myLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_map, container, false);
        fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_v2);
        fragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isLocationEnabled(getActivity())) {
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void addMarker()
    {
        map.addMarker( new MarkerOptions().position(new LatLng(21.025764,105.81134160)));
        map.addMarker( new MarkerOptions().position(new LatLng(21.0237642,105.8334160)));
        map.addMarker( new MarkerOptions().position(new LatLng(21.0277643,105.8534160)));
        map.addMarker( new MarkerOptions().position(new LatLng(21.029977644,105.8634160)));
        moveCamera(new LatLng(21.029977644,105.8634160));
        map.setOnMarkerClickListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (map == null) {
        }
    }
    public void moveCamera(LatLng latLng)
    {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        map.animateCamera(cameraUpdate);
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        {

        }
        return false;
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    public void showCircle(LatLng latLng,double radius)
    {
        map.clear();
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng)
                     .center(latLng)
                     .clickable(true)
                     .strokeColor(Color.CYAN)
                     .fillColor(Color.CYAN)
                     .radius(radius);
        map.addCircle(circleOptions);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        moveCamera(new LatLng(21.029977644,105.8634160));
        map.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(LatLng latLng) {
        showCircle(latLng,1000);
    }
}
