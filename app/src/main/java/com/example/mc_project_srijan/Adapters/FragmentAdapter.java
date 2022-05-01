package com.example.mc_project_srijan.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mc_project_srijan.Fragments.calls;
import com.example.mc_project_srijan.Fragments.chats;
import com.example.mc_project_srijan.Fragments.status;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new chats();
        else if(position==1)
            return new status();
        else if(position==2)
            return new calls();
        else
            return new chats();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        if(position==0)
            title="Chats";
        else if(position==1)
            title="Status";
        else if(position==2)
            title="Calls";

        return title;
    }
}
