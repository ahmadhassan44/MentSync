package com.example.mentsync.AfterLogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
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
    private static final String TAG = "SearchUserAdapter";
    private Context context;
    private FirebaseUser firebaseUser;

    public SearchUserAdapter(@NonNull FirebaseRecyclerOptions<SearchUserItemModel> options, Context context) {
        super(options);
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name;
        Button btn_follow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profilepic3);
            name = itemView.findViewById(R.id.name);
            btn_follow = itemView.findViewById(R.id.button4);
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull SearchUserAdapter.ViewHolder holder, int position, @NonNull SearchUserItemModel model) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        holder.name.setText(model.getName());
        Glide.with(holder.image.getContext())
                .load(model.getProfilepic())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.image);

        String userId = model.getUser_id();
        if (userId == null) {
            Log.e(TAG, "User ID is null for model: " + model);
            return;
        }

        isFollowing(userId, holder.btn_follow);

        if (firebaseUser != null && userId.equals(firebaseUser.getUid())) {
            holder.btn_follow.setVisibility(View.GONE);
        } else {
            holder.btn_follow.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(v -> {
            SharedPreferences.Editor editor = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
            editor.putString("profileID", userId);
            editor.apply();

            if (context instanceof FragmentActivity) {
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.searchview, new ProfileFragment())
                        .addToBackStack(null) // Optional: add to back stack if needed
                        .commit();
            } else {
                Log.e(TAG, "Context is not an instance of FragmentActivity");
            }
        });

        holder.btn_follow.setOnClickListener(v -> {
            if (holder.btn_follow.getText().toString().equals("follow")) {
                followUser(userId);
            } else {
                unfollowUser(userId);
            }
        });
    }

    private void followUser(String userId) {
        if (firebaseUser == null) return;
        DatabaseReference followRef = FirebaseDatabase.getInstance().getReference().child("Follow");
        followRef.child(firebaseUser.getUid()).child("following").child(userId).setValue(true);
        followRef.child(userId).child("followers").child(firebaseUser.getUid()).setValue(true);
    }

    private void unfollowUser(String userId) {
        if (firebaseUser == null) return;
        DatabaseReference followRef = FirebaseDatabase.getInstance().getReference().child("Follow");
        followRef.child(firebaseUser.getUid()).child("following").child(userId).removeValue();
        followRef.child(userId).child("followers").child(firebaseUser.getUid()).removeValue();
    }


    @NonNull
    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.serachuseritem, parent, false);
        return new ViewHolder(view);
    }

    private void isFollowing(final String userID, final Button button) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userID).exists()) {
                    button.setText("following");
                } else {
                    button.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.getMessage());
            }
        });
    }

}

