package com.sanbong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sanbong.R;
import com.sanbong.adapter.FindPitchAdapter;
import com.sanbong.model.Pitch;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

/**
 * Created by Diep_Chelsea on 18/07/2016.
 */
public class FindPitchFragment extends Fragment {
    public static final String TAG = "Find match";
    RecyclerView recyclerView;
    ArrayList<Pitch> listPitches;
    MaterialBetterSpinner spinner_location,spinner_time;
    private ArrayList<String> arrayLocation, arrayTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_find_pitch, container, false);

        initSpinner();
        initView(rootView);

        FindPitchAdapter adapter = new FindPitchAdapter(getActivity(),initData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
    public void initView(View v)
    {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        spinner_location = (MaterialBetterSpinner) v.findViewById(R.id.match_location);
        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,arrayLocation);

        spinner_location.setAdapter(adapterLocation);


    }
    public void initSpinner()
    {
        arrayLocation = new ArrayList<String>();
        arrayLocation.add("Quận số 1");
        arrayLocation.add("Quận số 2");
        arrayLocation.add("Quận số 3");
        arrayLocation.add("Quận số 4");
        arrayLocation.add("Quận số 5");

    }
    public ArrayList<Pitch> initData()
    {
        listPitches = new ArrayList<>();
        listPitches.add(new Pitch("id","id","600k 2 tiếng","Sân bóng FECON","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        listPitches.add(new Pitch("id","id","600k 2 tiếng","Sân bóng FECON","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        listPitches.add(new Pitch("id","id","600k 2 tiếng","Sân bóng FECON","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        listPitches.add(new Pitch("id","id","600k 2 tiếng","Sân bóng FECON","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        listPitches.add(new Pitch("id","id","600k 2 tiếng","Sân bóng FECON","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        listPitches.add(new Pitch("id","id","600k 2 tiếng","Sân bóng FECON","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        return listPitches;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("onActivityCreated","onActivityCreated");
    }
}
