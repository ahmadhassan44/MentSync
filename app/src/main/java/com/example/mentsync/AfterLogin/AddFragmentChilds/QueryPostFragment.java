package com.example.mentsync.AfterLogin.AddFragmentChilds;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mentsync.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QueryPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QueryPostFragment extends Fragment {

    View queryfrag;
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
        EditText queryedittext=queryfrag.findViewById(R.id.query);
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
    }
}