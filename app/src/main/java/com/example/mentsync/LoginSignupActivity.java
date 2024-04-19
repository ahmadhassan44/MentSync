package com.example.mentsync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        String email=((EditText)findViewById(R.id.emailedit)).getText().toString();
        String password=((EditText)findViewById(R.id.passwordedit)).getText().toString();
        findViewById(R.id.loginbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(email.isEmpty() || password.isEmpty()))
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                else if(password.isEmpty())
                    ((EditText)findViewById(R.id.passwordedit)).setError("Can't be empty");
                else if(email.isEmpty())
                    ((EditText)findViewById(R.id.passwordedit)).setError("Can't be empty");
                else if (!validate(email,password));
                    Log.d("Ahmad","Invalid credentials");
            }
        });
        findViewById(R.id.newaccbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RoleActivity.class));
            }
        });
    }
        boolean validate(String email,String password)
        {
            return true;
        }




}