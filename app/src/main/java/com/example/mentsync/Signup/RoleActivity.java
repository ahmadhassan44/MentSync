package com.example.mentsync.Signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentsync.Login.LoginSignupActivity;
import com.example.mentsync.R;

public class RoleActivity extends AppCompatActivity {

    NewUserData u= NewUserData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        findViewById(R.id.menteebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u.setRole("Mentee");
                startActivity(new Intent(getApplicationContext(), PersonalInfoActivity.class));
            }
        });

        findViewById(R.id.mentorbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u.setRole("Mentor");
                startActivity(new Intent(getApplicationContext(), PersonalInfoActivity.class));
            }
        });
        findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
            }
        });
    }

}