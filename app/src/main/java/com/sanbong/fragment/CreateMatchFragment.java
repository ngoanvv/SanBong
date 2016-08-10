package com.sanbong.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanbong.CONSTANT;
import com.sanbong.R;
import com.sanbong.model.Match;
import com.sanbong.model.UserModel;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Diep_Chelsea on 25/07/2016.
 */
public class CreateMatchFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = "CreateMatchFragment";
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private UserModel userModel;
    private EditText edt_stadium,edt_time,edt_money,edt_address,edt_description;
    private Button bt_create,bt_cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_create_match, container, false);
        initView(rootView);
        getUserModel();


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    public void getUserModel()
    {
        Intent intent = getActivity().getIntent();
        if(intent !=null)
        {
            userModel = (UserModel) intent.getSerializableExtra(CONSTANT.KEY_USER);
        }
        else
            userModel=null;
    }
    public void initView(View v)
    {
        edt_money = (EditText) v.findViewById(R.id.item_money);
        edt_time = (EditText) v.findViewById(R.id.item_time);
        edt_stadium = (EditText) v.findViewById(R.id.item_stadium);
        edt_address = (EditText) v.findViewById(R.id.item_address);
        edt_description = (EditText) v.findViewById(R.id.item_description);

        edt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        bt_cancel = (Button) v.findViewById(R.id.bt_cancel);
        bt_create = (Button) v.findViewById(R.id.bt_create);

        bt_cancel.setOnClickListener(this);
        bt_create.setOnClickListener(this);

    }
//    String id;
//    String time;
//    String hostName;
//    String stadium;
//    String description;
//    String location;
//    String money;
    public void showTimePicker(final int year, final int month, final int day)
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(view.isShown())
                {
                    String time = hourOfDay + ":" + minute + " ngày " + day + "-" + month + "-" + year;
                    edt_time.setText(time.toString());
                }
            }
        },Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE),true);
        timePickerDialog.show();
    }
    public void showDatePicker()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (view.isShown())
                {
                    Log.d(TAG,"year : "+year +" month :"+monthOfYear+" day"+dayOfMonth);
                    showTimePicker(year,monthOfYear,dayOfMonth);
                }
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public void createMatch()
    {
        String description,address,stadium,time,money;

        auth        = FirebaseAuth.getInstance();
        database    = FirebaseDatabase.getInstance();
        description = edt_description.getText().toString();
        time        = edt_time.getText().toString();
        money       = edt_money.getText().toString();
        address     = edt_address.getText().toString();
        stadium     = edt_stadium.getText().toString();

        Match match = new Match();
                match.setHostId(userModel.getId());
                match.setDescription(description);
                match.setHostName(userModel.getName());
                match.setLocation(address);
                match.setMoney(money);
                match.setStadium(stadium);
                match.setTime(time);
                match.setDescription(description);

        Log.d(TAG,match.toString());


        databaseReference = database.getReference().child("matchs");
        databaseReference.child("hostID").setValue(match.getHostId());
        databaseReference.child("hostname").setValue(match.getHostName());
        databaseReference.child("time").setValue(match.getTime());
        databaseReference.child("location").setValue(match.getLocation());
        databaseReference.child("stadium").setValue(match.getStadium());
        databaseReference.child("money").setValue(match.getMoney());
        databaseReference.child("description").setValue(match.getDescription());

        FindMatchFragment findMatchFragment = new FindMatchFragment();
        moveToFragment(findMatchFragment);


    }
    public void moveToFragment(Fragment fragment)
    {
        getActivity().getSupportFragmentManager().beginTransaction()
                     .replace(R.id.container,fragment).addToBackStack(null).commit();
    }

    public boolean validate() {
        boolean valid = true;
        String description,address,stadium,time,money;
        description = edt_description.getText().toString().replaceAll("\\s+", " ");;
        time        = edt_time.getText().toString().replaceAll("\\s+", " ");
        money       = edt_money.getText().toString().replaceAll("\\s+", " ");
        address     = edt_address.getText().toString().replaceAll("\\s+", " ");
        stadium     = edt_stadium.getText().toString().replaceAll("\\s+", " ");

        if (description.isEmpty() || description.length() < 6)
        {
            edt_description.setError(getString(R.string.invalid_length));
            valid = false;
        }
        else
        {
            edt_description.setError(null);
        }

        if (time.isEmpty() || time.length() < 6 )
        {
            edt_time.setError(getString(R.string.invalid_length));
            valid = false;
        }
        else
        {
            edt_time.setError(null);
        }
        if (money.isEmpty() || money.length() < 6 )
        {
            edt_money.setError(getString(R.string.invalid_length));
            valid = false;
        }
        else
        {
            edt_money.setError(null);
        }
        if (address.isEmpty() || address.length() < 6 )
        {
            edt_address.setError(getString(R.string.invalid_length));
            valid = false;
        }
        else
        {
            edt_address.setError(null);
        }
        if (stadium.isEmpty() || stadium.length() < 3 )
        {
            edt_stadium.setError(getString(R.string.invalid_length));
            valid = false;
        }
        else
        {
            edt_stadium.setError(null);
        }


        return valid;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.bt_cancel :
            {

                break;
            }
            case  R.id.bt_create:
            {
                if(validate())
                createMatch();
                break;
            }
        }
    }

}
