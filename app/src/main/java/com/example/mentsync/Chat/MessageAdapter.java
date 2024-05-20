package com.example.mentsync.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentsync.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private Context context;
    private List<MessageModel> messageList;
    private String currentUserid;

    public MessageAdapter(Context context, List<MessageModel> messageList, String currentUserid) {
        this.context = context;
        this.messageList = messageList;
        this.currentUserid = currentUserid;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            // Inflate outgoing message layout
            view = LayoutInflater.from(context).inflate(R.layout.sentmsg, parent, false);
        } else {
            // Inflate incoming message layout
            view = LayoutInflater.from(context).inflate(R.layout.receivedmsg, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageModel message = messageList.get(position);

        holder.messageTextView.setText(message.getMessage());
        holder.timestampTextView.setText(message.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessageModel message = messageList.get(position);
        if (message.getSenderid().equals(currentUserid)) {
            return 1;
        } else {
            return 2;
        }
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageTextView, timestampTextView;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            messageTextView = itemView.findViewById(R.id.textView25);
            timestampTextView = itemView.findViewById(R.id.textView26);
        }
    }
}
