package com.sanbong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sanbong.CONSTANT;
import com.sanbong.R;
import com.sanbong.dialog.AcceptMatchDialog;
import com.sanbong.model.Match;
import com.sanbong.utils.ShowToask;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Diep_Chelsea on 15/07/2016.
 */
public class AcceptMatchFragment extends Fragment implements View.OnClickListener, AcceptMatchDialog.onAcceptMatchClick{
    public static final String TAG = "accept match";
    FancyButton bt_accept,bt_contact;
    TextView tv_description,tv_address,tv_stadium,tv_time,tv_money,tv_hostName;
    Match match;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_accept_match, container, false);
        initView(rootView);
        initData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        handleBackPress();

    }

    public void initView(View v)
    {
        bt_accept = (FancyButton) v.findViewById(R.id.bt_accept);
        bt_contact = (FancyButton) v.findViewById(R.id.bt_contact);
        tv_address = (TextView) v.findViewById(R.id.item_location);
        tv_description = (TextView) v.findViewById(R.id.item_description);
        tv_hostName = (TextView) v.findViewById(R.id.item_hostName);
        tv_money = (TextView) v.findViewById(R.id.item_money);
        tv_stadium = (TextView) v.findViewById(R.id.item_stadium);
        tv_time = (TextView) v.findViewById(R.id.item_time);

        bt_contact.setOnClickListener(this);
        bt_accept.setOnClickListener(this);

    }
    public void initData()
    {
        if(getArguments() != null) {
            match = (Match) getArguments().getSerializable(CONSTANT.MATCH);
            tv_time.setText(match.getTime());
            tv_description.setText(match.getDescription());
//            tv_address.setText(match.getLocation());
            tv_money.setText(match.getMoney());
            tv_stadium.setText(match.getStadium());
            tv_hostName.setText(match.getHostName());

        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("onActivityCreated","onActivityCreated");
    }
    public void handleBackPress() {

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,new FindMatchFragment(),FindMatchFragment.TAG)
                            .addToBackStack(null)
                            .commit();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.bt_accept :
            {
                AcceptMatchDialog dialog = new AcceptMatchDialog();
                dialog.setAcceptMatch(this);
                dialog.show(getFragmentManager(),TAG);
                break;
            }
            case  R.id.bt_contact:
            {
                break;
            }
        }
    }

    @Override
    public void onAcceptInDialog() {
        ShowToask.showToaskLong(getContext(),"Bạn đã nhận kèo bóng này");
    }
}
