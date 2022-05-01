package com.example.mc_project_srijan.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mc_project_srijan.Adapters.UserAdapter;
import com.example.mc_project_srijan.R;
import com.example.mc_project_srijan.databinding.FragmentChatsBinding;
import com.example.mc_project_srijan.model.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class chats extends Fragment {



    public chats() {
        // Required empty public constructor
    }

    FragmentChatsBinding binding;
    ArrayList<user> userlist=new ArrayList<>();
    FirebaseDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentChatsBinding.inflate(inflater,container,false);
        db=FirebaseDatabase.getInstance();
        UserAdapter ua=new UserAdapter(userlist,getContext());
        binding.chatrecyclerview.setAdapter(ua);
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        binding.chatrecyclerview.setLayoutManager(lm);
        db.getReference().child("OurUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userlist.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    user u=ds.getValue(user.class);
                    u.setUserId(ds.getKey());
                    userlist.add(u);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }
}