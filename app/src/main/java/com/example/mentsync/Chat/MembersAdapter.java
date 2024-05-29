package com.example.mentsync.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberViewHolder> {

    private List<MemberModel> membersList;
    private OnUserSelectionListener selectionListener;

    public MembersAdapter(List<MemberModel> membersList, OnUserSelectionListener selectionListener) {
        this.membersList = membersList;
        this.selectionListener = selectionListener;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memberitem, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        MemberModel member = membersList.get(position);
        String memberId = member.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(memberId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.textView.setText(snapshot.child("name").getValue(String.class));
                Glide.with(holder.circleImageView.getContext())
                        .load(snapshot.child("profile_pic").getValue(String.class))
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.baseline_clear_24)
                        .into(holder.circleImageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        // Set visibility based on isSelected
        holder.add.setVisibility(member.isSelected() ? View.INVISIBLE : View.VISIBLE);
        holder.remove.setVisibility(member.isSelected() ? View.VISIBLE : View.INVISIBLE);

        // Add click listeners to toggle isSelected and notify listener
        holder.add.setOnClickListener(v -> {
            member.setSelected(true);
            holder.add.setVisibility(View.INVISIBLE);
            holder.remove.setVisibility(View.VISIBLE);
            if (selectionListener != null) {
                selectionListener.onUserSelected(memberId);
            }
        });

        holder.remove.setOnClickListener(v -> {
            member.setSelected(false);
            holder.add.setVisibility(View.VISIBLE);
            holder.remove.setVisibility(View.INVISIBLE);
            if (selectionListener != null) {
                selectionListener.onUserDeselected(memberId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {

        de.hdodenhof.circleimageview.CircleImageView circleImageView;
        TextView textView;
        ImageButton add;
        ImageButton remove;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circleImageView5);
            textView = itemView.findViewById(R.id.textView41);
            add = itemView.findViewById(R.id.imageButton4);
            remove = itemView.findViewById(R.id.imageButton2);
        }
    }

    public interface OnUserSelectionListener {
        void onUserSelected(String userId);

        void onUserDeselected(String userId);
    }
}
