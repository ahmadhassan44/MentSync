package com.example.mentsync.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mentsync.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChatsActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private ChatViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        viewPager2 = findViewById(R.id.vp);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        adapter = new ChatViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(adapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.chat)
                    viewPager2.setCurrentItem(0);
                else
                    viewPager2.setCurrentItem(1);
                return true;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.chat);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.forum);
                        break;
                }
            }
        });
    }
}
