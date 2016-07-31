package com.sanbong.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sanbong.MainActivity;
import com.sanbong.R;
import com.sanbong.model.UserModel;
import com.sanbong.utils.ShowToask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_signUp,bt_login,tv_forgot;
    EditText edt_email,edt_password;
    SharedPreferences sharedPreferences;
    AuthCredential credential;
    FirebaseUser firebaseUser ;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            initView();
        auth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        if(sharedPreferences!=null)   flash();




    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
    public void loginFirebase(String email, String password)
    {

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("loginFirebase", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("loginFirebase", "signInWithEmail:failed", task.getException());
                        }
                        else
                        {

                        }
                        // ...
                    }
                });
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                Log.d("email",firebaseUser.getEmail());
                Log.d("id",firebaseUser.getUid());
            }
        };
    }
    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }
    public void flash()
    {

        String email = sharedPreferences.getString("email","null");
        String password = sharedPreferences.getString("password", "null");
        String userType = sharedPreferences.getString("userType", "null");

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
            login(email,password,userType);
            loginFirebase(email,password);
        }


    }
    public void saveUserData(String email,String password,String userType)
    {
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        editor.putString("userType",userType);
        Log.d("info",email+"/"+password+"/"+userType);
        editor.commit();

    }
    public void initView()
    {
        tv_signUp = (TextView) findViewById(R.id.link_signup);
        tv_forgot = (TextView) findViewById(R.id.link_forgot);
        bt_login= (TextView) findViewById(R.id.btn_login);
        edt_password = (EditText) findViewById(R.id.input_password);
        edt_email = (EditText) findViewById(R.id.input_email);
        bt_login.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);
        tv_forgot.setOnClickListener(this);
    }
    public boolean validate(String email,String password) {
        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email.setError(getString(R.string.invalid_email));
            valid = false;
        } else {
            edt_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 1 || password.length() > 20) {
            edt_password.setError(getString(R.string.pass_length));
            valid = false;
        } else {
            edt_password.setError(null);
        }

        return valid;
    }

    public void onLoginFailed() {

        ShowToask.showToaskLong(LoginActivity.this,getString(R.string.login)+" "+getString(R.string.failed));

    }
    public void login(String email,String password,String userType) {

        if (!validate(email,password)) {
            onLoginFailed();
            return;
        }


        final Dialog dialog =  new MaterialDialog.Builder(this)
                .content("Đang đăng nhập....")
                .progress(true, 0)
                .show();

        if(email.equals("team@gmail.com")&&password.equals("1"))
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            onLoginSuccess(UserModel.TYPE_TEAM);
                            // onLoginFailed();
                            dialog.dismiss();
                        }
                    }, 3000);
        if(email.equals("owner@gmail.com")&&password.equals("1"))
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            onLoginSuccess(UserModel.TYPE_OWNER);
                            // onLoginFailed();
                            dialog.dismiss();
                        }
                    }, 3000);
    }

    private void onLoginSuccess(String userType)
    {
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();
        saveUserData(email,password,userType);
        Intent intent= new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("userType",userType);
        startActivity(intent);
        finish();
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
                if(edt_email.getText().toString().contains("team"))
                    login(edt_email.getText().toString(),edt_password.getText().toString(),UserModel.TYPE_TEAM);
                else
                    login(edt_email.getText().toString(),edt_password.getText().toString(),UserModel.TYPE_OWNER);

                break;
            }
            case R.id.link_forgot :
            {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                break;
            }
        }
    }
}
