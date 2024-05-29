    package com.example.mentsync.Chat;

    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ProgressBar;

    import com.example.mentsync.R;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;
    import java.util.List;

    public class ChatFragment extends Fragment {
        private List<ChatUserModel> chatUserModels;
        private RecyclerView recyclerView;
        private FollowedUsersAdapter adapter;
        private ProgressBar progressBar;
        private View chatFrag;

        public ChatFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            chatFrag = LayoutInflater.from(getContext()).inflate(R.layout.fragment_chat, container, false);
            return chatFrag;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            progressBar=chatFrag.findViewById(R.id.progressBar4);
            progressBar.setVisibility(View.VISIBLE);
            recyclerView=chatFrag.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            chatUserModels=new ArrayList<>();
            adapter=new FollowedUsersAdapter(chatUserModels);
            recyclerView.setAdapter(adapter);
            fetchFollowedUsers();
        }

        private void fetchFollowedUsers() {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Follow")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("following");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    chatUserModels.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.getValue(Boolean.class)) {
                            String followedUserId = snapshot.getKey();
                            Log.d("FollowedUsersAdapter",followedUserId);
                            ChatUserModel userModel = new ChatUserModel(followedUserId);
                            chatUserModels.add(userModel);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }