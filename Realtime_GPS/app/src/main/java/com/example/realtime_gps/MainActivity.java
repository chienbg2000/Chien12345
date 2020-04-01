package com.example.realtime_gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.realtime_gps.fragment.GroupFragment;
import com.example.realtime_gps.fragment.LocationTag;
import com.example.realtime_gps.fragment.MapFragment;
import com.example.realtime_gps.fragment.ProfileFragment;
import com.example.realtime_gps.fragment.SettingFragment;
import com.example.realtime_gps.fragment.spclass.SPMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNV;
    MapFragment mapFragment;


    private void finID(){
        bottomNV = findViewById(R.id.navigation);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.menu_People:
                   loadFragment(mapFragment);
                   return true;

                case R.id.menu_Location_tag:
                    fragment = new LocationTag();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_group:
                    fragment = new GroupFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_profile:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.menu_setting:
                    fragment = new SettingFragment();
                    loadFragment(fragment);
                    return true;
            }




            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Thanh công cụ trong suốt
        //Nguồn https://stackoverflow.com/questions/29311078/android-completely-transparent-status-bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        mapFragment = new MapFragment();
        loadFragment(mapFragment);
        finID();
        bottomNV.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        SPMap.requires_GPS(this);



    }

}