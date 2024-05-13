    package com.example.mentsync.Signup;

    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.example.mentsync.Login.LoginSignupActivity;
    import com.example.mentsync.R;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;

    import java.util.HashMap;
    import java.util.Map;


    public class PasswordActivity extends AppCompatActivity {
        NewUserData u= NewUserData.getInstance();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_password);
            findViewById(R.id.finishbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pass1=((EditText)findViewById(R.id.pass1)).getText().toString();
                    String pass2=((EditText)findViewById(R.id.pass2)).getText().toString();
                    if(!pass1.isEmpty() && !pass2.isEmpty() && pass1.length()>=6 && pass2.length()>=6)
                    {
                        if(!pass1.equals(pass2))
                            Toast.makeText(getApplicationContext(),"Passwords Must match!",Toast.LENGTH_LONG).show();
                        else
                        {
                            u.setPassword(pass1);
                            findViewById(R.id.signupprog).setVisibility(View.VISIBLE);
                            findViewById(R.id.finishbtn).setVisibility(View.INVISIBLE);
                            registerUser(u);
                        }
                    }
                    else if(pass1.isEmpty())
                        ((EditText)findViewById(R.id.pass1)).setError("Can't be empty");
                    else if(pass2.isEmpty())
                        ((EditText)findViewById(R.id.pass2)).setError("Can't be empty");
                    else if(pass1.isEmpty() && pass2.isEmpty())
                    {
                        ((EditText)findViewById(R.id.pass1)).setError("Can't be empty");
                        ((EditText)findViewById(R.id.pass2)).setError("Can't be empty");
                    }
                    else if(pass1.length()<6)
                    {
                        ((EditText)findViewById(R.id.pass1)).setError("Password must be atleast 6 characters long!");
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

        private void registerUser(NewUserData u) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            auth.createUserWithEmailAndPassword(u.getEmail(), u.getPassword())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser currentuser=auth.getCurrentUser();
                            if(currentuser!=null) {
                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference profilePicRef = storage.getReference("IMG_" + currentuser.getUid() + ".jpg");
                                if(u.getImage()!=null)
                                {
                                    UploadTask uploadTask=profilePicRef.putFile(u.getImage());
                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    addrealtimedata(currentuser,uri);
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            findViewById(R.id.signupprog).setVisibility(View.INVISIBLE);
                                            findViewById(R.id.finishbtn).setVisibility(View.VISIBLE);
                                            Toast.makeText(PasswordActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else
                                {
                                    UploadTask uploadTask=profilePicRef.putFile(getDefaultProfilePicUri());
                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    addrealtimedata(currentuser,uri);
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            findViewById(R.id.signupprog).setVisibility(View.INVISIBLE);
                                            findViewById(R.id.finishbtn).setVisibility(View.VISIBLE);
                                            Toast.makeText(PasswordActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            findViewById(R.id.signupprog).setVisibility(View.INVISIBLE);
                            findViewById(R.id.finishbtn).setVisibility(View.VISIBLE);
                            Toast.makeText(PasswordActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        private Uri getDefaultProfilePicUri() {
            return new Uri.Builder()
                    .scheme("android.resource")
                    .authority(getPackageName())
                    .appendPath("drawable")
                    .appendPath("placeholder")
                    .build();
        }
        private void addrealtimedata(FirebaseUser currentuser,Uri uri)
        {
            FirebaseDatabase db=FirebaseDatabase.getInstance();
            DatabaseReference userRef = db.getReference("Users").child(currentuser.getUid());
            Map<String, Object> updates = new HashMap<>();
            updates.put("email", u.getEmail());
            updates.put("cms", u.getCMS());
            updates.put("role", u.getRole());
            updates.put("semester", u.getSemester());
            updates.put("cgpa", u.getCGPA());
            updates.put("profile_pic", uri.toString());
            updates.put("discipline", u.getDiscipline());
            updates.put("name", u.getName());
            userRef.updateChildren(updates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            findViewById(R.id.signupprog).setVisibility(View.INVISIBLE);
                            Toast.makeText(PasswordActivity.this, "Account Created Successfully! You may Login!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),LoginSignupActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            findViewById(R.id.signupprog).setVisibility(View.INVISIBLE);
                            findViewById(R.id.finishbtn).setVisibility(View.VISIBLE);
                            Toast.makeText(PasswordActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
        }


    }