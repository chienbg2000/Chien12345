package com.example.realtime_gps.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.realtime_gps.LogInActivity;
import com.example.realtime_gps.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment {
    TextView logOut;
    View view;

    private void finID(){
        logOut =  view.findViewById(R.id.menu_setting_log_out);
    }
    //Sự kiện người dùng bấm vào log out
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Đăng xuất
            FirebaseAuth.getInstance().signOut();
            // change this code beacuse your app will crash
            Intent intent = new Intent(getContext(),LogInActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getActivity().finish();


        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.setting_fragment, container, false);
        finID();
        logOut.setOnClickListener(onClickListener);

        return view;

    }


}
