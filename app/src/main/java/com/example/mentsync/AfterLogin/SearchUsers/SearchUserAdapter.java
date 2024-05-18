package com.example.mentsync.AfterLogin.SearchUsers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mentsync.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchUserAdapter extends FirebaseRecyclerAdapter<SearchUserItemModel, SearchUserAdapter.ViewHolder> {
    private Context context;
    private FirebaseUser firebaseUser;
    private String currentUserId;

    public SearchUserAdapter(@NonNull FirebaseRecyclerOptions<SearchUserItemModel> options, Context context,String currentUserId) {
        super(options);
        this.context = context;
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.currentUserId = currentUserId;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name;
        Button btn_follow;
        Button btn_unfollow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profilepic3);
            name = itemView.findViewById(R.id.name);
            btn_follow = itemView.findViewById(R.id.follow);
            btn_unfollow = itemView.findViewById(R.id.unfollow);

            btn_follow.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    SearchUserItemModel clickedUser = getItem(position);
                    followUser(clickedUser.getUid());
                    btn_follow.setVisibility(View.INVISIBLE);
                    btn_unfollow.setVisibility(View.VISIBLE);
                }
            });

            btn_unfollow.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    SearchUserItemModel clickedUser = getItem(position);
                    unfollowUser(clickedUser.getUid());
                    btn_follow.setVisibility(View.VISIBLE);
                    btn_unfollow.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull SearchUserAdapter.ViewHolder holder, int position, @NonNull SearchUserItemModel model) {
        if (model.getUid().equals(currentUserId)) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.name.setText(model.getName());
            Glide.with(holder.image.getContext())
                    .load(model.getProfilepic())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.baseline_clear_24)
                    .into(holder.image);
        }

        isUserFollowed(model.getUid(), isFollowed -> {
            if (isFollowed) {
                holder.btn_follow.setVisibility(View.INVISIBLE);
                holder.btn_unfollow.setVisibility(View.VISIBLE);
            } else {
                holder.btn_follow.setVisibility(View.VISIBLE);
                holder.btn_unfollow.setVisibility(View.INVISIBLE);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                SearchUserItemModel clickedUser = getItem(adapterPosition);
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("userId", clickedUser.getUid());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.serachuseritem, parent, false);
        return new ViewHolder(view);
    }

    private void followUser(String userIdToFollow) {
        DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference("Follow")
                .child(firebaseUser.getUid()).child("following").child(userIdToFollow);
        currentUserRef.setValue(true);

        DatabaseReference followedUserRef = FirebaseDatabase.getInstance().getReference("Follow")
                .child(userIdToFollow).child("followers").child(firebaseUser.getUid());
        followedUserRef.setValue(true);
    }

    private void unfollowUser(String userIdToUnfollow) {
        DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference("Follow")
                .child(firebaseUser.getUid()).child("following").child(userIdToUnfollow);
        currentUserRef.removeValue();

        DatabaseReference unfollowedUserRef = FirebaseDatabase.getInstance().getReference("Follow")
                .child(userIdToUnfollow).child("followers").child(firebaseUser.getUid());
        unfollowedUserRef.removeValue();
    }

    private void isUserFollowed(String userId, final OnFollowCheckListener listener) {
        DatabaseReference followRef = FirebaseDatabase.getInstance().getReference("Follow")
                .child(firebaseUser.getUid()).child("following").child(userId);

        followRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onCheck(dataSnapshot.exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onCheck(false);
            }
        });
    }

    private interface OnFollowCheckListener {
        void onCheck(boolean isFollowed);
    }
}
