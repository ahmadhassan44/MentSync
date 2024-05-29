package com.example.mentsync.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentsync.R;

import java.util.List;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicViewHolder> {

    private List<Topics> topicsList;
    private Context context;
    private int selectedPosition = -1;
    private OnItemClickListener onItemClickListener;

    public TopicsAdapter(Context context, List<Topics> topicsList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.topicsList = topicsList;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topicitem, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topics topic = topicsList.get(position);
        holder.textViewTopic.setText(topic.topic);
        holder.textViewDescription.setText(topic.descriprtion);
        holder.logoImageView.setImageResource(topic.logo);

        if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(R.color.md_theme_light_inverseSurface);
        } else {
            holder.itemView.setBackgroundResource(android.R.color.transparent);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged(); // Notify the adapter to refresh views
                onItemClickListener.onItemClick(position); // Notify the activity
            }
        });
    }

    @Override
    public int getItemCount() {
        return topicsList.size();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTopic;
        TextView textViewDescription;
        ImageView logoImageView;

        TopicViewHolder(View itemView) {
            super(itemView);
            textViewTopic = itemView.findViewById(R.id.textView36);
            textViewDescription = itemView.findViewById(R.id.textView37);
            logoImageView = itemView.findViewById(R.id.logos);
        }
    }
}
