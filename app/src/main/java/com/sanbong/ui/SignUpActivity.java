package com.sanbong.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sanbong.R;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextView bt_signUp,bt_login;
    ArrayList<String> list_userType,list_userAdd;

    EditText edt_userName,edt_password,edt_rePassword,edt_userEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initData();
        initView();
        setListener();



    }
    public void initView()
    {
        edt_userName = (EditText) findViewById(R.id.input_name);
        edt_password = (EditText) findViewById(R.id.input_password);
        edt_rePassword = (EditText) findViewById(R.id.input_re_password);
        edt_userEmail = (EditText) findViewById(R.id.input_email);
        bt_signUp = (TextView) findViewById(R.id.btn_signUp);
        bt_login = (TextView) findViewById(R.id.link_login);

    }

    public void setListener()
    {
        bt_login.setOnClickListener(this);
        bt_signUp.setOnClickListener(this);
    }

    public void initData()
    {
        list_userAdd = new ArrayList<>();
        list_userType = new ArrayList<>();

        list_userAdd.add("Đông Anh");
        list_userAdd.add("Sóc Sơn");
        list_userAdd.add("Hà Nội");

        list_userType.add("Chủ sân");
        list_userType.add("Khách hàng");

    }
    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        bt_signUp.setEnabled(false);
        final Dialog dialog =  new MaterialDialog.Builder(this)
                .content("Creating....")
                .progress(true, 0)
                .show();

//        String name = _nameText.getText().toString();
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        dialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        bt_signUp.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        bt_signUp.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean validate() {
        boolean valid = true;

        String name = edt_userName.getText().toString();
        String email = edt_userEmail.getText().toString();
        String password = edt_password.getText().toString();
        String re_password = edt_rePassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            edt_userName.setError(getString(R.string.name_length));
            valid = false;
        } else {
            edt_userName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_userEmail.setError(getString(R.string.invalid_email));
            valid = false;
        } else {
            edt_userEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edt_password.setError(getString(R.string.pass_length));
            valid = false;
        } else {
            edt_password.setError(null);
        }
        if (re_password.isEmpty() || re_password.length() < 4 || re_password.length() > 10) {
            edt_rePassword.setError(getString(R.string.pass_length));
            valid = false;
        } else {
            edt_rePassword.setError(null);
        }
        if(re_password.equals(password))
        {
            valid=false;
            edt_rePassword.setError(getString(R.string.invalid_repassword));
            edt_password.setError(getString(R.string.invalid_repassword));
            edt_password.setText("");
            edt_password.setText("");

        }
        else
        {
            edt_rePassword.setError(null);
            edt_password.setError(null);
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.btn_signUp :
            {
                signup();
                break;
            }
            case R.id.btn_login :
            {
                onBackPressed();
                break;
            }
        }
    }
}
