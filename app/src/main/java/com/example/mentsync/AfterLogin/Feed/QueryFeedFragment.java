package com.example.mentsync.AfterLogin.Feed;

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

import com.example.mentsync.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QueryFeedFragment extends Fragment {
    private RecyclerView mainrecview;
    private View queryPostFeedView;
    private FeedAdapter feedAdapter;
    private List<Object> queryPosts;

    public QueryFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        queryPostFeedView = inflater.inflate(R.layout.fragment_query_feed, container, false);
        return queryPostFeedView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainrecview = queryPostFeedView.findViewById(R.id.mainrec1);
        mainrecview.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts = new ArrayList<>();
        feedAdapter = new FeedAdapter(queryPosts);
        mainrecview.setAdapter(feedAdapter);

        fetchData();
    }

    private void fetchData() {
        DatabaseReference queriesRef = FirebaseDatabase.getInstance().getReference().child("Query");

        ValueEventListener queryListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<QueryPostModel> tempList = new ArrayList<>(); // Temporary list to hold posts

                for (DataSnapshot querySnapshot : snapshot.getChildren()) {
                    QueryPostModel queryPost = querySnapshot.getValue(QueryPostModel.class);
                    if (queryPost != null) {
                        queryPost.setQueryId(querySnapshot.getKey()); // Set the queryId manually
                        tempList.add(queryPost);
                        Log.d("QueryPostFeedFragment", "QueryPost retrieved: " + queryPost.toString());
                    } else {
                        Log.e("QueryPostFeedFragment", "QueryPost is null for snapshot: " + querySnapshot.toString());
                    }
                }

                // Sort the list by date
                Collections.sort(tempList, new Comparator<QueryPostModel>() {
                    @Override
                    public int compare(QueryPostModel post1, QueryPostModel post2) {
                        return post2.getDate().compareTo(post1.getDate()); // Sort by date in descending order
                    }
                });

                queryPosts.clear();
                queryPosts.addAll(tempList);
                feedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        };

        queriesRef.addListenerForSingleValueEvent(queryListener);
    }
}
