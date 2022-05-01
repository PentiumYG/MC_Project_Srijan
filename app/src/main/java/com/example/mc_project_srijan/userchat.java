package com.example.mc_project_srijan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mc_project_srijan.Adapters.chatAdapter;
import com.example.mc_project_srijan.databinding.ActivityUserchatBinding;
import com.example.mc_project_srijan.model.user_messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class userchat extends AppCompatActivity {
    ActivityUserchatBinding binding;
    FirebaseDatabase db;
    FirebaseAuth au;
    String senderId;
    String receiverId;
    String receiverName;
    String receiverProfile_pic;
    String MSG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserchatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db=FirebaseDatabase.getInstance();
        au=FirebaseAuth.getInstance();
        senderId=au.getUid();
        receiverId=getIntent().getStringExtra("userID");
        receiverName=getIntent().getStringExtra("userName");
        receiverProfile_pic=getIntent().getStringExtra("profilePic");
        Picasso.get().load(receiverProfile_pic).placeholder(R.drawable.ic_icons8_circle_24).into(binding.profilePic);
        binding.receiverName.setText(receiverName);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(userchat.this,MainActivity.class);
                startActivity(intent);
            }
        });
        ArrayList<user_messages> u_m=new ArrayList<user_messages>();
        chatAdapter chat=new chatAdapter(u_m,this,receiverId);
        binding.crv.setAdapter(chat);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.crv.setLayoutManager(layoutManager);
        db.getReference().child("user_chats").child(senderId+receiverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                u_m.clear();
                for(DataSnapshot s1:snapshot.getChildren()){
                    user_messages u=s1.getValue(user_messages.class);
                    u.setMsgID(s1.getKey());
                    u_m.add(u);
                }
                chat.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              MSG=binding.msg.getText().toString();
              user_messages u_msg=new user_messages(senderId,MSG);
                db.getReference().child("user_chats").child(senderId+receiverId).push().setValue(u_msg).addOnSuccessListener(new OnSuccessListener<Void>() {

                  @Override
                  public void onSuccess(Void unused) {
                      db.getReference().child("user_chats").child(receiverId+senderId).push().setValue(u_msg).addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void unused) {

                          }
                      });
                  }
              });
                binding.msg.setText("");

            }
        });
        getSupportActionBar().hide();
    }
}