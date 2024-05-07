package com.example.mentsync.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.PixelCopy;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mentsync.AfterLogin.HomeActivity;
import com.example.mentsync.HandshakeErrorTackler;
import com.example.mentsync.IPAddress;
import com.example.mentsync.R;
import com.example.mentsync.Signup.OTPVerifictaionActivity;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        HandshakeErrorTackler.fixerror();
        findViewById(R.id.sendotpbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=((EditText)findViewById(R.id.email)).getText().toString();
                String new_password=((EditText)findViewById(R.id.newpass)).getText().toString();
                if(email.isEmpty())
                    ((EditText)findViewById(R.id.email)).setError("Input the email");
                if (new_password.isEmpty())
                    ((EditText)findViewById(R.id.newpass)).setError("Enter new Password");
                if(!email.isEmpty() && !new_password.isEmpty())
                {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url ="https://"+ IPAddress.ipaddress+"/changepass.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("Password Updated!"))
                                    {
                                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
                                    }
                                    else
                                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("email",email);
                            paramV.put("new_password",new_password);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                    return;
                }

            }
        });
    }
}