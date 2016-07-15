package com.sanbong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanbong.R;

/**
 * Created by Diep_Chelsea on 15/07/2016.
 */
public class AcceptMatchFragment extends Fragment {
    public static final String TAG = "accept match";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_accept_match, container, false);
        initSpinner();
        initView(rootView);
        return rootView;
    }
    public void initView(View v)
    {

    }
    public void initSpinner()
    {

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("onActivityCreated","onActivityCreated");
    }

}
