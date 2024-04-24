package com.example.mentsync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PersonalInfoActivity extends AppCompatActivity {

    User u=User.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        findViewById(R.id.next1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=((EditText) findViewById(R.id.nameedit)).getText().toString();
                String cms=((EditText) findViewById(R.id.cmsedit)).getText().toString();
                String email=((EditText) findViewById(R.id.loginemail)).getText().toString();
                if(!(email.isEmpty() || cms.isEmpty() || name.isEmpty() || cms.length()<6))
                {
                    u.setEmail(email);
                    u.setCMS(cms);
                    u.setName(name);
                    startActivity(new Intent(getApplicationContext(),AcademicInfoActivity.class));
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
                startActivity(new Intent(getApplicationContext(),LoginSignupActivity.class));
            }
        });
    }
}