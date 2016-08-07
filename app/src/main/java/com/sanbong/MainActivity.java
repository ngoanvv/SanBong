package com.sanbong;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanbong.dialog.CloseDialog;
import com.sanbong.dialog.LogOutDialog;
import com.sanbong.dialog.SearchDialog;
import com.sanbong.fragment.FindMatchFragment;
import com.sanbong.fragment.FindPitchFragment;
import com.sanbong.fragment.HotPitchFragment;
import com.sanbong.fragment.MyMapFragment;
import com.sanbong.model.UserModel;
import com.sanbong.ui.LoginActivity;
import com.sanbong.ui.NavigationDrawerCallbacks;
import com.sanbong.ui.NavigationDrawerFragment;
import com.sanbong.utils.ShowToask;


public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks, View.OnClickListener,
        SearchDialog.SearchEventInterface,LogOutDialog.LogoutInterface, CloseDialog.CloseInterface
        {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private static String TAG = "MainActivity";
    Dialog dialog;
    Fragment fragment;
    android.app.Fragment mapFragment;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    String userType;
    private GoogleMap googleMap;
    private UserModel userModel;
    FrameLayout container,container_map;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demo();
//        getUserModel();
        initDrawer();
        initView();
    }

    public void getUserModel()
    {
        userModel = (UserModel) getIntent().getSerializableExtra(CONSTANT.KEY_USER);
        if(userModel !=null)
        {
            userType = userModel.getUserType();
        }
    }
    public void initDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        // Set up the drawer.
        getSupportActionBar().setTitle("Tìm sân bóng");
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("sadasd","adasd", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));

    }

    public void initView() {
        container = (FrameLayout) findViewById(R.id.container);
        container_map = (FrameLayout) findViewById(R.id.container_map);
    }
    public void demo()
    {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("demo2");
        reference.child("hic").child("heelo").setValue("hello");

    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {

        switch (position) {
            case 0: //case tim kiem
            {
                if(mapFragment!= null ) {
                    container.setVisibility(View.GONE);
                    container_map.setVisibility(View.VISIBLE);
                    break;
                }
                else {

                    mapFragment = new MyMapFragment();
                    getFragmentManager().beginTransaction().replace(R.id.container_map, mapFragment, MyMapFragment.TAG).commit();
                    break;
                }
            }
            case 1: // dat san
            {//stats
                container_map.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);

                getSupportActionBar().setTitle("Đặt sân");
                getSupportFragmentManager().popBackStack();
                fragment = new FindPitchFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, FindPitchFragment.TAG).commit();
                break;
            }
            case 2: //tim doi thu //todo
            {
                container_map.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);

                getSupportActionBar().setTitle("Kèo bóng");
                getSupportFragmentManager().popBackStack();
                fragment = new FindMatchFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, FindMatchFragment.TAG).commit();
                break;
            }
            case 3: {//San dep gio vang //todo

                container_map.setVisibility(View.GONE);
                container.setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle("Sân đẹp giờ vàng");
                getSupportFragmentManager().popBackStack();
                fragment = new HotPitchFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, FindMatchFragment.TAG).commit();
                break;

            }
            case 4: // dang tin tim doi
            {

                break;
            }
            case 5: // cai dat cua team, them san cua owner
            {

                break;
            }
            case 6: // dang xuat cua team, quan ly san bong cua owner
            {
                if(userType.equals(UserModel.TYPE_TEAM)) {
                    showDialogLogout();
                    break;
                }
                if(userType.equals(UserModel.TYPE_OWNER))
                {
                    Log.d(MainActivity.TAG,"usertype : owner");
                    break;
                }
            }
            case 7: // cai dat cua owner
            {
                break;
            }
            case 8: //  dang xuat cua owner
            {
                if(userType.equals(UserModel.TYPE_OWNER)) {
                    showDialogLogout();
                    break;
                }
            }

        }

    }

    private void showDialogLogout() {
        LogOutDialog dialog = new LogOutDialog();
        dialog.setLogoutInterface(this);
        dialog.show(getSupportFragmentManager(),"logout");
    }

    public void logOut()
    {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        SharedPreferences sharedPreferences= getSharedPreferences("data",MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        Log.d("share",sharedPreferences.getString("email","null"));
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else {
        }
    }

    private void closeApp() {
        CloseDialog dialog = new CloseDialog();
        dialog.setmCloseInterface(this);
        dialog.show(getSupportFragmentManager(),"close");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_search :
            {
                if(mapFragment != null)
                {
                    SearchDialog dialog = new SearchDialog();
                    dialog.setmSearchEventInterface(this);
                    dialog.show(getSupportFragmentManager(),"");
                }
                break;
            }
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
        }
    }

    @Override
    public void search() {
            if(mapFragment != null)
            {
                ((MyMapFragment) mapFragment).addMarker();
            }
    }
    public void acceptMatch()
    {
        ShowToask.showToaskLong(MainActivity.this,"Accept Match");
    };
    @Override
    public void onLogOutEvent() {
        logOut();
    }

    @Override
    public void onClose() {
        finish();
    }


}
