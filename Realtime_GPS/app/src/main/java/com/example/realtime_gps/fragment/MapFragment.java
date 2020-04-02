package com.example.realtime_gps.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.realtime_gps.R;
import com.example.realtime_gps.fragment.Model.User;
import com.example.realtime_gps.fragment.spclass.SPGroup;
import com.example.realtime_gps.fragment.spclass.SPMap;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static View view;
    private Spinner spinner;
    private GoogleMap mMap;
    ImageView image_profile;
    StorageReference storageReference;
    FirebaseUser fuser;
    DatabaseReference reference;

    //Chữa lỗi dò rỉ bộ nhớ
    //Nguồn https://helpex.vn/question/id-trung-lap-the-null-hoac-id-cha-voi-mot-doan-khac-cho-com-google-android-gms-maps-mapfragment-5cb71a31ae03f62598dde726

    private void spinnerTT(){

        image_profile = view.findViewById(R.id.profile_image);


        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.getImageURL().equals("default")){
                    image_profile.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getContext()).load(user.getImageURL()).into(image_profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
        SPMap.context = getContext();
        SPMap.getListMember("-M3Lh8I_DzllN9NU0xnt");

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


    }
}
