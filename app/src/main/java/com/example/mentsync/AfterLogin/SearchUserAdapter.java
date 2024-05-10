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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {

    Context context;
    ArrayList<SearchUserItemModel> searchresult;
    SearchUserAdapter(Context context,ArrayList<SearchUserItemModel> searchresult)
    {
        this.context=context;
        this.searchresult=searchresult;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View l=LayoutInflater.from(parent.getContext()).inflate(R.layout.serachuseritem,parent,false);
        ViewHolder viewHolder=new ViewHolder(l);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(searchresult.get(position).name);
    }

    @Override
    public int getItemCount() {
        return searchresult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        CircleImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.profilepic3);
        }
    }
}
