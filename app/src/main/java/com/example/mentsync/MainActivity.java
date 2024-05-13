package com.example.mentsync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mentsync.AfterLogin.HomeActivity;
import com.example.mentsync.Login.LoginSignupActivity;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        VideoView videoView=findViewById(R.id.videoview);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animation);
        videoView.setVideoURI(videoUri);
        videoView.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref=getSharedPreferences("user_data",MODE_PRIVATE);
                if(!pref.getBoolean("loggedin?",false))
                    startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
                else
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        },4000);
    }
}