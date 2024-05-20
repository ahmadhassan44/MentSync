    package com.example.mentsync.AfterLogin;

    import android.app.AlertDialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.graphics.LinearGradient;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import com.bumptech.glide.Glide;
    import com.example.mentsync.Login.LoginSignupActivity;
    import com.example.mentsync.R;
    import com.google.android.material.dialog.MaterialAlertDialogBuilder;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

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
                    sem.setText("Semester: "+snapshot.child("semester").getValue(Long.class).toString());
                    gpa.setText("CGPA: "+snapshot.child("cgpa").getValue(String.class));
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
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        ref.child("cgpa").setValue(newCgpa)
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
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
        }

    }