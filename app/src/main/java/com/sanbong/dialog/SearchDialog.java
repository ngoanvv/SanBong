package com.sanbong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.sanbong.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

/**
 * Created by Diep_Chelsea on 27/06/2016.
 */
public class SearchDialog extends DialogFragment implements View.OnClickListener{
    public static final String TAG = "DeleteOffer";
    private ArrayList<String> arraySpinner;
    private ArrayList<String> arraySpinner2;
    private Button searchBt;
    private  Button cancelBtn;
    private Boolean addressed,typed;
    private  MaterialBetterSpinner spinner_address,spinner_type;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context mContext = getActivity();
        Dialog dialog = new CustomDialog(mContext);
        addressed=false;
        typed=false;

        initData();

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_search, null);
        searchBt = (Button) view.findViewById(R.id.id_dialog_ok);
        cancelBtn = (Button) view.findViewById(R.id.id_dialog_cancel);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,arraySpinner);
        spinner_address = (MaterialBetterSpinner)
                view.findViewById(R.id.search_address);
        spinner_address.setAdapter(arrayAdapter);
        spinner_type = (MaterialBetterSpinner)
                view.findViewById(R.id.search_type);


        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,arraySpinner2);
        spinner_type.setAdapter(arrayAdapter2);
        setListener();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        return dialog;
    }

    public void setListener()
    {
        spinner_address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        searchBt.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    public void initData()
    {
        arraySpinner = new ArrayList<String>();
        arraySpinner.add("Quận số 1");
        arraySpinner.add("Quận số 2");
        arraySpinner.add("Quận số 3");
        arraySpinner.add("Quận số 4");
        arraySpinner.add("Quận số 5");
        arraySpinner2 = new ArrayList<String>();
        arraySpinner2.add("Sân cỏ nhân tạo");
        arraySpinner2.add("Sân cỏ tự nhiên");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_dialog_ok:
                handleSearchEvent();
                break;
            case R.id.id_dialog_cancel:
                handleCancelEvent();
                break;
            default:
                break;
        }
    }

    private void handleCancelEvent() {
        dismiss();
    }

    private void handleSearchEvent() {
//        if(addressed && typed) {
        dismiss();
        mSearchEventInterface.search();
//        }
//        else
//        {
//            new MaterialDialog.Builder(getActivity())
//                    .content(getActivity().getString(R.string.empty_search))
//                    .positiveText("OK")
//                    .show();
//        }
    }

    public SearchEventInterface mSearchEventInterface;
    public void setmSearchEventInterface(SearchEventInterface onSearchEventInterface)
    {
        this.mSearchEventInterface = onSearchEventInterface;
    }

    public interface SearchEventInterface
    {
        public void search();
    }

}
