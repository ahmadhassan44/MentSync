package com.example.mentsync.AfterLogin.Feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentsync.Chat.ChatsActivity;
import com.example.mentsync.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class HomeFragment extends Fragment {
    View homefrag;
    ViewPager2 viewPager2;
    TabLayout tab;
    private FeedAdapter feedAdapter;
    private List<Object> combinedList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homefrag = inflater.inflate(R.layout.fragment_home, container, false);
        return homefrag;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar topAppBar = view.findViewById(R.id.hometoolbar);
        tab = homefrag.findViewById(R.id.tab);
        viewPager2 = homefrag.findViewById(R.id.viewpager);

        setupViewPager();

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_chats) {
                Intent intent = new Intent(getContext(), ChatsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    private void setupViewPager() {
        FeedViewPagerAdapter feedViewPagerAdapter = new FeedViewPagerAdapter(getChildFragmentManager(), getLifecycle());
        viewPager2.setAdapter(feedViewPagerAdapter);
        new TabLayoutMediator(tab, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Posts");
                    break;
                case 1:
                    tab.setText("Queries");
                    break;
                default:
                    tab.setText("Tab " + position);
                    break;
            }
        }).attach();
    }
}