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

import com.sanbong.dialog.CloseDialog;
import com.sanbong.dialog.LogOutDialog;
import com.sanbong.dialog.SearchDialog;
import com.sanbong.fragment.FindMatchFragment;
import com.sanbong.fragment.MyMapFragment;
import com.sanbong.fragment.OrderFragment;
import com.sanbong.ui.LoginActivity;
import com.sanbong.ui.NavigationDrawerCallbacks;
import com.sanbong.ui.NavigationDrawerFragment;


public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks, View.OnClickListener,
        SearchDialog.SearchEventInterface,LogOutDialog.LogoutInterface, CloseDialog.CloseInterface {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    Dialog dialog;
    Fragment fragment;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDrawer();
        initView();
    }


    public void initDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        // Set up the drawer.
        getSupportActionBar().setTitle("Tìm sân bóng");
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("Diep NV, UET", "Email : diep170995@gmail.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));

    }

    public void initView() {

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0: //case tim kiem
            {
                    fragment = new MyMapFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, MyMapFragment.TAG).commit();
                break;

            }
            case 1: // dat san
            {//stats
                getSupportActionBar().setTitle("Đặt sân");
                getSupportFragmentManager().popBackStack();
                fragment = new OrderFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, OrderFragment.TAG).commit();
                break;
            }
            case 2: //tim doi thu //todo
            {
                getSupportActionBar().setTitle("Kèo bóng");
                getSupportFragmentManager().popBackStack();
                fragment = new FindMatchFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, FindMatchFragment.TAG).commit();
                break;
            }
            case 3: {//San dep gio vang //todo
                break;
            }
            case 4 :  // them san
            {
                break;
            }
            case 5 : // quan ly san bong
            {

                break;
            }
            case 6 : // cai dat
            {

                break;
            }
            case 7 : // dang xuat
            {
                showDialogLogout();

                break;
            }

        }
    }

    private void showDialogLogout() {
        LogOutDialog dialog = new LogOutDialog();
        dialog.setLogoutInterface(this);
        dialog.show(getFragmentManager(),"logout");
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
            closeApp();
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
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
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
                if(fragment instanceof MyMapFragment)
                {
                    SearchDialog dialog = new SearchDialog();
                    dialog.setmSearchEventInterface(this);
                    dialog.show(getFragmentManager(),"");
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
        if(fragment!=null)
            if(fragment instanceof MyMapFragment)
            {
                ((MyMapFragment) fragment).addMarker();
            }

    }

    @Override
    public void onLogOutEvent() {
        logOut();
    }

    @Override
    public void onClose() {
        finish();
    }
}
