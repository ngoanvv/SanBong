package com.sanbong.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sanbong.R;
import com.sanbong.utils.ShowToask;

/**
 * Created by poliveira on 11/03/2015.
 */
public class MyMapFragment extends Fragment implements GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener, LocationSource.OnLocationChangedListener {
    public static final String TAG = "mapfragment";
    public static final int LOCATION_REFRESH_TIME = 10000;
    public static final int LOCATION_REFRESH_DISTANCE = 10000;


    LocationListener locationListener;
    GoogleMap map;
    MapFragment fragment;
    Location myLocation;
    LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_map, container, false);
        fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_v2);
        fragment.getMapAsync(this);
        return view;
    }


    public void showGpsSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getActivity().startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public void showInternetSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Network settings");
        alertDialog.setMessage("Network is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
                getActivity().startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    public void addMarker() {
        map.addMarker(new MarkerOptions().position(new LatLng(21.025764, 105.81134160)));
        map.addMarker(new MarkerOptions().position(new LatLng(21.0237642, 105.8334160)));
        map.addMarker(new MarkerOptions().position(new LatLng(21.0277643, 105.8534160)));
        map.addMarker(new MarkerOptions().position(new LatLng(21.029977644, 105.8634160)));
        moveCamera(new LatLng(21.029977644, 105.8634160));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (map == null) {
        }
    }

    public void moveCamera(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12  );
        map.animateCamera(cameraUpdate);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        {

        }
        return false;
    }

    public void isGPSEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try
        {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(network_enabled == false) showInternetSettingsAlert();
            else
            {
                if(gps_enabled==false) showGpsSettingsAlert();
                else
                {

                }
            }



        }
        catch (Exception ex)
        {

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
        isGPSEnabled(getActivity());

        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        showCircle(latLng,1000);
    }

    @Override
    public void onLocationChanged(Location location) {
        ShowToask.showToaskLong(getActivity(),"TING TING");
        showCircle(new LatLng(location.getLatitude(),location.getLongitude()),1000);

    }
}
