package com.sanbong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sanbong.R;
import com.sanbong.adapter.FindPitchAdapter;
import com.sanbong.dialog.OrderPitchDialog;
import com.sanbong.model.Pitch;
import com.sanbong.utils.ShowToask;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

/**
 * Created by Diep_Chelsea on 18/07/2016.
 */
public class FindPitchFragment extends Fragment implements FindPitchAdapter.PitchClickInterface , OrderPitchDialog.OrderPitchDialogInterface{
    public static final String TAG = "Find match";
    private RecyclerView recyclerView;
    private ArrayList<Pitch> listPitches;
    private EditText input_search;
    private FindPitchAdapter adapter;
    private MaterialBetterSpinner spinner_location;
    private ArrayList<String> arrayLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_find_pitch, container, false);
        initView(rootView);

        adapter = new FindPitchAdapter(getActivity(),initData());
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
        input_search = (EditText) v.findViewById(R.id.edt_input);

        input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    adapter.initList();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public ArrayList<Pitch> initData()
    {
        listPitches = new ArrayList<>();
        listPitches.add(new Pitch("id","id","600k 2 tiếng","a bc ","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        listPitches.add(new Pitch("id","id","600k 2 tiếng","d e f","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        listPitches.add(new Pitch("id","id","600k 2 tiếng","acx","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
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
