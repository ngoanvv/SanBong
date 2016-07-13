package com.sanbong.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sanbong.R;

public class PitchDetailActivity extends AppCompatActivity {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch_detail);
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

}
