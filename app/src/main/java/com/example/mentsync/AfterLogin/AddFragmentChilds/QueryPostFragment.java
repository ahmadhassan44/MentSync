package com.example.mentsync.AfterLogin.AddFragmentChilds;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mentsync.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.time.LocalDate;
import java.util.HashMap;


public class QueryPostFragment extends Fragment {

    View queryfrag;
    Button postqurybtn;
    Button discardquerybtn;
    public QueryPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        queryfrag=inflater.inflate(R.layout.fragment_query_post, container, false);
        return queryfrag;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText queryedittext=queryfrag.findViewById(R.id.queryedit);
        discardquerybtn=queryfrag.findViewById(R.id.button3);
        postqurybtn=queryfrag.findViewById(R.id.button2);
        queryedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(queryedittext.getText().length()>1)
                {
                    queryfrag.findViewById(R.id.button2).setVisibility(View.VISIBLE);
                    queryfrag.findViewById(R.id.button3).setVisibility(View.VISIBLE);
                }
                if(queryedittext.getText().length()==0)
                {
                    queryfrag.findViewById(R.id.button2).setVisibility(View.INVISIBLE);
                    queryfrag.findViewById(R.id.button3).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        postqurybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryfrag.findViewById(R.id.progressBar2).setVisibility(View.VISIBLE);
                String querytext=queryedittext.getText().toString();
                String queryId = FirebaseDatabase.getInstance().getReference().child("Query").push().getKey();
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Query").child(queryId);
                HashMap<String,Object> querydata=new HashMap<>();
                querydata.put("Text",querytext);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    querydata.put("date",LocalDate.now().toString());
                }
                querydata.put("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                ref.updateChildren(querydata).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        queryfrag.findViewById(R.id.progressBar2).setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity().getApplicationContext(), "Query made!", Toast.LENGTH_LONG).show();
                        queryedittext.setText(null);
                        postqurybtn.setVisibility(View.INVISIBLE);
                        discardquerybtn.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        queryfrag.findViewById(R.id.progressBar2).setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity().getApplicationContext(), "Failed to make Query!" +e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        discardquerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryedittext.setText(null);
                discardquerybtn.setVisibility(View.INVISIBLE);
                postqurybtn.setVisibility(View.INVISIBLE);
            }
        });
    }

}