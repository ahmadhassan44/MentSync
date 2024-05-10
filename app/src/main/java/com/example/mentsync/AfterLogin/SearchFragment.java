package com.example.mentsync.AfterLogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentsync.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    View searchFragment;

    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchFragment=inflater.inflate(R.layout.fragment_search, container, false);
        return searchFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<SearchUserItemModel> queryresult=new ArrayList<>();
        SearchView searchView=searchFragment.findViewById(R.id.searchview);
        RecyclerView recyclerView=searchFragment.findViewById(R.id.recyclerview);

    }
}