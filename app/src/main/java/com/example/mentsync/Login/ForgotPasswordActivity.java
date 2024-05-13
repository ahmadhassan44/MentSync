package com.example.mentsync.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mentsync.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        findViewById(R.id.sendotpbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=((EditText)findViewById(R.id.email)).getText().toString();
                if (!email.isEmpty())
                {
                    FirebaseAuth auth=FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete())
                                    {
                                        Toast.makeText(getApplicationContext(), "Check your Email for Resetting Password", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Couldn't reset Password "+e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
}