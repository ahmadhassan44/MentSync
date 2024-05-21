    package com.example.mentsync.AfterLogin;

    import static android.app.Activity.RESULT_OK;

    import static com.example.mentsync.AfterLogin.UserSessionManager.pref;

    import android.Manifest;
    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.LinearGradient;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import com.bumptech.glide.Glide;
    import com.example.mentsync.Login.LoginSignupActivity;
    import com.example.mentsync.R;
    import com.example.mentsync.Signup.PasswordActivity;
    import com.example.mentsync.Signup.SetProfilePicActivity;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.material.dialog.MaterialAlertDialogBuilder;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
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

    import de.hdodenhof.circleimageview.CircleImageView;

    public class    ProfileFragment extends Fragment {
        View profileFragment;

        public ProfileFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            profileFragment=inflater.inflate(R.layout.fragment_profile, container, false);
            return profileFragment;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            LoggedInUser currentUser=LoggedInUser.getInstance();
            ImageView profilepic=profileFragment.findViewById(R.id.profilepic3);
            TextView disp=profileFragment.findViewById(R.id.textView22);
            TextView sem=profileFragment.findViewById(R.id.textView23);
            TextView gpa=profileFragment.findViewById(R.id.textView24);
            //set user data into components
            SharedPreferences pref= getContext().getSharedPreferences("user_data",Context.MODE_PRIVATE);
            TextView tf=profileFragment.findViewById(R.id.textView9);
            tf.setText(pref.getString("name","User"));
            Glide.with(getContext()).load(pref.getString("profile_pic","null")).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(profilepic);
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    disp.setText(snapshot.child("discipline").getValue(String.class));
                    sem.setText("Semester : "+(snapshot.child("semester").getValue(Long.class).toString()));
                    gpa.setText("CGPA : "+(snapshot.child("cgpa").getValue(Long.class).toString()));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            profileFragment.findViewById(R.id.logoutbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new UserSessionManager(getContext()).endSession();
                    startActivity(new Intent(getContext(), LoginSignupActivity.class));
                }
            });
            profileFragment.findViewById(R.id.textView10).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(),DeleteAccountActivity.class));
                }
            });
            profileFragment.findViewById(R.id.floatingActionButton3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialogbox, null);
                    TextView dialogTextView = view1.findViewById(R.id.txtview);
                    dialogTextView.setHint("Enter your new CGPA");
                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle("Update Profile")
                            .setView(view1)
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String newCgpa = dialogTextView.getText().toString().trim();
                                    if (!newCgpa.isEmpty()) {
                                        Long gpa = Long.parseLong(dialogTextView.getText().toString().trim());
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        ref.child("cgpa").setValue(gpa)
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        ((TextView)profileFragment.findViewById(R.id.textView24)).setText("CGPA : "+newCgpa);
                                                        Toast.makeText(getContext(), "CGPA updated", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    } else {
                                                        Toast.makeText(getContext(), "Failed to update CGPA", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        dialogTextView.setError("Enter a valid CGPA!");
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            });
            profileFragment.findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialogbox, null);
                    TextView dialogTextView = view1.findViewById(R.id.txtview);
                    dialogTextView.setHint("Enter your current semester");
                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle("Update Profile")
                            .setView(view1)
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String newsem = dialogTextView.getText().toString().trim();
                                    if (!newsem.isEmpty()) {
                                        Long sem= Long.parseLong(newsem);
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        ref.child("semester").setValue(sem)
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getContext(), "Current Semester updated", Toast.LENGTH_SHORT).show();
                                                        ((TextView)profileFragment.findViewById(R.id.textView23)).setText("Semester : "+newsem);
                                                        dialog.dismiss();
                                                    } else {
                                                        Toast.makeText(getContext(), "Failed to update Semester", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        dialogTextView.setError("Enter a valid semester!");
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            });
            profileFragment.findViewById(R.id.editpropic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dexter.withActivity(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                            startActivityForResult(Intent.createChooser(intent, "Browse Image"), 123);
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
            });
        }
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if(requestCode==123 && resultCode==RESULT_OK)
            {
                Uri filepath=data.getData();
                try {

                    InputStream inputStream=getActivity().getContentResolver().openInputStream(filepath);
                    Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    CircleImageView propic=profileFragment.findViewById(R.id.profilepic3);
                    propic.setImageBitmap(bitmap);
                    new MaterialAlertDialogBuilder(getContext())
                            .setTitle("Update Profile Picture")
                            .setMessage("Do you really want to update your profile picture?")
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    StorageReference ref= FirebaseStorage.getInstance().getReference("IMG_" + userid + ".jpg");
                                    ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            snapshot.getRef().child("profile_pic").setValue(uri.toString());
                                                            Toast.makeText(getContext(),"Profile Picture Updated!",Toast.LENGTH_LONG).show();
                                                            SharedPreferences pref= getActivity().getSharedPreferences("user_data",Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor=pref.edit();
                                                            editor.putString("profile_pic",uri.toString());
                                                            editor.apply();
                                                            dialog.dismiss();
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences preferences=getActivity().getSharedPreferences("user_data",Context.MODE_PRIVATE);
                                    Glide.with(getContext()).load(preferences.getString("profile_pic",null)).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(propic);
                                    dialog.dismiss();
                                }
                            })
                            .show();

                }
                catch (Exception e)
                {

                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    }