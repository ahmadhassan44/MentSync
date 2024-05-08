package com.example.mentsync.AfterLogin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.mentsync.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AddFragment extends Fragment {

    TabLayout tab;
    ViewPager2 viewpager;

    View addFragment;
    public AddFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addFragment=inflater.inflate(R.layout.fragment_add, container, false);
        return addFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tab=addFragment.findViewById(R.id.tab);
        viewpager=addFragment.findViewById(R.id.viewpager);

        ViewPagerMessengerAdapter messengerAdapter=new ViewPagerMessengerAdapter(getActivity().getSupportFragmentManager(), getLifecycle());
        viewpager.setAdapter(messengerAdapter);

        new TabLayoutMediator(tab, viewpager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Post");
                    break;
                case 1:
                    tab.setText("Query");
                    break;  
                default:
                    tab.setText("Tab " + position);
                    break;
            }
        }).attach();
    }
}