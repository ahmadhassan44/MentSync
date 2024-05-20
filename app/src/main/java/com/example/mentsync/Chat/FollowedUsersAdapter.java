package com.example.mentsync.Chat;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mentsync.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowedUsersAdapter extends RecyclerView.Adapter<FollowedUsersAdapter.UserViewHolder> {

    private List<ChatUserModel> followedUsers;

    public FollowedUsersAdapter(List<ChatUserModel> followedUsers) {
        this.followedUsers = followedUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatactivityuseritem, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ChatUserModel userModel = followedUsers.get(position);
        Log.d("FollowedUsersAdapter", "User ID: " + userModel.getUid());

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(userModel.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = snapshot.child("name").getValue(String.class);
                String profilePicUrl = snapshot.child("profile_pic").getValue(String.class);

                holder.userIdTextView.setText(userName);

                Glide.with(holder.propic.getContext())
                        .load(profilePicUrl)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.baseline_clear_24)
                        .into(holder.propic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FollowedUsersAdapter", "Error loading user data", error.toException());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), UserChat.class);
                intent.putExtra("uid",userModel.getUid());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return followedUsers.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userIdTextView;
        CircleImageView propic;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userIdTextView = itemView.findViewById(R.id.textView15);
            propic = itemView.findViewById(R.id.circleImageView3);
        }
    }
}
