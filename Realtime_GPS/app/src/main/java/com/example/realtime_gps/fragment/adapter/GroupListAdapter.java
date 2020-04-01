package com.example.realtime_gps.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtime_gps.R;
import com.example.realtime_gps.fragment.Model.Group;

import java.util.ArrayList;
import java.util.List;
//https://xuanthulab.net/su-dung-recyclerview-trong-lap-trinh-android.html
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {

    private ArrayList<Group> groupList;
    private Context contextList;
    private String imgURl;
    public GroupListAdapter(ArrayList<Group> groupList, Context contextList) {
        this.groupList = groupList;
        this.contextList = contextList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View grView =
                inflater.inflate(R.layout.groupitem, parent, false);

        ViewHolder viewHolder = new ViewHolder(grView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group group = groupList.get(position);
        holder.nameGroup.setText(group.getName());
//        holder.memberCount.setText(String.format("%d",group.getMemberCount()));
        if (group.getImageURL().equals("default")){
            holder.groupImg.setImageResource(R.drawable.your_group);
        }
        else {
            holder.groupImg.setImageResource(R.drawable.your_group);
        }
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameGroup;
        public ImageView groupImg;
        public TextView memberCount;

        public ViewHolder(View itemView) {
            super(itemView);

            nameGroup = itemView.findViewById(R.id.username);
            groupImg = itemView.findViewById(R.id.profile_image);
            memberCount = itemView.findViewById(R.id.memberCount);



        }
    }


}