package com.sanbong.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sanbong.CONSTANT;
import com.sanbong.MainActivity;
import com.sanbong.R;
import com.sanbong.custom_view.RoundedImageView;
import com.sanbong.model.UserModel;
import com.sanbong.utils.ShowToask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    public   static String TAG = "SignUpActivity";
    private TextView bt_signUp,bt_login,tv_temp;
    private ArrayList<String> list_userType,list_userAdd;
    private EditText edt_userName,edt_password,edt_rePassword,edt_userEmail;
    private EditText edt_phone;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private RadioGroup groupUsertype;
    private String userType=UserModel.TYPE_TEAM;
    private DatabaseReference reference;
    private UserModel userModel;
    private Dialog dialog;
    private SharedPreferences sharedPreferences;
    private RoundedImageView img_avatar;
    private static int PICK_IMAGE=1111;
    private Uri avatarUri;
    private UploadTask uploadTask;
    private StorageReference storageReference;
    private String downloadURL="";
    private boolean pickedImage=false;
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
            }
        };
    }
    public void initView()
    {
        groupUsertype  = (RadioGroup) findViewById(R.id.radio_userType);
        edt_userName   = (EditText) findViewById(R.id.input_name);
        edt_password   = (EditText) findViewById(R.id.input_password);
        edt_rePassword = (EditText) findViewById(R.id.input_re_password);
        edt_userEmail  = (EditText) findViewById(R.id.input_email);
        edt_phone      = (EditText) findViewById(R.id.input_phone);
        bt_signUp      = (TextView) findViewById(R.id.btn_signUp);
        // textview dung de luu lai imageURL
        tv_temp        = (TextView) findViewById(R.id.txt_temp);
        bt_login       = (TextView) findViewById(R.id.link_login);
        img_avatar     = (RoundedImageView) findViewById(R.id.img_avatar);
        img_avatar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.btn_signUp :
            {
                if(pickedImage==true)
                postImage(avatarUri);
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage(getString(R.string.upload_later));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tv_temp.setText("imageURL");
                            signup();
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                break;
            }
            case R.id.btn_login :
            {
                onBackPressed();
                break;
            }
            case R.id.img_avatar :
            {
                chooseImage();
                break;
            }
        }
    }
    public void postImage(Uri uri)
    {
        final Dialog dialog =  new MaterialDialog.Builder(this)
                .content("Đang tạo tài khoản...")
                .progress(true, 0)
                .show();

        FirebaseStorage storageRef = FirebaseStorage.getInstance();

        storageReference = storageRef.getReferenceFromUrl("gs://sanbong-1352.appspot.com");

        UploadTask uploadTask =  storageReference.child("images").putFile(uri);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG,"exception "+exception.getLocalizedMessage().toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Log.d(TAG,"downloadURL "+taskSnapshot.getDownloadUrl().toString());
                downloadURL = taskSnapshot.getDownloadUrl().toString();
                tv_temp.setText(downloadURL);
                signup();
                dialog.dismiss();
            }
        });
    }

    public void chooseImage()
    {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"intent data: "+data.toString());
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data == null) {
                Log.d(TAG, "data null");
            } else {
//             Picasso.with(SignUpActivity.this).load(data.getData()).fit().into(img_avatar);
                try {
                        pickedImage=true;
                        avatarUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), avatarUri);
                        // Log.d(TAG, String.valueOf(bitmap));
                        img_avatar.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
        }

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
    public void insertDatabase(final String id, final String password,final  String name,
                               final String phone, final String userType,final String imgURL)
    {
            firebaseAuth = FirebaseAuth.getInstance();
            reference = firebaseDatabase.getReference("users");
            reference= reference.child(id).getRef();
            reference.child("name").setValue(name);
            reference.child("type").setValue(userType);
            reference.child("phone").setValue(phone);
            reference.child("imageURL").setValue(imgURL);
            reference.child("password").setValue(password);

    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        bt_signUp.setEnabled(false);

        String name = edt_userName.getText().toString();
        String email = edt_userEmail.getText().toString();
        String password = edt_password.getText().toString();
        String phone = edt_phone.getText().toString();
        createAccount(email,password,name,phone,userType,tv_temp.getText().toString());

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
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
    private void createAccount(final String email, final String password,final  String name,
                               final String phone, final String userType,final String imgURL)
        {
        showProgressDialog();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                hideProgressDialog();
                //nếu tạo user thành công
                if(task.isSuccessful())
                {
                    insertDatabase(task.getResult().getUser().getUid(),password,name,phone,userType,imgURL);
                    loginFirebase(email,password,name,phone,userType,imgURL);

                }
                else
                {
                    ShowToask.showToaskLong(SignUpActivity.this,"failed");
                }
            }
        });
    }
    public void loginFirebase(String email, final String password, final String name, final String phone, final String userType,final String imageURL)
    {
        dialog = new Dialog(SignUpActivity.this);
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
                            userModel.setEmail(firebaseUser.getEmail().replaceAll("\\s+", " "));
                            userModel.setId(firebaseUser.getUid().replaceAll("\\s+", " "));
                            userModel.setPassword(password.replaceAll("\\s+", " "));
                            userModel.setUserType(userType.replaceAll("\\s+", " "));
                            userModel.setPhone(phone.replaceAll("\\s+", " "));
                            userModel.setName(name.replaceAll("\\s+", " "));
                            userModel.setImageURL(imageURL);
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

}
