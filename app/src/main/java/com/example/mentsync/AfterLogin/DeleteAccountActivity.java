package com.example.mentsync.AfterLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.StrongBoxUnavailableException;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mentsync.Login.LoginSignupActivity;
import com.example.mentsync.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DeleteAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        findViewById(R.id.deleteaccbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=((EditText)findViewById(R.id.pass1)).getText().toString();
                if(password.isEmpty())
                    ((EditText)findViewById(R.id.pass1)).setError("Enter password");
                else
                {
                    findViewById(R.id.deleteprogbar).setVisibility(View.VISIBLE);
                    FirebaseAuth auth=FirebaseAuth.getInstance();
                    FirebaseUser currentUser= auth.getCurrentUser();
                    String currentUserId=currentUser.getUid();
                    AuthCredential credential= EmailAuthProvider.getCredential(currentUser.getEmail(), password);
                    currentUser.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            currentUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    DatabaseReference userrefindb=FirebaseDatabase.getInstance().getReference("Users").child(currentUserId);
                                    userrefindb.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            StorageReference pic= FirebaseStorage.getInstance().getReference("IMG_"+currentUserId+".jpg");
                                            pic.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    findViewById(R.id.deleteprogbar).setVisibility(View.INVISIBLE);
                                                    Toast.makeText(DeleteAccountActivity.this, "Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                    new UserSessionManager(getApplicationContext()).endSession();
                                                    startActivity(new Intent(getApplicationContext(),LoginSignupActivity.class));
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(DeleteAccountActivity.this, "pic"+e.toString(), Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(DeleteAccountActivity.this,"data"+ e.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DeleteAccountActivity.this,"authen"+ e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DeleteAccountActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}