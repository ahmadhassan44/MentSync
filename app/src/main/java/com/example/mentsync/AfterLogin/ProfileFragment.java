    package com.example.mentsync.AfterLogin;

    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.net.Uri;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import android.util.Log;
    import android.view.Display;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.bumptech.glide.Glide;
    import com.example.mentsync.IPAddress;
    import com.example.mentsync.Login.LoginSignupActivity;
    import com.example.mentsync.R;


    import java.io.FileNotFoundException;
    import java.io.InputStream;
    import java.net.URI;
    import java.net.URISyntaxException;

    import de.hdodenhof.circleimageview.CircleImageView;

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
            TextView tf=profileFragment.findViewById(R.id.textView8);
            tf.setText(pref.getString("name","User"));
            Glide.with(this) // "this" refers to the Fragment context
                    .load("https://" + IPAddress.ipaddress + "/UserProfilePics/" + pref.getString("profile_pic","IMG154.jpg")) // Load from URL
                    .placeholder(R.drawable.placeholder) // Optional: placeholder image
                    .error(R.drawable.baseline_clear_24) // Optional: error image
                    .into(profilepic); // Set in the ImageView
            profileFragment.findViewById(R.id.logoutbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new UserSessionManager(getContext()).endSession();
                    startActivity(new Intent(getContext(), LoginSignupActivity.class));
                }
            });
        }
    }