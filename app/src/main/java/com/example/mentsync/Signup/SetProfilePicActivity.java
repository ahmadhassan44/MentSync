package com.example.mentsync.Signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mentsync.Login.LoginSignupActivity;
import com.example.mentsync.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SetProfilePicActivity extends AppCompatActivity {
    NewUserData u=NewUserData.getInstance();
    Bitmap bitmap;
    ImageView img;
    String encodedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile_pic);
        img=findViewById(R.id.profilepic3);

        findViewById(R.id.cancelbtn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
            }
        });
        findViewById(R.id.skipbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap == null) {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
                    img.setImageBitmap(bitmap);
                }
                startActivity(new Intent(getApplicationContext(),PasswordActivity.class));
            }
        });
        findViewById(R.id.browsebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(SetProfilePicActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==123 && resultCode==RESULT_OK)
        {
            Uri filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                ((Button)findViewById(R.id.skipbtn)).setText("Next");
                u.setImage(filepath);
                Intent intent=new Intent(getApplicationContext(), PasswordActivity.class);
            }
            catch (Exception e)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}