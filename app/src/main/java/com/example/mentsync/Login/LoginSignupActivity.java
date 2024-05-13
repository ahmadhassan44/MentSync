package com.example.mentsync.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mentsync.AfterLogin.HomeActivity;
import com.example.mentsync.AfterLogin.UserSessionManager;
import com.example.mentsync.R;
import com.example.mentsync.Signup.NewUserData;
import com.example.mentsync.Signup.RoleActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        String email=((EditText)findViewById(R.id.loginemail)).getText().toString();
        String password=((EditText)findViewById(R.id.loginpassword)).getText().toString();
        findViewById(R.id.newaccbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewUserData u= NewUserData.getInstance();
                startActivity(new Intent(getApplicationContext(), RoleActivity.class));
            }
        });
        findViewById(R.id.loginbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.loginprogress).setVisibility(View.VISIBLE);
                String useremail=((EditText)findViewById(R.id.loginemail)).getText().toString();
                String pass=((EditText)findViewById(R.id.loginpassword)).getText().toString();
                if(!(useremail.isEmpty() || pass.isEmpty()) && !(useremail.length()==6))
                {
                    findViewById(R.id.loginprogress).setVisibility(View.VISIBLE);
                    FirebaseAuth auth=FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(useremail,pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    DatabaseReference user =FirebaseDatabase.getInstance().getReference("Users").child(authResult.getUser().getUid());
                                    user.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists())
                                            {
                                                new UserSessionManager(getApplicationContext(),snapshot).startSession();
                                                findViewById(R.id.loginprogress).setVisibility(View.INVISIBLE);
                                                Toast.makeText(LoginSignupActivity.this, "Signed In!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    findViewById(R.id.loginprogress).setVisibility(View.INVISIBLE);
                                    Toast.makeText(LoginSignupActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else if(useremail.length()==6)
                {
                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                    DatabaseReference reference=database.getReference("Users");
                    reference.orderByChild("cms").equalTo(useremail).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                DataSnapshot userSnapshot = snapshot.getChildren().iterator().next();
                                String email = userSnapshot.child("email").getValue().toString();
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                auth.signInWithEmailAndPassword(email, pass)
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                DatabaseReference userref=FirebaseDatabase.getInstance().getReference("Users").child(authResult.getUser().getUid());
                                                userref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.exists())
                                                        {
                                                            new UserSessionManager(getApplicationContext(),snapshot).startSession();
                                                            findViewById(R.id.loginprogress).setVisibility(View.INVISIBLE);
                                                            Toast.makeText(LoginSignupActivity.this, "Signed In!", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                findViewById(R.id.loginprogress).setVisibility(View.INVISIBLE);
                                                Toast.makeText(LoginSignupActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else
                                Toast.makeText(LoginSignupActivity.this, "No user with such CMS", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else if(useremail.isEmpty())
                    ((EditText)findViewById(R.id.loginemail)).setError("Enter Email!");
                else if(pass.isEmpty())
                    ((EditText)findViewById(R.id.loginpassword)).setError("EnterPassword");
            }
        });
        findViewById(R.id.forgotpass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}