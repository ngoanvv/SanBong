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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sanbong.CONSTANT;
import com.sanbong.MainActivity;
import com.sanbong.R;
import com.sanbong.model.UserModel;
import com.sanbong.utils.ShowToask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_signUp,bt_login,tv_forgot;
    EditText edt_email,edt_password;
    SharedPreferences sharedPreferences;
    FirebaseUser firebaseUser ;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth auth;
    UserModel userModel;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initDb();
        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        if(sharedPreferences!=null)   flash();

    }
    public void initDb()
    {
        database = FirebaseDatabase.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
//        auth.addAuthStateListener(authStateListener);
    }
    public void loginFirebase(String email, final String password)
    {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("loginFirebase", "signInWithEmail:failed", task.getException());
                        }
                        else
                        {

                            Log.d("loginFirebase", "signInWithEmail:isSuccess", task.getException());
                            firebaseUser = task.getResult().getUser();
                            // get data from db
                            databaseReference = database.getReference("users").child(firebaseUser.getUid());

                            userModel = new UserModel();
                            userModel.setEmail(firebaseUser.getEmail());
                            userModel.setId(firebaseUser.getUid());
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Log.d("data-json",dataSnapshot.getValue().toString());
                                    userModel.setImageURL(dataSnapshot.child("imageURL").getValue().toString());
                                    userModel.setPhone(dataSnapshot.child("phone").getValue().toString());
                                    userModel.setName(dataSnapshot.child("name").getValue().toString());
                                    userModel.setUserType(dataSnapshot.child("type").getValue().toString());
                                    onLoginSuccess(userModel,password);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                        ShowToask.showToaskLong(LoginActivity.this,"Không thể kết nối máy chủ");
                                }
                            });

                        }
                        // ...
                    }
                });
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//            }
//        };
    }
    @Override
    public void onStop() {
        super.onStop();
//        if (authStateListener != null) {
//            auth.removeAuthStateListener(authStateListener);
//        }
    }
    public void flash()
    {
        dialog = new MaterialDialog.Builder(this)
                .content("Đang đăng nhập....")
                .progress(true, 0)
                .show();
        auth = FirebaseAuth.getInstance();
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


    private void onLoginSuccess(UserModel userModel,String password)
    {
        dialog.dismiss();
        saveUserData(userModel.getEmail(),password,userModel.getUserType());
        Intent intent= new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra(CONSTANT.USER_TYPE,userModel.getUserType());
        intent.putExtra(CONSTANT.KEY_USER,userModel);
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

                if (!validate(edt_email.getText().toString(),edt_password.getText().toString())) {
                        onLoginFailed();
                        break;
                }
                else {

                    dialog = new MaterialDialog.Builder(this)
                            .content("Đang đăng nhập....")
                            .progress(true, 0)
                            .show();
                    loginFirebase(edt_email.getText().toString(), edt_password.getText().toString());
                    break;
                }
            }
            case R.id.link_forgot :
            {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                break;
            }
        }
    }
}
