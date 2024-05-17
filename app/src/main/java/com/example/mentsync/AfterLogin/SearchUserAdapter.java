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
    }
    @NonNull
    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.serachuseritem, parent, false);
        return new ViewHolder(view);
    }

}

