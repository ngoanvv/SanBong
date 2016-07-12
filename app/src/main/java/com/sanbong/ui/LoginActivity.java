package com.sanbong.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sanbong.R;
import com.sanbong.utils.ShowToask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_signUp,bt_login;
    EditText edt_email,edt_password;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        if(sharedPreferences!=null)
        flash();
    }


    public void flash()
    {

            String email = sharedPreferences.getString("email","null");
            String password = sharedPreferences.getString("password", "null");
            if(email.equals("null")&& password.equals("null"))
            {
                edt_email.setText("");
                edt_password.setText("");
            }
            else
            {
                edt_email.setText(email);
                edt_password.setText(password);
                Log.d("email/password",email+"/"+password);
                login(email,password);

        }


    }
    public void saveUserData(String email,String password)
    {
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        Log.d("email/pass",email+"/"+password);
        editor.commit();

    }
    public void initView()
    {
        tv_signUp = (TextView) findViewById(R.id.link_signup);
        bt_login= (TextView) findViewById(R.id.btn_login);
        edt_password = (EditText) findViewById(R.id.input_password);
        edt_email = (EditText) findViewById(R.id.input_email);
        bt_login.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);
    }
    public boolean validate(String email,String password) {
        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email.setError(getString(R.string.invalid_email));
            valid = false;
        } else {
            edt_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            edt_password.setError(getString(R.string.pass_length));
            valid = false;
        } else {
            edt_password.setError(null);
        }

        return valid;
    }

    public void onLoginFailed() {

        ShowToask.showToaskLong(LoginActivity.this,getString(R.string.logout)+" "+getString(R.string.failed));

    }
    public void login(String email,String password) {

        if (!validate(email,password)) {
            onLoginFailed();
            return;
        }


        final Dialog dialog =  new MaterialDialog.Builder(this)
                .content("Authenticating....")
                .progress(true, 0)
                .show();

        if(email.equals("diep@gmail.com")&&password.equals("123456"))
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        dialog.dismiss();
                    }
                }, 3000);
    }

    private void onLoginSuccess() {
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();
        saveUserData(email,password);
        startActivity(new Intent(LoginActivity.this,MainActivity.class));

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) 
        {
            case R.id.link_signup : 
            {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                break;
            }
            case R.id.btn_login :
            {
                login(edt_email.getText().toString(),edt_password.getText().toString());
                break;
            }
        }
    }
}
