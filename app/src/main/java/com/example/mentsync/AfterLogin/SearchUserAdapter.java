package com.example.mentsync.AfterLogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mentsync.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchUserAdapter extends FirebaseRecyclerAdapter<SearchUserItemModel,SearchUserAdapter.ViewHolder> {
    Context context;
    public SearchUserAdapter(@NonNull FirebaseRecyclerOptions<SearchUserItemModel> options,Context context) {
        super(options);
        this.context=context;
    }
    class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView image;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=(CircleImageView) itemView.findViewById(R.id.profilepic3);
            name=(TextView)itemView.findViewById(R.id.name);
        }
    }
    @Override
    protected void onBindViewHolder(@NonNull SearchUserAdapter.ViewHolder holder, int position, @NonNull SearchUserItemModel model) {
        holder.name.setText(model.name);
        Glide.with(holder.image.getContext())
                .load(model.profilepic)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.image);
    }
    @NonNull
    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.serachuseritem,parent,false);
        return new ViewHolder(view);
    }
}
