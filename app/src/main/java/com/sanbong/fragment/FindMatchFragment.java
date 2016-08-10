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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanbong.R;
import com.sanbong.adapter.FindMatchAdapter;
import com.sanbong.adapter.FindPitchAdapter;
import com.sanbong.dialog.AcceptMatchDialog;
import com.sanbong.dialog.OrderPitchDialog;
import com.sanbong.model.Match;
import com.sanbong.utils.ShowToask;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

/**
 * Created by Diep_Chelsea on 13/07/2016.
 */
public class FindMatchFragment extends Fragment implements FindMatchAdapter.MatchClickInterface ,
        AcceptMatchDialog.onAcceptMatchClick, FindPitchAdapter.PitchClickInterface {
    public static final String TAG = "Find match";
    private RecyclerView recyclerView;
    private ArrayList<Match> listMatches;
    private MaterialBetterSpinner spinner_location,spinner_time;
    private ArrayList<String> arrayLocation, arrayTime;
    private EditText edt_input_search;
    private FindMatchAdapter adapter;
    private DatabaseReference mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_find_match, container, false);
//        initSpinner();
        initView(rootView);

        adapter = new FindMatchAdapter(getActivity(),initData());
        adapter.setMatchClickInterface(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void initView(View v)
    {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        edt_input_search = (EditText) v.findViewById(R.id.edt_input);
        edt_input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.initList();
                    adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        spinner_location = (MaterialBetterSpinner) v.findViewById(R.id.match_location);
//        spinner_time = (MaterialBetterSpinner) v.findViewById(R.id.match_time);
//        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,arrayLocation);
//        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,arrayTime);
//
//        spinner_time.setAdapter(adapterTime);
//        spinner_location.setAdapter(adapterLocation);


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
//        listMatches.add(new Match("1","18h ngày 11-7","Chelsea FC","FECON  Stadium","Đá giao lưu nhẹ nhàng, mong gặp đối thủ lâu dài thường xuyên, liên hệ số 0998989898","144 Xuân Thủy, Cầu Giấy","100k chia đôi"));
//        listMatches.add(new Match("1","18h ngày 12-7","MU FC","Old Traford Stadium","Đá giao lưu","144 Hồ tùng mậu , Cầu Giấy","100k chia đôi"));
//        listMatches.add(new Match("1","18h ngày 13-7","MC FC","ETIHAD Stadium","Đá giao lưu","144 Xuân Thủy, Cầu Giấy","100k chia đôi"));
//        listMatches.add(new Match("1","18h ngày 14-7","Arsenal FC","Emirates Bridge Stadium","Đá giao lưu","144 Xuân Thủy, Cầu Giấy","100k chia đôi"));
//        listMatches.add(new Match("1","18h ngày 11-7","Chelsea FC","Stamford Bridge Stadium","Đá giao lưu","144 Xuân Thủy, Cầu Giấy, Hà Nội","100k chia đôi"));


        return listMatches;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("onActivityCreated","onActivityCreated");
    }


    @Override
    public void onClickAcceptMatch() {
            AcceptMatchDialog dialog = new AcceptMatchDialog();
            dialog.setAcceptMatch(this);
            dialog.show(getFragmentManager(),TAG);
    }

    @Override
    public void onClickReadMore() {
        getActivity().getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container,new AcceptMatchFragment(),AcceptMatchFragment.TAG).addToBackStack(null).commit();
                }


    @Override
    public void onAcceptInDialog() {
        ShowToask.showToaskLong(getContext(),"Bạn đã nhận kèo bóng này");
    }

    @Override
    public void clickOrderNow() {
        OrderPitchDialog dialog = new OrderPitchDialog();
        dialog.show(getActivity().getSupportFragmentManager(),OrderPitchDialog.TAG);
    }

    @Override
    public void clickReadmore() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new PitchDetailFragment()).addToBackStack(null).commit();
    }
}

