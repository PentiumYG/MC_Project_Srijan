package com.example.mc_project_srijan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.mc_project_srijan.Adapters.FragmentAdapter;
import com.example.mc_project_srijan.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();
        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.settings)
            Toast.makeText(this, "Clicked on Settings", Toast.LENGTH_SHORT).show();
        else if(item.getItemId()==R.id.groupChat)
            Toast.makeText(this, "Clicked on Group Chat", Toast.LENGTH_SHORT).show();
        else if(item.getItemId()==R.id.fetch){
             Intent intent=new Intent(MainActivity.this,MapsActivity.class);
             startActivity(intent);

        }
        else if(item.getItemId()==R.id.logout) {
            // Toast.makeText(this, "Clicked on Logout", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Intent intent=new Intent(MainActivity.this,signup.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}