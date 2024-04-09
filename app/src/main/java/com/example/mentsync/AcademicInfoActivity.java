package com.example.mentsync;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AcademicInfoActivity extends AppCompatActivity {

    String[] disciplines={"BSCS","BSDS","BESE","BEEE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_info);
    }
}