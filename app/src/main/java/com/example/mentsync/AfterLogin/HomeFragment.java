package com.example.mentsync.AfterLogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentsync.R;
import com.google.android.material.appbar.MaterialToolbar;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the MaterialToolbar within the fragment's layout
        MaterialToolbar topAppBar = view.findViewById(R.id.hometoolbar);

        // Set up the menu item click listener
        topAppBar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_chats) {
                    // Use getActivity() or requireActivity() to get the context to start the activity
                    Intent intent = new Intent(getContext(), ChatsActivity.class);
                    startActivity(intent);
                    return true; // Return true if the menu item was handled
                }
                return false; // Return false if the menu item was not handled
            }
        });
    }

}