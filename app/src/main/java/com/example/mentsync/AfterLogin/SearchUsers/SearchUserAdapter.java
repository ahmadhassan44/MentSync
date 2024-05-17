package com.example.mentsync.AfterLogin.SearchUsers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mentsync.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchUserAdapter extends FirebaseRecyclerAdapter<SearchUserItemModel, SearchUserAdapter.ViewHolder> {
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
        holder.name.setText(model.getName());
        Glide.with(holder.image.getContext())
                .load(model.getProfilepic())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.baseline_clear_24)
                .into(holder.image);

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

}

