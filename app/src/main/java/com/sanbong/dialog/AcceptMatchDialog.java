package com.sanbong.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sanbong.R;

/**
 * Created by Diep_Chelsea on 25/07/2016.
 */
public class AcceptMatchDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "DeleteOffer";
    public Button bt_ok,bt_cancel;
    private TextView title;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context mContext = getActivity();
        Dialog dialog = new CustomDialog(mContext);
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog, null);


        title = (TextView) view.findViewById(R.id.title);
        bt_ok = (Button) view.findViewById(R.id.id_dialog_ok);
        bt_cancel = (Button) view.findViewById(R.id.id_dialog_cancel);

        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        title.setText(getString(R.string.accept_match));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        return dialog;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_dialog_ok:
                handleOkEvent();
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

    private void handleOkEvent() {
        matchClick.onAcceptInDialog();
        dismiss();
    }
    public onAcceptMatchClick matchClick;
    public void setAcceptMatch(onAcceptMatchClick m)
    {
        this.matchClick=m;
    }
    public interface onAcceptMatchClick
    {
        void onAcceptInDialog();
    }
}
