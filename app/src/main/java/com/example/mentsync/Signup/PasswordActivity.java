    package com.example.mentsync.Signup;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.EditText;
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
    import com.example.mentsync.AfterLogin.HomeActivity;
    import com.example.mentsync.HandshakeErrorTackler;
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
            HandshakeErrorTackler.fixerror();
            findViewById(R.id.finishbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.prog).setVisibility(View.VISIBLE);
                    String pass1=((EditText)findViewById(R.id.pass1)).getText().toString();
                    String pass2=((EditText)findViewById(R.id.pass2)).getText().toString();
                    if(!pass1.isEmpty() && !pass2.isEmpty() && pass1.equals(pass2))
                    {
                        findViewById(R.id.finishbtn).setVisibility(View.INVISIBLE);
                        u.setPassword(pass1);
                        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                        String url="https://"+ IPAddress.ipaddress+"/verification.php";
                        StringRequest verificationRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("ahmad",response);
                                findViewById(R.id.prog).setVisibility(View.INVISIBLE);
                                startActivity(new Intent(getApplicationContext(),OTPVerifictaionActivity.class));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                findViewById(R.id.prog).setVisibility(View.INVISIBLE);
                                findViewById(R.id.finishbtn).setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(),"Error sending OTP:"+error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> map=new HashMap<>();
                                map.put("email",u.getEmail());
                                return map;
                            }
                        };
                        queue.add(verificationRequest);
                    }
                    if(pass1.isEmpty())
                    {
                        ((EditText)findViewById(R.id.pass1)).setError("Can't be empty");
                        findViewById(R.id.prog).setVisibility(View.INVISIBLE);
                    }
                    if(pass2.isEmpty())
                    {
                        findViewById(R.id.prog).setVisibility(View.INVISIBLE);
                        ((EditText)findViewById(R.id.pass2)).setError("Can't be empty");
                    }
                    if(!pass1.equals(pass2))
                    {
                        findViewById(R.id.prog).setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Passwords must match",Toast.LENGTH_LONG).show();
                    }
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