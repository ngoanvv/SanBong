package com.sanbong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sanbong.R;
import com.sanbong.ui.PitchFragment;

/**
 * Created by poliveira on 11/03/2015.
 */
public class MyMapFragment extends Fragment implements GoogleMap.OnMarkerClickListener {
    public static final String TAG = "stats";
    private SupportMapFragment fragment;
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_map_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_v2);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_v2, fragment).commit();
        }
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
            map = fragment.getMap();
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
            PitchFragment pitchFragment = new PitchFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,pitchFragment,TAG).commit();
            Log.d("marker",marker.getPosition().latitude+":"+marker.getPosition().longitude);
        }
        return false;
    }
}
