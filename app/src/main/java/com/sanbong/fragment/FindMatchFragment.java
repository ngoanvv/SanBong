package com.sanbong.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sanbong.CONSTANT;
import com.sanbong.R;
import com.sanbong.adapter.FindMatchAdapter;
import com.sanbong.adapter.FindPitchAdapter;
import com.sanbong.dialog.AcceptMatchDialog;
import com.sanbong.dialog.OrderPitchDialog;
import com.sanbong.model.Match;
import com.sanbong.utils.ShowToask;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Diep_Chelsea on 13/07/2016.
 */
public class FindMatchFragment extends Fragment implements FindMatchAdapter.MatchClickInterface ,
        AcceptMatchDialog.onAcceptMatchClick, FindPitchAdapter.PitchClickInterface {
    public static final String TAG = "FindMatchFragment";
    private RecyclerView recyclerView;
    private ArrayList<Match> listMatches;
    private MaterialBetterSpinner spinner_location,spinner_time;
    private ArrayList<String> arrayLocation, arrayTime;
    private EditText edt_input_search;
    private FindMatchAdapter adapter;
    private DatabaseReference mDatabaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_find_match, container, false);
        new MyTask().doInBackground("");
        initView(rootView);
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
    private class MyTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {

            initData();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getActivity().getString(R.string.processing));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    public void initData()
    {
         listMatches= new ArrayList<>();
         mDatabaseReference = FirebaseDatabase.getInstance().getReference("matchs");
         mDatabaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                     Match match = new Match();
                     match.setHostID((String) messageSnapshot.child("hostID").getValue());
                     match.setDescription( (String) messageSnapshot.child("description").getValue());
                     match.setHostName((String) messageSnapshot.child("hostname").getValue());
                     match.setLocation((String) messageSnapshot.child("location").getValue());
                     match.setMoney((String) messageSnapshot.child("money").getValue());
                     match.setStadium( (String) messageSnapshot.child("stadium").getValue());
                     match.setTime((String) messageSnapshot.child("time").getValue());
                     listMatches.add(match);
                 }
                 adapter = new FindMatchAdapter(getActivity(),listMatches);
                 adapter.setMatchClickInterface(FindMatchFragment.this);
                 LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                 linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                 recyclerView.setLayoutManager(linearLayoutManager);
                 recyclerView.setAdapter(adapter);

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClickAcceptMatch(Match match) {
            AcceptMatchDialog dialog = new AcceptMatchDialog();
            dialog.setAcceptMatch(this);
            dialog.show(getFragmentManager(),TAG);
    }

    @Override
    public void onClickReadMore(Match match) {
        AcceptMatchFragment fragment = new AcceptMatchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CONSTANT.MATCH,match);
        Log.d(TAG,match.toString());
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container,fragment,AcceptMatchFragment.TAG).addToBackStack(null).commit();
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

