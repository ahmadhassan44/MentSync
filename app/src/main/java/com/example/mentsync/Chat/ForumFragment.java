package com.example.mentsync.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import java.util.List;
import java.util.Map;

public class ForumFragment extends Fragment {

    private View forumFrag;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ForumAdapter forumAdapter;
    private List<String> forumIdList;

    public ForumFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        forumFrag = inflater.inflate(R.layout.fragment_forum, container, false);
        return forumFrag;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = forumFrag.findViewById(R.id.rec);
        progressBar = forumFrag.findViewById(R.id.progressBar5);

        forumIdList = new ArrayList<>();
        forumAdapter = new ForumAdapter(getContext(),forumIdList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(forumAdapter);

        fetchForums();

        forumFrag.findViewById(R.id.floatingActionButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ForumTopic.class));
            }
        });
    }

    private void fetchForums() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference forumsRef = FirebaseDatabase.getInstance().getReference("Forums");
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        forumsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                forumIdList.clear();
                for (DataSnapshot forumSnapshot : snapshot.getChildren()) {
                    Map<String, Object> forum = (Map<String, Object>) forumSnapshot.getValue();
                    if (forum != null) {
                        String creatorId = (String) forum.get("creatorid");
                        Map<String, Boolean> members = (Map<String, Boolean>) forum.get("members");

                        if ((creatorId != null && creatorId.equals(currentUserId)) ||
                                (members != null && members.containsKey(currentUserId) && members.get(currentUserId))) {
                            forumIdList.add(forumSnapshot.getKey());
                        }
                    }
                }
                forumAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
