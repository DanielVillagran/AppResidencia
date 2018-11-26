package com.firebaseloginapp.AccountActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebaseloginapp.R;

public class TeacherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
    }
    public void ver_alumnos(View v){
        startActivity(new Intent(TeacherActivity.this, StudentsListActivity.class));
    }
    public void ver_documentos(View v){
        startActivity(new Intent(TeacherActivity.this, LoginActivity.class));
    }
    public void add_alumno(View v){
        startActivity(new Intent(TeacherActivity.this, AddAlumnoActivity.class));
    }
}
