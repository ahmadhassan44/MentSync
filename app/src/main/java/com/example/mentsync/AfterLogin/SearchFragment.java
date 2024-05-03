package com.example.mentsync.AfterLogin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mentsync.IPAddress;
import com.example.mentsync.R;

import org.json.JSONObject;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView searchUserRecyclerView=searchFragment.findViewById(R.id.searchuseritem);
        searchUserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        SearchView searchview=searchFragment.findViewById(R.id.searchuser);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
                StringRequest  stringRequest =new StringRequest(Request.Method.POST, "https://" + IPAddress.ipaddress + "/searchuser.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject user=

                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("searchuser",newText)
                        return paramV;
                    }
                };
                requestQueue.add(stringRequest);
                return true;
            }
        });
    }


}