package com.example.mentsync.AfterLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mentsync.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.framelayout,new HomeFragment());
        ft.commit();
        ((BottomNavigationView)findViewById(R.id.bottomnav)).setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==findViewById(R.id.nav_home).getId())
                    loadFrag(new HomeFragment());
                else if(item.getItemId()==findViewById(R.id.nav_search).getId())
                    loadFrag(new SearchFragment());
                else if(item.getItemId()==findViewById(R.id.nav_post).getId())
                    loadFrag(new AddFragment());
                else if(item.getItemId()==findViewById(R.id.nav_notifications).getId())
                    loadFrag(new NotificationsFragment());
                else if(item.getItemId()==findViewById(R.id.nav_profile).getId())
                    loadFrag(new ProfileFragment());
                return true;
            }
        });
    }
    void loadFrag(Fragment frag)
    {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.framelayout,frag);
        ft.commit();
    }
}