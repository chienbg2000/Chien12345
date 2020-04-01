package com.example.realtime_gps.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.realtime_gps.R;
import com.example.realtime_gps.fragment.spclass.SPMap;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static View view;
    private Spinner spinner;
    private GoogleMap mMap;

    //Chữa lỗi dò rỉ bộ nhớ
    //Nguồn https://helpex.vn/question/id-trung-lap-the-null-hoac-id-cha-voi-mot-doan-khac-cho-com-google-android-gms-maps-mapfragment-5cb71a31ae03f62598dde726

    private void spinnerTT(){

        spinner = view.findViewById(R.id.spinner);
        ArrayList<String> spinerTTString = new ArrayList<String>();
        spinerTTString.add("An Toàn");
        spinerTTString.add("Nguy Hiểm");
        spinerTTString.add("Đi Lạc");
        spinerTTString.add("Hết Pin");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,spinerTTString);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SPMap.requires_GPS(getActivity());

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.map_fragment, container, false);
        } catch (InflateException e) {
            /* map is already there, just return view as it is */
        }

        spinnerTT();

        SupportMapFragment mapFragment = (SupportMapFragment)
                this.getChildFragmentManager()
                        .findFragmentById(R.id.mapppppppppp);
        mapFragment.getMapAsync(this);

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(SPMap.check_requires_my_GPS(getActivity())){
            mMap.setMyLocationEnabled(true);
        }

        LatLng latLng = new LatLng(21.005959, 105.841940);
//        mMap.addMarker(new MarkerOptions().position(latLng).title("okboy"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
        SPMap.showListLocation(mMap);
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);





    }
}
