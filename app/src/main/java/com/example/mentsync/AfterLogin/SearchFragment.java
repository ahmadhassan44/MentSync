package com.example.mentsync.AfterLogin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mentsync.IPAddress;
import com.example.mentsync.R;
import com.google.android.material.search.SearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        RecyclerView searchUserRecyclerView=searchFragment.findViewById(R.id.searchuseritem);
        searchUserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        SearchView searchview=searchFragment.findViewById(R.id.searchuser);
        ArrayList<SearchUserItemModel> returnedUsers=new ArrayList<SearchUserItemModel>();
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
                                try {
                                    JSONObject returnedUsersJSONObject=new JSONObject(response);
                                    JSONArray returnedUsersArray=returnedUsersJSONObject.optJSONArray("users");
                                    for(int i=0;i<returnedUsersArray.length();i++)
                                    {
                                        JSONObject singleUser=returnedUsersArray.getJSONObject(i);
                                        returnedUsers.add(new SearchUserItemModel(singleUser.optString("profile_pic"),singleUser.optString("name")));
                                    }
                                    SearchUserAdapter adapter=new SearchUserAdapter(getActivity().getApplicationContext(),returnedUsers);
                                    searchUserRecyclerView.setAdapter(adapter);
                                } catch (JSONException e) {
                                }
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(),"Error searching Users",Toast.LENGTH_LONG).show();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("searchuser",newText);
                        return paramV;
                    }
                };
                requestQueue.add(stringRequest);
                return true;
            }
        });
    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        RecyclerView searchUserRecyclerView=searchFragment.findViewById(R.id.searchuseritem);
//        searchUserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//        SearchView searchview=searchFragment.findViewById(R.id.searchuser);
//
//        ArrayList<SearchUserItemModel> returnedUsers=new ArrayList<SearchUserItemModel>();
//        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
//                StringRequest  stringRequest =new StringRequest(Request.Method.POST, "https://" + IPAddress.ipaddress + "/searchuser.php",
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject returnedUsersJSONObject=new JSONObject(response);
//                                    JSONArray returnedUsersArray=returnedUsersJSONObject.optJSONArray("users");
//                                    for(int i=0;i<returnedUsersArray.length();i++)
//                                    {
//                                        JSONObject singleUser=returnedUsersArray.getJSONObject(i);
//                                        returnedUsers.add(new SearchUserItemModel(singleUser.optString("profile_pic"),singleUser.optString("name")));
//                                    }
//                                    SearchUserAdapter adapter=new SearchUserAdapter(getActivity().getApplicationContext(),returnedUsers);
//                                    searchUserRecyclerView.setAdapter(adapter);
//                                } catch (JSONException e) {
//                                }
//                            }
//
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity().getApplicationContext(),"Erro searching Users",Toast.LENGTH_LONG).show();
//                    }
//                }){
//                    protected Map<String, String> getParams(){
//                        Map<String, String> paramV = new HashMap<>();
//                        paramV.put("searchuser",newText);
//                        return paramV;
//                    }
//                };
//                requestQueue.add(stringRequest);
//                return true;
//            }
//        });
//    }
}