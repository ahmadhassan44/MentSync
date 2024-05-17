package com.example.mentsync.AfterLogin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentsync.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private View searchFragment;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchUserAdapter adapter;
    private List<LoggedInUser> mUsers;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchFragment = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = searchFragment.findViewById(R.id.searchview);
        recyclerView = searchFragment.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return searchFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchUser(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchUser(newText);
                return true;
            }
        });
    }

    private void searchUser(String s) {
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("name")
                .startAt(s)
                .endAt(s + "\uf8ff");

        FirebaseRecyclerOptions<SearchUserItemModel> options =
                new FirebaseRecyclerOptions.Builder<SearchUserItemModel>()
                        .setQuery(query, snapshot -> {
                            String profile_pic = snapshot.child("profile_pic").getValue(String.class);
                            String name = snapshot.child("name").getValue(String.class);
                            return new SearchUserItemModel(profile_pic, name);
                        })
                        .build();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SearchUserItemModel user = snapshot.getValue(SearchUserItemModel.class);
                    if (user != null) {
                        Log.d("ahmad", "User found: " + user.getName() + ", Profile Pic: " + user.getProfilepic());
                    } else {
                        Log.d("ahmad", "User data is null");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (adapter != null) {
            adapter.updateOptions(options);
        } else {
            adapter = new SearchUserAdapter(options, getContext());
            recyclerView.setAdapter(adapter);
        }
        adapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.startListening();
        }
    }
}
