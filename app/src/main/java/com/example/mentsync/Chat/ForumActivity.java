package com.example.mentsync.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mentsync.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ForumActivity extends AppCompatActivity {

    private String forumid;
    private RecyclerView imageRecyclerView;
    private ForumImageAdapter adapter;
    private List<String> imageUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        forumid = getIntent().getStringExtra("forumid");
        imageUrlList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Forums").child(forumid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String forumName = snapshot.child("forumname").getValue(String.class);
                ((TextView)findViewById(R.id.textView21)).setText(snapshot.child("forumtopic").getValue(String.class));

                int imageResource;
                switch (snapshot.child("forumtopic").getValue(String.class)) {
                    case "Web Development":
                        imageResource = R.drawable.web;
                        break;
                    case "App Development":
                        imageResource = R.drawable.app;
                        break;
                    case "Data Structures and Algorithms":
                        imageResource = R.drawable.dsa;
                        break;
                    case "Masters":
                        imageResource = R.drawable.foreign;
                        break;
                    case "Interviews":
                        imageResource = R.drawable.interview;
                        break;
                    case "Work life balance":
                        imageResource = R.drawable.balance;
                        break;
                    default:
                        imageResource = R.drawable.placeholder;
                        break;
                }
                Glide.with(ForumActivity.this)
                        .load(imageResource)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.baseline_clear_24)
                        .into((CircleImageView) findViewById(R.id.circleImageView4));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ForumActivity.this, "Error loading forum details", Toast.LENGTH_SHORT).show();
            }
        });

        imageRecyclerView = findViewById(R.id.messages);
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ForumImageAdapter(imageUrlList);
        imageRecyclerView.setAdapter(adapter);

        findViewById(R.id.attachImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(ForumActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("image/*");
                                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                                startActivityForResult(Intent.createChooser(intent, "Browse Image"), 123);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                Toast.makeText(ForumActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                imageUrlList.add(imageUri.toString());
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Failed to retrieve image URI", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
