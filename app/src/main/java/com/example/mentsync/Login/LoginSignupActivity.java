package com.example.mentsync.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mentsync.AfterLogin.HomeActivity;
import com.example.mentsync.AfterLogin.LoggedInUser;
import com.example.mentsync.AfterLogin.ProfileFragment;
import com.example.mentsync.AfterLogin.UserSessionManager;
import com.example.mentsync.IPAddress;
import com.example.mentsync.R;
import com.example.mentsync.Signup.RoleActivity;
import com.example.mentsync.Signup.NewUserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LoginSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
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
                String emailOrCMS= ((EditText)findViewById(R.id.loginemail)).getText().toString();
                String password= ((EditText)findViewById(R.id.loginpassword)).getText().toString();
                if(emailOrCMS.isEmpty())
                    ((EditText)findViewById(R.id.loginemail)).setError("Provide Email or CMS");
                if(password.isEmpty())
                    ((EditText)findViewById(R.id.loginpassword)).setError("Provide password");

                if(!emailOrCMS.isEmpty() && !password.isEmpty())
                {
                    findViewById(R.id.loginprogress).setVisibility(View.VISIBLE);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url ="https://"+ IPAddress.ipaddress+"/signin.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                       JSONObject jsonresponse=new JSONObject(response);
                                       if (jsonresponse.getBoolean("success"))
                                       {
                                           JSONObject user=jsonresponse.getJSONObject("user_data");
                                           //putting userdata into shared preferences
                                           UserSessionManager newsession=new UserSessionManager(getApplicationContext());
                                           newsession.startSession(getApplicationContext(),user);
                                           startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                       }
                                       else
                                       {
                                           Toast.makeText(getApplicationContext(),jsonresponse.getString("message"), Toast.LENGTH_LONG).show();
                                       }
                                    } catch (JSONException e) {
                                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                    }
                                    findViewById(R.id.loginprogress).setVisibility(View.INVISIBLE);
                                }

                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            findViewById(R.id.loginprogress).setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            if(emailOrCMS.length()==6)
                                paramV.put("cms",emailOrCMS);
                            else
                                paramV.put("email",emailOrCMS);
                            paramV.put("password",password);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                    return;
                }
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