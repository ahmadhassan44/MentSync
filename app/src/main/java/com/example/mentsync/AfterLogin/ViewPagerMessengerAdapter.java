package com.example.mentsync.AfterLogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mentsync.AfterLogin.AddFragmentChilds.QueryPostFragment;
import com.example.mentsync.AfterLogin.AddFragmentChilds.SimplePostFragment;

public class ViewPagerMessengerAdapter extends FragmentStateAdapter {


    public ViewPagerMessengerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new SimplePostFragment();
        else
            return new QueryPostFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
