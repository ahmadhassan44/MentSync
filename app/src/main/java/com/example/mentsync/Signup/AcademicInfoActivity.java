package com.example.mentsync.Signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mentsync.Login.LoginSignupActivity;
import com.example.mentsync.R;

public class AcademicInfoActivity extends AppCompatActivity {

    NewUserData u= NewUserData.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_info);

        ArrayAdapter<String> adapter1=new ArrayAdapter<>(getApplicationContext(),R.layout.list_item,new String[]{"BSCS","BSDS","BESE","BEEE"});
        AutoCompleteTextView selectdisp=findViewById(R.id.selectdisp);
        selectdisp.setAdapter(adapter1);
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String discipline = ((AutoCompleteTextView) findViewById(R.id.selectdisp)).getText().toString();
                String semesterText = ((EditText) findViewById(R.id.semesteredit)).getText().toString();
                String cgpaText = ((EditText) findViewById(R.id.gpaedit)).getText().toString();
                if (discipline.isEmpty()) {
                    ((AutoCompleteTextView) findViewById(R.id.selectdisp)).setError("Can't be empty");
                    return;
                }
                if (semesterText.isEmpty()) {
                    ((EditText) findViewById(R.id.semesteredit)).setError("Can't be empty");
                    return;
                }
                if (cgpaText.isEmpty()) {
                    ((EditText) findViewById(R.id.gpaedit)).setError("Can't be empty");
                    return;
                }
                try {
                    int semester = Integer.parseInt(semesterText);
                    double cgpa = Double.parseDouble(cgpaText);
                    if (semester < 1 || semester > 8) {
                        ((EditText) findViewById(R.id.semesteredit)).setError("Invalid Semester");
                        return;
                    }
                    if (cgpa < 0.0 || cgpa > 4.0) {
                        ((EditText) findViewById(R.id.gpaedit)).setError("Invalid CGPA");
                        return;
                    }
                    NewUserData u = NewUserData.getInstance();
                    u.setDiscipline(discipline);
                    u.setSemester(semester);
                    u.setCGPA(cgpa);
                    startActivity(new Intent(getApplicationContext(), PasswordActivity.class));
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Please enter valid numbers", Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.cancelbtn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginSignupActivity.class));
            }
        });
    }
}