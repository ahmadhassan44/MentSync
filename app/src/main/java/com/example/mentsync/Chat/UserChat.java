package com.example.mentsync.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mentsync.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserChat extends AppCompatActivity {
    TextView name;
    CircleImageView propic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
        name=findViewById(R.id.textView21);
        propic=findViewById(R.id.circleImageView4);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child(getIntent().getStringExtra("uid"));
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

            }
        });

    }
}