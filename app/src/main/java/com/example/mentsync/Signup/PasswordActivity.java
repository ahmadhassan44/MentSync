package com.example.mentsync.Signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mentsync.AfterLogin.HomeActivity;
import com.example.mentsync.IPAddress;
import com.example.mentsync.Login.LoginSignupActivity;
import com.example.mentsync.R;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PasswordActivity extends AppCompatActivity {
    NewUserData u= NewUserData.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        try {
            // Set the hostname verifier to accept any hostname
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

            // Create a new SSL context that accepts all certificates
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    }
            }, new SecureRandom());

            // Set the default SSL socket factory to the custom SSL context
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
        }
        findViewById(R.id.finishbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String pass1=((EditText)findViewById(R.id.pass1)).getText().toString();
            String pass2=((EditText)findViewById(R.id.pass2)).getText().toString();
                if(!pass1.isEmpty() && !pass2.isEmpty() && pass1.equals(pass2))
                {
                    findViewById(R.id.signupprog).setVisibility(View.VISIBLE);
                    u.setPassword(pass1);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url ="https://"+ IPAddress.ipaddress+"/signup.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("Account created Successfully"))
                                    {
                                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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
                if(pass1.isEmpty())
                    ((EditText)findViewById(R.id.pass1)).setError("Can't be empty");
                if(pass2.isEmpty())
                    ((EditText)findViewById(R.id.pass2)).setError("Can't be empty");
                if(!pass1.equals(pass2))
                    Toast.makeText(getApplicationContext(),"Passwords must match",Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.cancelbtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
            }
        });
    }
}