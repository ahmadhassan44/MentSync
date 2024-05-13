package com.example.mentsync.AfterLogin.AddFragmentChilds;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mentsync.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.time.LocalDate;

public class SimplePostFragment extends Fragment {

    View simplepostfrag;
    Bitmap bitmap;
    ImageView postimage;
    EditText caption;
    Uri postimguri;

    Button postbtn;
    Button discardbtn;

    public SimplePostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        simplepostfrag= inflater.inflate(R.layout.fragment_simple_post, container, false);
        return simplepostfrag;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postimage=simplepostfrag.findViewById(R.id.postimage);
        postbtn=simplepostfrag.findViewById(R.id.postbtn);
        discardbtn=simplepostfrag.findViewById(R.id.discard);
        caption=simplepostfrag.findViewById(R.id.queryedit);
        discardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postimage.setImageResource(R.drawable.baseline_photo_library_24);
                caption.setText(null);
                postbtn.setVisibility(View.INVISIBLE);
                discardbtn.setVisibility(View.INVISIBLE);
            }
        });
        postimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(postimage.getDrawable()==null)
                {
                    Dexter.withActivity(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            startActivityForResult(Intent.createChooser((new Intent(Intent.ACTION_PICK)).setType("image/*"),"Browse Image"),123);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
                }
                else
                {
                    postimage.setImageDrawable(null);
                    Dexter.withActivity(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            startActivityForResult(Intent.createChooser((new Intent(Intent.ACTION_PICK)).setType("image/*"),"Browse Image"),123);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();

                }
            }
        });

        postbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postbtn.setVisibility(View.INVISIBLE);
                discardbtn.setVisibility(View.INVISIBLE);
                simplepostfrag.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                String postId = FirebaseDatabase.getInstance().getReference().child("Post").push().getKey();
                FirebaseStorage storage=FirebaseStorage.getInstance();
                StorageReference postref = storage.getReference("IMG_" + postId + ".jpg");
                UploadTask uploadTask=postref.putFile(postimguri);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        postref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                String postId = FirebaseDatabase.getInstance().getReference().child("Post").push().getKey();
                                DatabaseReference postRef = FirebaseDatabase.getInstance().getReference().child("Post").child(postId);
                                postRef.child("caption").setValue(caption.getText().toString());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    postRef.child("date").setValue(LocalDate.now().toString());
                                }
                                postRef.child("likes").setValue(0);
                                postRef.child("uid").setValue(uid);
                                postRef.child("imgurl").setValue(uri.toString());
                                simplepostfrag.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                                postimage.setImageResource(R.drawable.baseline_photo_library_24);
                                caption.setText(null);
                                Toast.makeText(getActivity().getApplicationContext(), "Posted.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Upload Failed", Toast.LENGTH_SHORT).show();
                        simplepostfrag.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==123 && resultCode==RESULT_OK)
        {
            postimguri=data.getData();
            try {
                InputStream inputStream=getActivity().getContentResolver().openInputStream(postimguri);
                bitmap= BitmapFactory.decodeStream(inputStream);
                postimage.setImageBitmap(bitmap);
                discardbtn.setVisibility(View.VISIBLE);
                postbtn.setVisibility(View.VISIBLE);
            }
            catch (Exception e)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}