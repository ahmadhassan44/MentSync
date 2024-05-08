package com.example.mentsync.Signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mentsync.IPAddress;
import com.example.mentsync.Login.LoginSignupActivity;
import com.example.mentsync.R;

import java.util.HashMap;
import java.util.Map;

public class OTPVerifictaionActivity extends AppCompatActivity {
    NewUserData u= NewUserData.getInstance();
    EditText[] pinboxes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverifictaion);
        pinboxes= new EditText[]{findViewById(R.id.pin1), findViewById(R.id.pin2), findViewById(R.id.pin3), findViewById(R.id.pin4), findViewById(R.id.pin5), findViewById(R.id.pin6)};
        TextView email=findViewById(R.id.textView9);
        email.setText(u.getEmail());
        pinboxes[0].requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(pinboxes[0], InputMethodManager.SHOW_IMPLICIT);
        moveFocus();
        String userenteredotp="";
        for(int i=0;i<pinboxes.length;i++)
            userenteredotp+=pinboxes[i].getText();
        String finalUserenteredotp = userenteredotp;
        findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.signupprog).setVisibility(View.VISIBLE);
                RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                String url1="https://"+ IPAddress.ipaddress+"/verification.php";
                StringRequest verificationRequest= new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Correct OTP!"))
                        {
                            findViewById(R.id.signupprog).setVisibility(View.INVISIBLE);
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            String url ="https://"+ IPAddress.ipaddress+"/signup.php";

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.equals("Account created Successfully! You may Log in"))
                                            {
                                                findViewById(R.id.signupprog).setVisibility(View.INVISIBLE);
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
                        findViewById(R.id.signupprog).setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> map=new HashMap<>();
                        map.put("userenteredotp", finalUserenteredotp);
                        return map;
                    }
                };
                queue.add(verificationRequest);
            }
        });
    }
    private void moveFocus() {
        for (int i = 0; i < pinboxes.length; i++) {
            int currentbox=i;
            pinboxes[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(pinboxes[currentbox].getText().length()==1 && currentbox<pinboxes.length-1)
                        pinboxes[currentbox+1].requestFocus();
                    if(pinboxes[currentbox].getText().length()==0 && currentbox>=1)
                        pinboxes[currentbox-1].requestFocus();
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }

    }


}