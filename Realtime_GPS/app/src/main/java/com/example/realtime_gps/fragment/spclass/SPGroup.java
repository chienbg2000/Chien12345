package com.example.realtime_gps.fragment.spclass;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.realtime_gps.DatabaseSQLite;
import com.example.realtime_gps.fragment.Model.Group;
import com.example.realtime_gps.fragment.Model.User;
import com.example.realtime_gps.fragment.adapter.GroupListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class SPGroup {
    ArrayList<User> arrayListMember = new ArrayList<User>();

    private static boolean boolExitsJoin = true;
    // phần này phải xem lại vì chưa đc tối ưu đâu
    public static boolean exitsJoin(String jCode){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("join_code").child(jCode);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    boolExitsJoin = true;
                else
                    boolExitsJoin =false;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return boolExitsJoin;
    }
    //Tạo joinCode
    public static String createCode(){
        Random random = new Random();
        String code;
//        do {
//             code =String.valueOf(0 + random.nextInt(99999999));
//        }while (!exitsJoin(code));
        code =String.valueOf(10000000 + random.nextInt(89999999));
        return code;

    }






    public static void INSERT_GroupList(Context context){


        DatabaseSQLite databaseSQLite = new DatabaseSQLite(context,"groupList.sqlite",null,1);

        databaseSQLite.QueryData("CREATE TABLE IF NOT EXIST MyGroup(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "groupID VARCHAR(200), name VARCHAR(200),imageURL VARCHAR(200) " +
                ",admin_id VARCHAR(200),join_code VARCHAR(200))");

        Cursor cursor = databaseSQLite.getData("SELECT * FROM MyGroup");



        while (cursor.moveToNext()){
            String groupID = cursor.getString(1);
            String name = cursor.getString(2);
            String imageURL = cursor.getString(3);
            String admin_id = cursor.getString(4);
            String join_code = cursor.getString(5);


        }




    }


}
