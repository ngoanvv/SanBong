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
import com.sanbong.dialog.OrderPitchDialog;
import com.sanbong.model.Pitch;
import com.sanbong.utils.ShowToask;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

/**
 * Created by Diep_Chelsea on 26/07/2016.
 */
public class HotPitchFragment extends Fragment implements FindPitchAdapter.PitchClickInterface ,
        OrderPitchDialog.OrderPitchDialogInterface {
 public static final String TAG = "Find match";
    RecyclerView recyclerView;
    ArrayList<Pitch> listPitches;
    MaterialBetterSpinner spinner_location;
    private ArrayList<String> arrayLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_find_pitch, container, false);

        initSpinner();
        initView(rootView);

        FindPitchAdapter adapter = new FindPitchAdapter(getActivity(),initData());
        adapter.setmPitchClickInterface(this);

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

    @Override
    public void clickOrderNow() {

        OrderPitchDialog dialog = new OrderPitchDialog();
        dialog.setPitchDialogInterface(this);
        dialog.show(getActivity().getSupportFragmentManager(),FindPitchFragment.TAG);

    }

    @Override
    public void clickReadmore() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new PitchDetailFragment(), PitchDetailFragment.TAG).addToBackStack(null).commit();
    }

    @Override
    public void onOrderClickDialog() {
        ShowToask.showToaskLong(getContext(),"Bạn đã đặt sân này");
    }
}
