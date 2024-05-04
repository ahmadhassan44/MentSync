    package com.example.mentsync.AfterLogin;

    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.net.Uri;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;


    import com.bumptech.glide.Glide;
    import com.example.mentsync.IPAddress;
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
            String url="https://" + IPAddress.ipaddress + "/UserProfilePics/" + currentUser.getProfilePic();
            TextView tf=profileFragment.findViewById(R.id.textView8);
            tf.setText(currentUser.getName());
            Glide.with(this) // "this" refers to the Fragment context
                    .load(url) // Load from URL
                    .placeholder(R.drawable.placeholder) // Optional: placeholder image
                    .error(R.drawable.baseline_clear_24) // Optional: error image
                    .into(profilepic); // Set in the ImageView

        }
    }