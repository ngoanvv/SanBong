package com.sanbong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanbong.R;
import com.sanbong.dialog.AcceptMatchDialog;
import com.sanbong.utils.ShowToask;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Diep_Chelsea on 15/07/2016.
 */
public class AcceptMatchFragment extends Fragment implements View.OnClickListener, AcceptMatchDialog.onAcceptMatchClick{
    public static final String TAG = "accept match";
    FancyButton bt_accept,bt_contact;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_accept_match, container, false);
        initView(rootView);
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

        bt_contact.setOnClickListener(this);
        bt_accept.setOnClickListener(this);

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
