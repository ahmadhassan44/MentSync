package com.example.mentsync.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mentsync.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserChat extends AppCompatActivity {
    TextView name;
    CircleImageView propic;
    EditText messeageTextView;
    FloatingActionButton sendBtn;
    RecyclerView messagesRecyclerView;
    MessageAdapter messagesAdapter;
    String otherUserid;
    String currentUserid;
    List<MessageModel> messageList;
    String chatRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
        name = findViewById(R.id.textView21);
        propic = findViewById(R.id.circleImageView4);
        sendBtn = findViewById(R.id.floatingActionButton);
        messagesRecyclerView = findViewById(R.id.messages);
        messeageTextView = findViewById(R.id.messageTextView);
        otherUserid = getIntent().getStringExtra("uid");
        currentUserid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        messageList = new ArrayList<>();
        messagesAdapter = new MessageAdapter(getApplicationContext(), messageList, currentUserid);
        messagesRecyclerView.setAdapter(messagesAdapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set the layout manager
        loadMessages();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(otherUserid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("name").getValue(String.class));
                Glide.with(propic.getContext())
                        .load(snapshot.child("profile_pic").getValue(String.class))
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.baseline_clear_24)
                        .into(propic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserChat.this, "Failed to load user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messeageTextView.getText().toString();
                if (message.isEmpty()) {
                    messeageTextView.setError("Type some message");
                } else {
                    String messageId = ref.push().getKey();
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("message", message);
                    updates.put("senderid", currentUserid);
                    updates.put("receiverid", otherUserid);
                    updates.put("timestamp", (new SimpleDateFormat("HH:mm", Locale.getDefault())).format(new Date()));

                    DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference()
                            .child("Chats").child(chatRoomId).child(messageId);
                    messageRef.updateChildren(updates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        messeageTextView.setText("");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to send message", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void loadMessages() {
        generateChatRoomId(currentUserid, otherUserid);
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Chats").child(chatRoomId);
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageModel message = snapshot.getValue(MessageModel.class);
                    if (message != null) {
                        messageList.add(message);
                    }
                }
                messagesAdapter.notifyDataSetChanged();
                messagesRecyclerView.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserChat.this, "Failed to load messages: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateChatRoomId(String userId1, String userId2) {
        List<String> userIds = new ArrayList<>();
        userIds.add(userId1);
        userIds.add(userId2);
        Collections.sort(userIds);
        chatRoomId = userIds.get(0) + "_" + userIds.get(1); // Use "_" to separate user IDs
    }
}
