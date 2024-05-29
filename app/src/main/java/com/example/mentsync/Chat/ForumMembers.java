package com.example.mentsync.Chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentsync.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ForumMembers extends AppCompatActivity implements MembersAdapter.OnUserSelectionListener {

    RecyclerView followerRecyclerView;
    RecyclerView otherUsersRecyclerView;
    MembersAdapter followersAdapter;
    MembersAdapter otherUsersAdapter;
    List<MemberModel> followersList;
    List<MemberModel> otherUsersList;
    Set<String> selectedUserIds;
    String forumTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_members);

        followerRecyclerView = findViewById(R.id.followersrecview);
        followerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        otherUsersRecyclerView = findViewById(R.id.otherusersrecview);
        otherUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        followersList = new ArrayList<>();
        otherUsersList = new ArrayList<>();
        selectedUserIds = new HashSet<>();

        followersAdapter = new MembersAdapter(followersList, this);
        otherUsersAdapter = new MembersAdapter(otherUsersList, this);

        followerRecyclerView.setAdapter(followersAdapter);
        otherUsersRecyclerView.setAdapter(otherUsersAdapter);

        forumTopic = getIntent().getStringExtra("topic"); // Assuming you pass topic via Intent

        DatabaseReference followersRef = FirebaseDatabase.getInstance()
                .getReference("Follow")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("followers");

        followersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String followerId = dataSnapshot.getKey();
                    if (followerId != null) {
                        DatabaseReference userRef = FirebaseDatabase.getInstance()
                                .getReference("Users")
                                .child(followerId);

                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    MemberModel member = new MemberModel(followerId);
                                    member.setSelected(true); // Mark followers as selected by default
                                    followersList.add(member);
                                    selectedUserIds.add(followerId); // Add to selected users
                                    followersAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("ForumMembers", "Failed to read user", error.toException());
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ForumMembers", "Failed to read followers", error.toException());
            }
        });

        DatabaseReference usersRef = FirebaseDatabase.getInstance()
                .getReference("Users");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                otherUsersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String userId = dataSnapshot.getKey();
                    if (userId != null && !userId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        boolean isFollower = false;
                        for (MemberModel follower : followersList) {
                            if (follower.getUid().equals(userId)) {
                                isFollower = true;
                                break;
                            }
                        }
                        if (!isFollower) {
                            MemberModel member = new MemberModel(userId);
                            member.setSelected(false);
                            otherUsersList.add(member);
                            otherUsersAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ForumMembers", "Failed to read other users", error.toException());
            }
        });

        findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.button).setVisibility(View.INVISIBLE);
                findViewById(R.id.progressBar6).setVisibility(View.VISIBLE);
                createForumInDatabase();
            }
        });
    }

    private void createForumInDatabase() {
        DatabaseReference forumsRef = FirebaseDatabase.getInstance().getReference("Forums");
        String forumId = forumsRef.push().getKey();

        if (forumId != null) {
            // Construct forum name
            SharedPreferences pre=getSharedPreferences("user_data",MODE_PRIVATE);
            String forumName=pre.getString("name",null)+"'s Forum for " + getIntent().getStringExtra("topic");

            // Create map to represent forum data
            Map<String, Object> forumData = new HashMap<>();
            forumData.put("creatorid", FirebaseAuth.getInstance().getCurrentUser().getUid());
            forumData.put("forumname", forumName);
            forumData.put("forumtopic", forumTopic);
            Map<String, Boolean> members = new HashMap<>();
            for (String userId : selectedUserIds) {
                members.put(userId, true);
            }
            forumData.put("members", members);

            forumsRef.child(forumId).setValue(forumData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(ForumMembers.this, "Forum created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ForumMembers.this, "Failed to create forum: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ChatsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    });
        } else {
            Log.e("ForumMembers", "Failed to generate forum ID");
        }
    }

    @Override
    public void onUserSelected(String userId) {
        selectedUserIds.add(userId);
    }

    @Override
    public void onUserDeselected(String userId) {
        selectedUserIds.remove(userId);
    }

    public Set<String> getSelectedUserIds() {
        return selectedUserIds;
    }
}
