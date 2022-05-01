package com.example.mc_project_srijan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mc_project_srijan.R;
import com.example.mc_project_srijan.model.user;
import com.example.mc_project_srijan.userchat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    ArrayList<user> userlist;
    Context context;

    public UserAdapter(ArrayList<user> userlist, Context context) {
        this.userlist = userlist;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        user ur=userlist.get(position);
        Picasso.get().load(ur.getProfile_pic()).placeholder(R.drawable.ic_icons8_circle_24).into(holder.img);
        holder.uname.setText(ur.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, userchat.class);
                intent.putExtra("userId",ur.getUserId());
                intent.putExtra("profilePic",ur.getProfile_pic());
                intent.putExtra("userName",ur.getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView uname,lm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.circleImageView);
            uname=itemView.findViewById(R.id.name);
            lm=itemView.findViewById(R.id.lastchat);

        }
    }
}
