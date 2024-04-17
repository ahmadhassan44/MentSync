package com.example.mentsync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;

public class AcademicInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_info);

        ArrayAdapter<String> adapter1=new ArrayAdapter<>(getApplicationContext(),R.layout.list_item,new String[]{"BSCS","BSDS","BESE","BEEE"});
        AutoCompleteTextView selectdisp=findViewById(R.id.selectdisp);

        selectdisp.setAdapter(adapter1);
        selectdisp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=adapter1.getItem(position).toString();
                Toast.makeText(getApplicationContext(),"Item"+item,Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.cancelbtn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginSignupActivity.class));
            }
        });
    }
}