package com.sanbong.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sanbong.R;


/**
 * Created by Diep_Chelsea on 29/06/2016.
 */
public class LogOutDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "DeleteOffer";
    public Button bt_ok,bt_cancel;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context mContext = getActivity();
        Dialog dialog = new CustomDialog(mContext);
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog, null);

        bt_ok = (Button) view.findViewById(R.id.id_dialog_ok);
        bt_cancel = (Button) view.findViewById(R.id.id_dialog_cancel);

        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);

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
        mLogoutInterface.onLogOutEvent();
    }

    LogoutInterface mLogoutInterface;
    public void setLogoutInterface(LogoutInterface mLogoutInterface2)
    {
        mLogoutInterface=mLogoutInterface2;
    }
    public interface LogoutInterface
    {
        void onLogOutEvent();

    }

}
