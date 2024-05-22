package com.example.mentsync.AfterLogin.SearchUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mentsync.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";

    private String userId;
    private TextView nameTextView;
    private TextView disciplineTextView;
    private TextView semesterTextView;
    private TextView GPATextView;
    private ImageView prof;
    private Button followButton;
    private Button unfollowButton;

    private DatabaseReference userRef;
    private DatabaseReference followsRef;
    private ValueEventListener userValueEventListener;
    private ValueEventListener followStatusListener;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        nameTextView = findViewById(R.id.textView9);
        disciplineTextView = findViewById(R.id.textView7);
        semesterTextView = findViewById(R.id.textView12);
        GPATextView = findViewById(R.id.textView13);
        prof = findViewById(R.id.profilepic3);
        followButton = findViewById(R.id.followbtn);
        unfollowButton = findViewById(R.id.unfollowbtn);
        back=findViewById(R.id.imageButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        userId = getIntent().getStringExtra("userId");
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        followsRef = FirebaseDatabase.getInstance().getReference("Follow");

        findViewById(R.id.progressBar3).setVisibility(View.VISIBLE);
        findViewById(R.id.include).setVisibility(View.GONE);


        // Fetch user data
        userValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String discipline = dataSnapshot.child("discipline").getValue(String.class);
                    String semester = dataSnapshot.child("semester").getValue(Long.class).toString();
                    String cgpa = dataSnapshot.child("cgpa").getValue(Double.class).toString();
                    String profilePicUrl = dataSnapshot.child("profile_pic").getValue(String.class);

                    nameTextView.setText(name);
                    GPATextView.setText("GPA: " + cgpa);
                    semesterTextView.setText("Semester: " + semester);
                    disciplineTextView.setText(discipline);

                    if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
                        Glide.with(UserProfileActivity.this)
                                .load(profilePicUrl)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.baseline_clear_24)
                                .into(prof);
                    }

                    findViewById(R.id.progressBar3).setVisibility(View.GONE);
                    findViewById(R.id.include).setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(UserProfileActivity.this, "User data does not exist for userId: " + userId, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "User data does not exist for userId: " + userId);

                    findViewById(R.id.progressBar3).setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserProfileActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to fetch user data: " + databaseError.getMessage());

                findViewById(R.id.progressBar3).setVisibility(View.GONE);
            }
        };
        userRef.addValueEventListener(userValueEventListener);

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followUser();
            }
        });

        unfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unfollowUser();
            }
        });

        followStatusListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(currentUserId).child("following").child(userId).exists()) {
                    setFollowingUI();
                } else {
                    setNotFollowingUI();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to check follow status: " + databaseError.getMessage());
            }
        };
        followsRef.addValueEventListener(followStatusListener);

        // Check initial follow status and update UI
        followsRef.child(currentUserId).child("following").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    setFollowingUI();
                } else {
                    setNotFollowingUI();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to fetch initial follow status: " + databaseError.getMessage());
            }
        });
    }

    private void followUser() {
        followsRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("following").child(userId).setValue(true);
        followsRef.child(userId).child("followers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);
    }

    private void unfollowUser() {
        followsRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("following").child(userId).removeValue();
        followsRef.child(userId).child("followers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
    }

    private void setFollowingUI() {
        followButton.setVisibility(View.INVISIBLE);
        unfollowButton.setVisibility(View.VISIBLE);
    }

    private void setNotFollowingUI() {
        followButton.setVisibility(View.VISIBLE);
        unfollowButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (userValueEventListener != null) {
            userRef.addValueEventListener(userValueEventListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (userValueEventListener != null) {
            userRef.removeEventListener(userValueEventListener);
        }
        if (followStatusListener != null) {
            followsRef.removeEventListener(followStatusListener);
        }
    }
}
