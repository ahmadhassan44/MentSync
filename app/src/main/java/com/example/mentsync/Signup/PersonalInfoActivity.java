package com.example.mentsync.Signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentsync.Login.LoginSignupActivity;
import com.example.mentsync.R;

public class PersonalInfoActivity extends AppCompatActivity {

    NewUserData u= NewUserData.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        findViewById(R.id.next1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=((EditText) findViewById(R.id.nameedit)).getText().toString().trim();
                String cms=((EditText) findViewById(R.id.cmsedit)).getText().toString().trim();
                String email=((EditText) findViewById(R.id.loginemail)).getText().toString().trim();
                if(!(email.isEmpty() || cms.isEmpty() || name.isEmpty() || cms.length()<6))
                {
                    u.setEmail(email);
                    u.setCMS(cms);
                    u.setName(name);
                    startActivity(new Intent(getApplicationContext(), AcademicInfoActivity.class));
                }
                if(name.isEmpty())
                    ((EditText) findViewById(R.id.nameedit)).setError("Can't be empty");
                if(cms.isEmpty())
                    ((EditText) findViewById(R.id.cmsedit)).setError("Can't be empty");
                if(cms.length()<6)
                    ((EditText) findViewById(R.id.cmsedit)).setError("Invalid CMS");
                if(email.isEmpty())
                    ((EditText) findViewById(R.id.loginemail)).setError("Can't be empty");
            }
        });
        findViewById(R.id.cancelbtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
            }
        });
    }
}