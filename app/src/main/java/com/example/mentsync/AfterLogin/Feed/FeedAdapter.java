package com.example.mentsync.AfterLogin.Feed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_IMAGE_POST = 1;
    private static final int TYPE_QUERY_POST = 2;
    private List<Object> combinedList;

    public FeedAdapter(List<Object> combinedList) {
        this.combinedList = combinedList;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object model = combinedList.get(position);
        switch (holder.getItemViewType()) {
            case TYPE_IMAGE_POST:
                ((ImagePostViewHolder) holder).bind((ImagePostModel) model);
                break;
            case TYPE_QUERY_POST:
                ((QueryPostViewHolder) holder).bind((QueryPostModel) model);
                break;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_IMAGE_POST:
                View imagePostView = inflater.inflate(R.layout.postitem, parent, false);
                viewHolder = new ImagePostViewHolder(imagePostView);
                break;
            case TYPE_QUERY_POST:
                View queryPostView = inflater.inflate(R.layout.queryitem, parent, false);
                viewHolder = new QueryPostViewHolder(queryPostView);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = combinedList.get(position);
        if (item instanceof ImagePostModel) {
            return TYPE_IMAGE_POST;
        } else if (item instanceof QueryPostModel) {
            return TYPE_QUERY_POST;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return combinedList.size();
    }

    public static class ImagePostViewHolder extends RecyclerView.ViewHolder {

        ImageView profilepic;
        TextView name;
        TextView date;
        TextView caption;
        ImageView postimage;
        TextView likecount;

        public ImagePostViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView16);
            date = itemView.findViewById(R.id.textView17);
            profilepic = itemView.findViewById(R.id.circleImageView);
            postimage = itemView.findViewById(R.id.imageView);
            caption = itemView.findViewById(R.id.textView19);
            likecount = itemView.findViewById(R.id.textView18);
        }

        public void bind(ImagePostModel item) {
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(item.uid);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Glide.with(profilepic.getContext())
                            .load(snapshot.child("profile_pic").getValue(String.class))
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.baseline_clear_24)
                            .into(profilepic);
                    name.setText(snapshot.child("name").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            date.setText(item.date);
            caption.setText(item.caption);
            Glide.with(postimage.getContext())
                    .load(item.imgurl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.baseline_clear_24)
                    .into(postimage);
            likecount.setText(item.likes.toString());
        }
    }

    public static class QueryPostViewHolder extends RecyclerView.ViewHolder {
        ImageView profilepic;
        TextView name;
        TextView querybody;
        TextView date;
        EditText youranswer;
        Button showAnswers;
        Button postanswer;
        RecyclerView answers;

        public QueryPostViewHolder(@NonNull View itemView) {
            super(itemView);
            profilepic = itemView.findViewById(R.id.circleImageView);
            name = itemView.findViewById(R.id.textView16);
            date = itemView.findViewById(R.id.textView17);
            querybody = itemView.findViewById(R.id.textView4);
            showAnswers = itemView.findViewById(R.id.buttonShowAnswers);
            youranswer = itemView.findViewById(R.id.editTextAnswer);
            postanswer = itemView.findViewById(R.id.buttonPostAnswer);
        }

        public void bind(QueryPostModel item) {
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users").child(item.uid);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Glide.with(profilepic.getContext())
                            .load(snapshot.child("profile_pic").getValue(String.class))
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.baseline_clear_24)
                            .into(profilepic);
                    name.setText(snapshot.child("name").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            date.setText(item.date);
            querybody.setText(item.Text);
        }
    }
}
