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


public class PostsFeedFragment extends Fragment {
    private RecyclerView mainrecview;
    private View imagePostFeedView;
    private FeedAdapter feedAdapter;
    private List<Object> imagePosts;

    public PostsFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        imagePostFeedView = inflater.inflate(R.layout.fragment_posts_feed, container, false);
        return imagePostFeedView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainrecview = imagePostFeedView.findViewById(R.id.mainrec2);
        mainrecview.setLayoutManager(new LinearLayoutManager(getContext()));

        imagePosts = new ArrayList<>();
        feedAdapter = new FeedAdapter(imagePosts);
        mainrecview.setAdapter(feedAdapter);

        fetchData();
    }

    private void fetchData() {
            DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child("Post");

            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<ImagePostModel> tempList = new ArrayList<>(); // Temporary list to hold posts

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        ImagePostModel imagePost = postSnapshot.getValue(ImagePostModel.class);
                        if (imagePost != null) {
                            imagePost.setPostId(postSnapshot.getKey()); // Set the postId manually
                            tempList.add(imagePost);
                            Log.d("ImagePostFeedFragment", "ImagePost retrieved: " + imagePost.toString());
                        } else {
                            Log.e("ImagePostFeedFragment", "ImagePost is null for snapshot: " + postSnapshot.toString());
                        }
                    }

                    // Sort the list by date
                    Collections.sort(tempList, new Comparator<ImagePostModel>() {
                        @Override
                        public int compare(ImagePostModel post1, ImagePostModel post2) {
                            return post2.getDate().compareTo(post1.getDate()); // Sort by date in descending order
                        }
                    });

                    imagePosts.clear();
                    imagePosts.addAll(tempList);
                    feedAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors.
                }
            };
            postsRef.addListenerForSingleValueEvent(postListener);
        }
}