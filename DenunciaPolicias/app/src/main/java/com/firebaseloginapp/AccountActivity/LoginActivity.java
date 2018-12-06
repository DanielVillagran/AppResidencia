package com.firebaseloginapp.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebaseloginapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("maestros");
    DatabaseReference mensajeRef = ref.child("maestros");
    List<String> listalumnos= new ArrayList<>();
    List<PojoMaestros> pojoList= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        ref.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoMaestros post = dataSnapshot.getValue(PojoMaestros.class);


                        pojoList.add(post);

                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoMaestros post = dataSnapshot.getValue(PojoMaestros.class);
                    pojoList.add(post);

                }else{
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    PojoMaestros post = dataSnapshot.getValue(PojoMaestros.class);
                    pojoList.add(post);


                }else{
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoMaestros post = dataSnapshot.getValue(PojoMaestros.class);
                    pojoList.add(post);

                }else{
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null && !auth.getCurrentUser().getEmail().matches("") && pojoList.size() > 0) {
            boolean maestro=false;
            for (PojoMaestros p : pojoList){
                if(auth.getCurrentUser().getEmail().trim().matches(p.getEmail().trim())){
                    maestro=true;
                    break;
                }

            }
        if(maestro) {
            startActivity(new Intent(LoginActivity.this, TeacherActivity.class));
        }else{
            startActivity(new Intent(LoginActivity.this, AlumnoHome.class));
        }
            finish();
        }

        setContentView(R.layout.activity_login);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    boolean maestro=false;
                                    for (PojoMaestros p : pojoList){
                                        if(auth.getCurrentUser().getEmail().trim().matches(p.getEmail().trim())){
                                            maestro=true;
                                            break;
                                        }

                                    }
                                    if(maestro) {
                                        startActivity(new Intent(LoginActivity.this, TeacherActivity.class));
                                    }else{
                                        startActivity(new Intent(LoginActivity.this, AlumnoHome.class));
                                    }
                                    finish();

                                }
                            }
                        });
            }
        });
    }
}

