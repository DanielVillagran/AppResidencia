package com.firebaseloginapp.AccountActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebaseloginapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddAlumnoActivity extends Activity {
    private EditText inputEmail, inputPassword,nombre,ncontrol;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private Spinner semestre,carrera;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseAuth authAlumnos;
    private  String maestro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alumno);
        auth = FirebaseAuth.getInstance();
        authAlumnos=FirebaseAuth.getInstance();
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        nombre = (EditText) findViewById(R.id.nombre);
        ncontrol = (EditText) findViewById(R.id.ncontrol);

        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        addItemsOnSpinner2();
        addItemsOnSpinner3();

        maestro= auth.getCurrentUser().getEmail();

    }
    public void addItemsOnSpinner2() {

        carrera = (Spinner) findViewById(R.id.carrera);
        List<String> list = new ArrayList<String>();
        list.add("Carrera");
        list.add("Sistemas");
        list.add("Gestion");
        list.add("Industrial");
        list.add("Gastronomia");
        list.add("Mecatronica");
        list.add("Animacion");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carrera.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner3() {

        semestre = (Spinner) findViewById(R.id.semestre);
        List<String> list = new ArrayList<String>();
        list.add("Semestre");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semestre.setAdapter(dataAdapter);
    }
    public void registrar_alumno(View v) {
        final String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();



        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }



        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Contraseña muy corta, debe ser mayor a 6 caracteres!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //create user
        authAlumnos.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(AddAlumnoActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        authAlumnos.signOut();
                        Toast.makeText(AddAlumnoActivity.this, "Alumno Creado!" , Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(AddAlumnoActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("alumnos");

                            String id = ref.push().getKey();

                            PojoAlumnos u= new PojoAlumnos(id, nombre.getText().toString().trim(),ncontrol.getText().toString().trim(),maestro,"0",semestre.getSelectedItem().toString(), "",carrera.getSelectedItem().toString());
                            ref.child(id).setValue(u);
                            startActivity(new Intent(AddAlumnoActivity.this, TeacherActivity.class));
                            finish();
                        }
                    }
                });

    }

        }
