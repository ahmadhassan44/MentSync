package com.example.mentsync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RoleActivity extends AppCompatActivity {

    User u=User.getInstance();

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
                startActivity(new Intent(getApplicationContext(),LoginSignupActivity.class));
            }
        });
        findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginSignupActivity.class));
            }
        });
    }

}