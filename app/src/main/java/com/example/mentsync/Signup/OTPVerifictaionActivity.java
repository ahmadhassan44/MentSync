package com.example.mentsync.Signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.example.mentsync.IPAddress;
import com.example.mentsync.Login.LoginSignupActivity;
import com.example.mentsync.R;

import java.util.HashMap;
import java.util.Map;

public class OTPVerifictaionActivity extends AppCompatActivity {
    NewUserData u= NewUserData.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverifictaion);
        TextView email=findViewById(R.id.textView9);
        email.setText(u.getEmail());
        PinView pinView=findViewById(R.id.pinView);
        findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.signupprog).setVisibility(View.VISIBLE);
                String userenteredotp=pinView.getText().toString();
                RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                String url1="https://"+ IPAddress.ipaddress+"verification/.php";
                StringRequest verificationRequest= new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Account Created Successfully! You May Log in!"))
                        {
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            String url ="https://"+ IPAddress.ipaddress+"/signup.php";

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.equals("Account created Successfully! You may Log in"))
                                            {
                                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
                                            }
                                            else
                                            {
                                                findViewById(R.id.signupprog).setVisibility(View.INVISIBLE);
                                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    findViewById(R.id.signupprog).setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                                }
                            }){
                                protected Map<String, String> getParams(){
                                    Map<String, String> paramV = new HashMap<>();
                                    paramV.put("email", u.getEmail());
                                    paramV.put("name", u.getName());
                                    paramV.put("cms", u.getCMS());
                                    paramV.put("password", u.getPassword());
                                    paramV.put("discipline", u.getDiscipline());
                                    paramV.put("role", u.getRole());
                                    paramV.put("semester",Integer.toString(u.getSemester()));
                                    paramV.put("cgpa", Double.toString(u.getCGPA()));
                                    paramV.put("image", u.getImage());
                                    return paramV;
                                }
                            };
                            queue.add(stringRequest);
                            return;

                        }
                        else
                        {
                            findViewById(R.id.signupprog).setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> map=new HashMap<>();
                        map.put("userenteredotp",userenteredotp);
                        return map;
                    }
                };
                queue.add(verificationRequest);
            }
        });
    }
}