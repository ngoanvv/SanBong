package com.sanbong.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanbong.CONSTANT;
import com.sanbong.MainActivity;
import com.sanbong.R;
import com.sanbong.model.UserModel;
import com.sanbong.utils.ShowToask;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextView bt_signUp,bt_login;
    ArrayList<String> list_userType,list_userAdd;
    static String TAG = "SignUpActivity";
    EditText edt_userName,edt_password,edt_rePassword,edt_userEmail;
    EditText edt_phone;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authListener;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RadioGroup groupUsertype;
    String userType=UserModel.TYPE_TEAM;
    DatabaseReference reference;
    UserModel userModel;
    private Dialog dialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        initDb();
        initView();
        setListener();

    }

    public void initDb()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
    public void firebaseLicense()
    {
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        };
    }
    public void initView()
    {
        groupUsertype = (RadioGroup) findViewById(R.id.radio_userType);
        edt_userName = (EditText) findViewById(R.id.input_name);
        edt_password = (EditText) findViewById(R.id.input_password);
        edt_rePassword = (EditText) findViewById(R.id.input_re_password);
        edt_userEmail = (EditText) findViewById(R.id.input_email);
        edt_phone = (EditText) findViewById(R.id.input_phone);
        bt_signUp = (TextView) findViewById(R.id.btn_signUp);
        bt_login = (TextView) findViewById(R.id.link_login);

    }

    public void setListener()
    {
        bt_login.setOnClickListener(this);
        bt_signUp.setOnClickListener(this);
        groupUsertype.check(R.id.radio_team);
        groupUsertype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId==R.id.radio_owner)
                    {
                        userType = UserModel.TYPE_OWNER;
                        Log.d("owner","click");
                    }
                    if(checkedId==R.id.radio_team)
                    {
                        userType = UserModel.TYPE_TEAM;
                        Log.d("team","click");
                    }
            }
        });
    }
    public void insertDatabase(UserModel userModel)
    {
            firebaseAuth = FirebaseAuth.getInstance();
            reference = firebaseDatabase.getReference("users");
            reference= reference.child(userModel.getId()).getRef();
            reference.child("name").setValue(userModel.getName());
            reference.child("type").setValue(userModel.getUserType());
            reference.child("phone").setValue(userModel.getPhone());
            reference.child("imageURL").setValue(userModel.getImageURL());
            reference.child("password").setValue(userModel.getPassword());

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

        String name = edt_userName.getText().toString();
        String email = edt_userEmail.getText().toString();
        String password = edt_password.getText().toString();
        String phone = edt_phone.getText().toString();

        createAccount(email,password,name,phone,userType);

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
        if(!re_password.equals(password))
        {
            valid=false;
            edt_rePassword.setError(getString(R.string.invalid_repassword));
            edt_password.setError(getString(R.string.invalid_repassword));
            edt_rePassword.setText("");
            edt_password.setText("");

        }
        else
        {
            edt_rePassword.setError(null);
            edt_password.setError(null);
        }
        if(edt_phone.length()<8)
        {
            valid=false;
            edt_phone.setError(getString(R.string.invalid_repassword));
            edt_phone.setText("");

        }
        else
        {
            edt_phone.setError(null);
            edt_phone.setError(null);
        }
        return valid;
    }
    private void createAccount(final String email, final String password,final  String name, final String phone, final String userType) {
        showProgressDialog();
        // [START create_user_with_email]
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        //nếu tạo user thành công
                        if(task.isSuccessful())
                        {
                            loginFirebase(email,password,name,phone,userType);
                        }
                        else
                        {
                            ShowToask.showToaskLong(SignUpActivity.this,task.isSuccessful()+" ");
                        }
                    }
                });
        // [END create_user_with_email]
    }
    public void loginFirebase(String email, final String password, final String name, final String phone, final String userType)
    {
        dialog.setTitle("Đang đăng nhập");
        dialog.show();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("loginFirebase", "signInWithEmail:failed", task.getException());
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setMessage("Không thể tạo tài khoản, vui lòng thử lại sau");
                            builder.setCancelable(true);
                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();
                        }
                        else
                        {
                            Log.d("loginFirebase", "signInWithEmail:isSuccess", task.getException());
                            firebaseUser = task.getResult().getUser();
                            // get data from db
                            userModel = new UserModel();
                            userModel.setEmail(firebaseUser.getEmail());
                            userModel.setId(firebaseUser.getUid());
                            userModel.setPassword(password);
                            userModel.setUserType(userType);
                            userModel.setPhone(phone);
                            userModel.setName(name);
                            userModel.setImageURL("image");
                            insertDatabase(userModel);
                            onLoginSuccess(userModel,password);
                        }
                        // ...
                    }
                });
    }
    private void showProgressDialog() {
    }
    private void onLoginSuccess(UserModel userModel,String password)
    {
        dialog.dismiss();
        saveUserData(userModel.getEmail(),password,userModel.getUserType());
        Intent intent= new Intent(SignUpActivity.this,MainActivity.class);
        intent.putExtra(CONSTANT.USER_TYPE,userModel.getUserType());
        intent.putExtra(CONSTANT.KEY_USER,userModel);
        startActivity(intent);
        finish();
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
    private void    hideProgressDialog()
    {

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
