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
public class OrderPitchDialog extends DialogFragment implements View.OnClickListener{

    public static final String TAG = "DeleteOffer";
    public Button bt_ok,bt_cancel;
    public TextView title;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context mContext = getActivity();
        Dialog dialog = new CustomDialog(mContext);
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog, null);

        bt_ok = (Button) view.findViewById(R.id.id_dialog_ok);
        bt_cancel = (Button) view.findViewById(R.id.id_dialog_cancel);
        title = (TextView) view.findViewById(R.id.title);

        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        title.setText(getString(R.string.order_pitch));


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
        pitchDialogInterface.onOrderClickDialog();
        dismiss();
    }
    public OrderPitchDialogInterface pitchDialogInterface;
    public void setPitchDialogInterface(OrderPitchDialogInterface orderPitchDialogInterface)
    {
        this.pitchDialogInterface = orderPitchDialogInterface;
    }
    public interface OrderPitchDialogInterface
    {
        void onOrderClickDialog();

    }
}
