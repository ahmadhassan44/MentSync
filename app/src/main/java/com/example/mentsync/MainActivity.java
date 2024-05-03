package com.example.mentsync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mentsync.Login.LoginSignupActivity;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        // Google Apps Script URL
        String googleSheetUrl = "https://script.google.com/macros/s/AKfycbzJB4VAydA3AXbCajV3HYG0yZ2UTcJtXtrrwop2v0qdbfZlKJOCA4lVBr5RcOc58x9ADQ/exec";

        // Create a Volley GET request to fetch the IP address
        StringRequest stringRequest = new StringRequest(Request.Method.GET, googleSheetUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the response and extract the IP address
                            JSONObject jsonResponse = new JSONObject(response);
                            IPAddress.ipaddress = jsonResponse.getString("ipAddress");
                            Toast.makeText(getApplicationContext(),IPAddress.ipaddress,Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error parsing IP address.", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(getApplicationContext(), "Error fetching IP address: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        // Add the request to the queue
        queue.add(stringRequest);
        startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));

    }
}