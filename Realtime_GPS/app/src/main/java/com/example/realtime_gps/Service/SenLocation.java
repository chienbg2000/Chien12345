package com.example.realtime_gps.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.realtime_gps.MainActivity;
import com.example.realtime_gps.fragment.spclass.SPMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;

public class SenLocation extends Service {
    Context context;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void upLocation(double Latitude , double Longitude){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference.child(firebaseUser.getUid()).child("Latitude").setValue(Latitude);
        reference.child(firebaseUser.getUid()).child("Longitude").setValue(Longitude);

    }






    OnLocationUpdatedListener locationListener = new OnLocationUpdatedListener() {
        @Override
        public void onLocationUpdated(Location location) {

            double lat = location.getLatitude();
            double lng = location.getLongitude();
            upLocation(lat,lng);
        }
    };
    LocationParams.Builder builder = new LocationParams.Builder().setAccuracy(LocationAccuracy.HIGH)
            .setDistance(0)
            .setInterval(1000);
    public void startingLocationTracking(){


        SmartLocation.with(this).location(new LocationGooglePlayServicesProvider()).continuous().config(builder.build()).start(locationListener);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startingLocationTracking();

        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        SmartLocation.with(this).location(new LocationGooglePlayServicesProvider()).continuous().config(builder.build()).stop();

        super.onDestroy();
    }
}
