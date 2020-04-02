package com.example.realtime_gps.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtime_gps.R;
import com.example.realtime_gps.fragment.Model.Group;
import com.example.realtime_gps.fragment.adapter.GroupListAdapter;
import com.example.realtime_gps.fragment.spclass.SPGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupFragment extends Fragment {

    // Quả code dối tung @@@@@@@@@@@
    View view;
    Button create;
    Button join;
    FirebaseUser firebaseUser;
    EditText search;
    ArrayList<Group> groupsList= new ArrayList<Group>();
    RecyclerView recyclerView;
    boolean EditTextEven = true;
    long childrenCount = -1;



    private void findID(){
        create = view.findViewById(R.id.Button_YourGroups_Create);
        join = view.findViewById(R.id.Button_YourGroups_Join);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        search = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recyclerView1);

    }

    private View.OnClickListener createOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Gọi hộp thoại tạo nhóm
            final Dialog dialog = new Dialog(getContext());
            dialog.setCancelable(false);//false là không bấm đc ra bên ngoài
            dialog.setContentView(R.layout.create_group_dialog);
            //HIện hộp thoại
            dialog.show();
            //Set 2 sự kiện người dùng bấm button
            Button ok = dialog.findViewById(R.id.Button_Create_Group_Dialog_OK);
            Button cancer = dialog.findViewById(R.id.Button_Create_Group_Dialog_Cancer);


            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText name = dialog.findViewById(R.id.EditText_Create_Group_GroupName);
                    String groupName = name.getText().toString();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("group");
                    String grID = reference.push().getKey();

                    //Nhập dữ liệu cơ bản cho nhóm mới
                    String newJC = SPGroup.createCode();

                    Group group = new Group();
                    group.setGroupID(grID);
                    group.setName(groupName);
                    group.setImageURL("default");
                    group.setAdmin_id(firebaseUser.getUid());
                    group.setJoinCode(newJC);

                    reference.child(grID).setValue(group);

                    DatabaseReference listUser = FirebaseDatabase.getInstance().getReference("usersInGroup");
                    listUser.child(grID).push().setValue(firebaseUser.getUid());


                    String userid = firebaseUser.getUid();
                    // Đường dẫn đến thư mục user/group_list
                    DatabaseReference listGr = FirebaseDatabase.getInstance().getReference("groupsInUser").child(userid);
                    listGr.push().setValue(grID);


                    //Tạo vào thêm join code

                    DatabaseReference reference1JoinCode = FirebaseDatabase.getInstance().getReference("join_code");
                    reference1JoinCode.child(newJC).setValue(grID);

                    dialog.cancel();
                }
            });

            cancer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });



        }
    };

    private View.OnClickListener joinOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Gọi hộp thoại tạo nhóm
            final Dialog dialog = new Dialog(getContext());
            dialog.setCancelable(false);//false là không bấm đc ra bên ngoài
            dialog.setContentView(R.layout.join_group_dialog);
            //HIện hộp thoại
            dialog.show();
            //Set 2 sự kiện người dùng bấm button
            Button ok = dialog.findViewById(R.id.Button_YourGroups_Join_OK);
            Button cancer = dialog.findViewById(R.id.Button_YourGroups_Join_NO);


            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText enterJoinCode = dialog.findViewById(R.id.EditText_YourGroups_Join);
                    String joinString = enterJoinCode.getText().toString();
                    DatabaseReference referenceJoin = FirebaseDatabase.getInstance().getReference().child("join_code").child(joinString);
                    //Lấy ra id của gr đc lưu trong list join group
                    referenceJoin.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String groupID;

                            if(dataSnapshot.exists()) {
                                //Trong trường hợp join không tồn tại
                                groupID = dataSnapshot.getValue().toString();

                                DatabaseReference listUser = FirebaseDatabase.getInstance().getReference("usersInGroup");
                                listUser.child(groupID).push().setValue(firebaseUser.getUid());

                                DatabaseReference listGr = FirebaseDatabase.getInstance().getReference("groupsInUser").child(firebaseUser.getUid());
                                listGr.push().setValue(groupID);


                            }
                            else {
                                Toast.makeText(getContext(),"join code sai",Toast.LENGTH_SHORT).show();
                            }
                            dialog.cancel();



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                    dialog.cancel();
                }
            });

            cancer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });


        }
    };





    public ValueEventListener getGroup = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Group group = dataSnapshot.getValue(Group.class);

                groupsList.add(group);
                GroupListAdapter groupListAdapter = new GroupListAdapter(groupsList,getContext());
                recyclerView.setAdapter(groupListAdapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    public void loadGroup(){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("groupsInUser")
                .child(firebaseUser.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    childrenCount = dataSnapshot.getChildrenCount();
                    groupsList.clear();
                    //Duyệt rất cả các ID của các Gr
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                        DatabaseReference getGr = FirebaseDatabase.getInstance().getReference("group")
                                .child(snapshot1.getValue().toString());
                        getGr.addListenerForSingleValueEvent(getGroup);

                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void search(String sea){
        ArrayList list = new ArrayList();
        for (Group group : groupsList){

            if(group.getName().toLowerCase().contains(sea)){
                list.add(group);

            }

        }
        GroupListAdapter groupListAdapter = new GroupListAdapter(list,getContext());

        recyclerView.setAdapter(groupListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.group_fragment,container,false);
        findID();
        create.setOnClickListener(createOnClickListener);
        join.setOnClickListener(joinOnClickListener);
        loadGroup();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {




            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String sea;
                sea = s.toString().toLowerCase();
                search(sea);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }







}
