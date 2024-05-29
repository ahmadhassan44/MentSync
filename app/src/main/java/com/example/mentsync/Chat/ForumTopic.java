package com.example.mentsync.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentsync.R;

import java.util.List;

public class ForumTopic extends AppCompatActivity implements TopicsAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    Button next;
    int selectedPosition;
    List<Topics> topicsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_topic);
        topicsList=Topics.getList();
        topicsList=Topics.getList();
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TopicsAdapter adapter = new TopicsAdapter(this, Topics.getList(), this);
        recyclerView.setAdapter(adapter);

        next = findViewById(R.id.button6);
        next.setVisibility(View.INVISIBLE);

        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != -1) {
                    Topics selectedTopic = topicsList.get(selectedPosition);
                    Intent intent = new Intent(ForumTopic.this,ForumMembers.class);
                    intent.putExtra("topic", selectedTopic.topic);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onItemClick(int position) {
        selectedPosition=position;
        next.setVisibility(View.VISIBLE);
    }
}
