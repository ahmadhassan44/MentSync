package com.example.mentsync.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mentsync.AfterLogin.HomeActivity;
import com.example.mentsync.R;
import com.example.mentsync.Signup.NewUserData;
import com.example.mentsync.Signup.RoleActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

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
                String email=((EditText)findViewById(R.id.loginemail)).getText().toString();
                String pass=((EditText)findViewById(R.id.loginpassword)).getText().toString();
                if(!(email.isEmpty() && pass.isEmpty()))
                {
                    findViewById(R.id.loginprogress).setVisibility(View.VISIBLE);
                    FirebaseAuth auth=FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email,pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    findViewById(R.id.loginprogress).setVisibility(View.INVISIBLE);
                                    Toast.makeText(LoginSignupActivity.this, "Signed In!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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
                else if(email.isEmpty())
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

    void insertCurrentUserDataIntoSharedPreferences(JSONObject user) throws JSONException {
        SharedPreferences pref=getSharedPreferences("user_data",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putInt("u_id",user.optInt("u_id"));
        editor.putString("name",user.optString("name"));
        editor.putString("email",user.getString("email"));
        editor.putString("profile_pic",user.optString("profile_pic"));
        editor.putBoolean("loggedin?",true);
        editor.apply();
    }
}