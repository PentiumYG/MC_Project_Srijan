package com.example.mc_project_srijan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mc_project_srijan.R;
import com.example.mc_project_srijan.model.user_messages;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter{
    ArrayList<user_messages> u_m;
    Context context;
    int sender_vt=0,receiver_vt=1;
    String r_id;

    public chatAdapter(ArrayList<user_messages> u_m, Context context) {
        this.u_m = u_m;
        this.context = context;
    }

    public chatAdapter(ArrayList<user_messages> u_m, Context context, String r_id) {
        this.u_m = u_m;
        this.context = context;
        this.r_id = r_id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType==receiver_vt){
           View view= LayoutInflater.from(context).inflate(R.layout.receiver_screen,parent,false);
           return new receiver_vh(view);
       }
       else{
           View view= LayoutInflater.from(context).inflate(R.layout.sender_screen,parent,false);
           return new sender(view);
       }


    }

    @Override
    public int getItemViewType(int position) {
       // return super.getItemViewType(position);
        if(u_m.get(position).getUserId().equals(FirebaseAuth.getInstance().getUid())){
            return sender_vt;
        }
        else
            return receiver_vt;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      user_messages u=u_m.get(position);
      if(holder.getClass()==sender.class)
          ((sender)holder).s_msg.setText(u.getMsg());
      else
          ((receiver_vh)holder).r_msg.setText((u.getMsg()));
    }

    @Override
    public int getItemCount() {
        return u_m.size();
    }

    public class receiver_vh extends RecyclerView.ViewHolder{
        TextView r_msg;
        public receiver_vh(@NonNull View itemView) {
            super(itemView);
            r_msg=itemView.findViewById(R.id.receiverTxT);

        }
    }
    public class sender extends RecyclerView.ViewHolder{
        TextView s_msg;
        public sender(@NonNull View itemView) {
            super(itemView);
            s_msg=itemView.findViewById(R.id.senderTxt);
        }
    }
}
