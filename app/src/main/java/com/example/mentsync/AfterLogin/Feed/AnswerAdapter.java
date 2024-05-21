package com.example.mentsync.AfterLogin.Feed;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mentsync.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {
    private List<AnswerModel> answerList;

    public AnswerAdapter(List<AnswerModel> answerList) {
        this.answerList = answerList;
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answeritem, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        AnswerModel answerModel = answerList.get(position);
        holder.bind(answerModel);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if((answerModel.getUid()).equals( FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    showDeleteOption(holder.itemView);
                }
                return false;
            }

            private void showDeleteOption(View anchorView) {
                View view = LayoutInflater.from(anchorView.getContext()).inflate(R.layout.deletedialog, null);
                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // Lets taps outside the popup also dismiss it
                PopupWindow window = new PopupWindow(view, width, height, focusable);
                window.showAsDropDown(anchorView, 0, 0);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAnswer();
                    }

                    private void deleteAnswer() {
                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Query").child(answerModel.getQueryId()).child("answers").child(answerModel.getUid());
                        ref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                answerList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, answerList.size());
                                Toast.makeText(view.getContext(), "Answer Deleted!", Toast.LENGTH_SHORT).show();
                                window.dismiss();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {
        TextView answerText;
        TextView name;
        CircleImageView pic;

        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            answerText = itemView.findViewById(R.id.textView20);
            name=itemView.findViewById(R.id.textView14);
            pic=itemView.findViewById(R.id.circleImageView2);
        }

        public void bind(AnswerModel answerModel) {
            answerText.setText(answerModel.getAnswer());
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users").child(answerModel.getUid());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name.setText(snapshot.child("name").getValue(String.class));
                    Glide.with(pic.getContext())
                            .load(snapshot.child("profile_pic").getValue(String.class))
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.baseline_clear_24)
                            .into(pic);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}

