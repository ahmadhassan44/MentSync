package com.example.mentsync.AfterLogin.Feed;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class FeedViewPagerAdapter extends FragmentStateAdapter {
    public FeedViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new PostsFeedFragment();
        else
            return new QueryFeedFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
