package com.firebaseloginapp.AccountActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddAlumnoActivity extends Activity {
    private EditText inputEmail, inputPassword,nombre,ncontrol;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private Spinner semestre,carrera;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseAuth authAlumnos;
    private  String maestro;
    public static final String REGEX_NUMEROS = "^[0-9]+$";
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("alumnos");
    DatabaseReference mensajeRef = ref.child("alumnos");
    List<String> listalumnos= new ArrayList<>();
    List<PojoAlumnos> pojoList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alumno);
        auth = FirebaseAuth.getInstance();
        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://escuela-2a8e4.firebaseio.com")
                .setApiKey("escuela-2a8e4")
                .setApplicationId("AIzaSyDa9TK22kkC54s213cy8tkyKMHBaib94aU").build();


        try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "escuela");
            authAlumnos = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            authAlumnos = FirebaseAuth.getInstance(FirebaseApp.getInstance("escuela"));
        }
        ref.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    System.out.println("Maestro:"+" "+post.getMaestro());

                    pojoList.add(post);

                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    System.out.println("Maestro:"+" "+post.getMaestro());

                    pojoList.add(post);

                }else{
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    System.out.println("Maestro:"+" "+post.getMaestro());

                    pojoList.add(post);


                }else{
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    System.out.println("Maestro:"+" "+post.getMaestro());

                    pojoList.add(post);

                }else{
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });
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
        String name = nombre.getText().toString().trim();
        String nucontrol = ncontrol.getText().toString().trim();
        Pattern patron = Pattern.compile(REGEX_NUMEROS) ;
        carrera = (Spinner)findViewById(R.id.carrera);
        semestre = (Spinner)findViewById(R.id.semestre);
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nucontrol)) {
            Toast.makeText(getApplicationContext(), "Enter no. control!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(nucontrol.length()<8 || nucontrol.length()>8){
            Toast.makeText(getApplicationContext(),"el numero de control debe contener 8 digitos",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!patron.matcher(nucontrol).matches()){
            Toast.makeText(getApplicationContext(),"numero de control debe contener solo numeros",Toast.LENGTH_SHORT).show();
            return;
        }


        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Contraseña muy corta, debe ser mayor a 6 caracteres!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(carrera.getSelectedItem().toString().equals("Carrera")){
            Toast.makeText(getApplicationContext(),"selecciona una carrera",Toast.LENGTH_SHORT).show();
            return;

        }

        if(semestre.getSelectedItem().toString().equals("Semestre")){
            Toast.makeText(getApplicationContext(),"selecciona una semestre",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(getApplicationContext(),"email invalido",Toast.LENGTH_SHORT).show();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        //create user
        boolean exist=true;
        for (PojoAlumnos p: pojoList){
            if(p.getNcontrol().trim().matches(ncontrol.getText().toString())){
                exist=false;
                break;
            }

        }
        if(exist) {
            authAlumnos.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(AddAlumnoActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            authAlumnos.signOut();
                            Toast.makeText(AddAlumnoActivity.this, "Alumno Creado!", Toast.LENGTH_SHORT).show();
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

                                PojoAlumnos u = new PojoAlumnos(id, nombre.getText().toString().trim(), ncontrol.getText().toString().trim(), maestro, email,"0", semestre.getSelectedItem().toString(), "", carrera.getSelectedItem().toString());
                                ref.child(id).setValue(u);
                                startActivity(new Intent(AddAlumnoActivity.this, TeacherActivity.class));
                                finish();
                            }
                        }
                    });
        }else{
            Toast.makeText(AddAlumnoActivity.this, "EL numero de control de ese alumno ya esta registrado.",
                    Toast.LENGTH_SHORT).show();
        }

    }

        }
