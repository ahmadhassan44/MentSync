    package com.example.mentsync.AfterLogin.Feed;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.os.Bundle;
    import android.util.Log;

    import com.example.mentsync.R;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;
    import java.util.List;

    public class AnswersActivity extends AppCompatActivity {

        private RecyclerView recview;
        private List<AnswerModel> answers;
        private AnswerAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_answers);

            recview = findViewById(R.id.recview);
            recview.setLayoutManager(new LinearLayoutManager(this));

            answers = new ArrayList<>();
            adapter = new AnswerAdapter(answers);
            recview.setAdapter(adapter);

            String queryId = getIntent().getStringExtra("queryId");
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Query").child(queryId).child("answers");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    answers.clear(); // Clear the list before adding new data
                    for(DataSnapshot answerSnapshot : snapshot.getChildren()) {
                        String uid = answerSnapshot.getKey(); // Assuming UID is the key of each answer
                        String answerText = answerSnapshot.getValue(String.class);

                        AnswerModel answerModel = new AnswerModel(uid, answerText,queryId);
                        answers.add(answerModel);
                    }
                    adapter.notifyDataSetChanged(); // Notify adapter after adding data
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("AnswersActivity", "Error fetching data", error.toException());
                }
            });
        }
    }
