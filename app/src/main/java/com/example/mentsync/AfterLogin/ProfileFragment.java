    package com.example.mentsync.AfterLogin;

    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import com.bumptech.glide.Glide;
    import com.example.mentsync.Login.LoginSignupActivity;
    import com.example.mentsync.R;

    public class ProfileFragment extends Fragment {
        View profileFragment;

        public ProfileFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            profileFragment=inflater.inflate(R.layout.fragment_profile, container, false);
            return profileFragment;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            LoggedInUser currentUser=LoggedInUser.getInstance();
            ImageView profilepic=profileFragment.findViewById(R.id.profilepic3);

            //set user data into components
            SharedPreferences pref= getContext().getSharedPreferences("user_data",Context.MODE_PRIVATE);
            TextView tf=profileFragment.findViewById(R.id.textView9);
            tf.setText(pref.getString("name","User"));
            profileFragment.findViewById(R.id.logoutbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new UserSessionManager(getContext()).endSession();
                    startActivity(new Intent(getContext(), LoginSignupActivity.class));
                }
            });
            profileFragment.findViewById(R.id.textView10).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(),DeleteAccountActivity.class));
                }
            });
        }
    }