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

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.sanbong.R;
import com.sanbong.adapter.FindMatchAdapter;
import com.sanbong.model.Match;

import java.util.ArrayList;

/**
 * Created by Diep_Chelsea on 13/07/2016.
 */
public class FindMatchFragment extends Fragment implements FindMatchAdapter.MatchClickInterface {
    public static final String TAG = "Find match";
    RecyclerView recyclerView;
    ArrayList<Match> listMatches;
    MaterialSpinner spinner_location,spinner_time;
    private ArrayList<String> arrayLocation, arrayTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_find_match, container, false);
        initSpinner();

        initView(rootView);

        FindMatchAdapter adapter = new FindMatchAdapter(getActivity(),initData());
        adapter.setMatchClickInterface(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
    public void initLicense()
    {
    }
    public void initView(View v)
    {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        spinner_location = (MaterialSpinner) v.findViewById(R.id.match_location);
        spinner_time = (MaterialSpinner) v.findViewById(R.id.match_time);
        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,arrayLocation);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,arrayTime);

        spinner_time.setAdapter(adapterTime);
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
        arrayTime = new ArrayList<String>();
        arrayTime.add("Sân cỏ nhân tạo");
        arrayTime.add("Sân cỏ tự nhiên");

    }
    public ArrayList<Match> initData()
    {
        listMatches = new ArrayList<>();
        listMatches.add(new Match("1","18h ngày 11-7","Chelsea FC","Stamford Bridge Stadium","Đá giao lưu","144 Xuân Thủy, Cầu Giấy","100k chia đôi"));
        listMatches.add(new Match("1","18h ngày 11-7","Chelsea FC","Stamford Bridge Stadium","Đá giao lưu","144 Xuân Thủy, Cầu Giấy","100k chia đôi"));
        listMatches.add(new Match("1","18h ngày 11-7","Chelsea FC","Stamford Bridge Stadium","Đá giao lưu","144 Xuân Thủy, Cầu Giấy","100k chia đôi"));
        listMatches.add(new Match("1","18h ngày 11-7","Chelsea FC","Stamford Bridge Stadium","Đá giao lưu","144 Xuân Thủy, Cầu Giấy","100k chia đôi"));
        listMatches.add(new Match("1","18h ngày 11-7","Chelsea FC","Stamford Bridge Stadium","Đá giao lưu","144 Xuân Thủy, Cầu Giấy","100k chia đôi"));
        return listMatches;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("onActivityCreated","onActivityCreated");
    }

    @Override
    public void onMatchClick() {
        Log.d("match click","received");
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new AcceptMatchFragment(),AcceptMatchFragment.TAG).commit();
    }
}

