package com.example.mentsync.Chat;

import android.content.Context;
import android.content.Intent;
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

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    private Context context;
    private List<String> forumIdList;

    public ForumAdapter(Context context, List<String> forumIdList) {
        this.context = context;
        this.forumIdList = forumIdList;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forumitem, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        String forumId = forumIdList.get(position);
        holder.bind(forumId);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ForumActivity.class);
                intent.putExtra("forumid", forumId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return forumIdList.size();
    }

    public static class ForumViewHolder extends RecyclerView.ViewHolder {

        private TextView forumIdTextView;
        private CircleImageView pic;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            forumIdTextView = itemView.findViewById(R.id.textView42);
            pic = itemView.findViewById(R.id.circleImageView6);
        }

        public void bind(String forumId) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Forums").child(forumId);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String forumName = snapshot.child("forumname").getValue(String.class);
                    String topic = snapshot.child("forumtopic").getValue(String.class);

                    forumIdTextView.setText(forumName);
                    setTopicImage(topic);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        private void setTopicImage(String topic) {
            int imageResource;
            switch (topic) {
                case "Web Development":
                    imageResource = R.drawable.web;
                    break;
                case "App Development":
                    imageResource = R.drawable.app;
                    break;
                case "Data Structures and Algorithms":
                    imageResource = R.drawable.dsa;
                    break;
                case "Masters":
                    imageResource = R.drawable.foreign;
                    break;
                case "Interviews":
                    imageResource = R.drawable.interview;
                    break;
                case "Work life balance":
                    imageResource = R.drawable.balance;
                    break;
                default:
                    imageResource = R.drawable.placeholder;
                    break;
            }

            Glide.with(pic.getContext())
                    .load(imageResource)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.baseline_clear_24)
                    .into(pic);
        }
    }
}
