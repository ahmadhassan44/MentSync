package com.example.mentsync.AfterLogin;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mentsync.HandshakeErrorTackler;
import com.example.mentsync.IPAddress;
import com.example.mentsync.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        HandshakeErrorTackler.fixerror();
        ArrayList<SearchUserItemModel> queryresult=new ArrayList<>();
        SearchView searchView=searchFragment.findViewById(R.id.searchview);
        RecyclerView recyclerView=searchFragment.findViewById(R.id.recyclerview);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url="https://"+ IPAddress.ipaddress+"/searchuser.php";
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()) {
                    StringRequest search = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject searchresults = new JSONObject(response);
                                if (searchresults.getBoolean("usersFound")) {
                                    Log.d("ahmad", "raw ersponse:" + response);
                                    JSONArray usersarray = searchresults.getJSONArray("users");
                                    queryresult.clear();
                                    for (int i = 0; i < usersarray.length(); i++) {
                                        JSONObject user = usersarray.getJSONObject(i);
                                        queryresult.add(new SearchUserItemModel(user.getString("imageUrl"), user.getString("name")));
                                    }
                                    SearchUserAdapter adapter = new SearchUserAdapter(getActivity().getApplicationContext(), queryresult);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    recyclerView.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("searched_user", newText);
                            return paramV;
                        }
                    };
                    queue.add(search);
                }
                else
                {
                    queryresult.clear();
                    SearchUserAdapter adapter = new SearchUserAdapter(getActivity().getApplicationContext(), queryresult);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });
    }
}