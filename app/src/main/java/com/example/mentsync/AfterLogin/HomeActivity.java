    package com.example.mentsync.AfterLogin;

    import android.os.Bundle;
    import android.view.MenuItem;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;

    import com.example.mentsync.AfterLogin.Feed.HomeFragment;
    import com.example.mentsync.AfterLogin.SearchUsers.SearchFragment;
    import com.example.mentsync.R;
    import com.google.android.material.bottomnavigation.BottomNavigationView;

    public class HomeActivity extends AppCompatActivity {

        private FragmentManager fragmentManager;
        private Fragment homeFragment;
        private BottomNavigationView bottomNav;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            fragmentManager = getSupportFragmentManager();
            homeFragment = new HomeFragment();
            switchFragment(homeFragment, false);
            bottomNav = findViewById(R.id.bottomnav);


            bottomNav.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if(item.getItemId()==R.id.nav_home)
                        selectedFragment=homeFragment;
                    else if (item.getItemId()==R.id.nav_search)
                        selectedFragment= new SearchFragment();
                    else if(item.getItemId()==R.id.nav_post)
                        selectedFragment=new AddFragment();
                    else if(item.getItemId()==R.id.nav_profile)
                        selectedFragment=new ProfileFragment();
                    switchFragment(selectedFragment,true);
                    return true;
                }
            });
        }
        private void switchFragment(Fragment fragment, boolean addToBackStack) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.framelayout, fragment);
            if (addToBackStack)
                transaction.addToBackStack(null);
            transaction.commit();
        }
    }
