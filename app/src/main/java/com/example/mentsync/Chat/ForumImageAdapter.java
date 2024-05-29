package com.example.mentsync.Chat;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mentsync.R;

import java.util.List;

public class ForumImageAdapter extends RecyclerView.Adapter<ForumImageAdapter.ImageViewHolder> {

    private List<String> imageUrlList;

    public ForumImageAdapter(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forumimage, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = imageUrlList.get(position);
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.baseline_clear_24)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrlList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
         ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
             imageView = itemView.findViewById(R.id.imageView2);
        }
    }
}
