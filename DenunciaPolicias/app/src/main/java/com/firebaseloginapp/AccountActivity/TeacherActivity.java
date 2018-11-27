package com.firebaseloginapp.AccountActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebaseloginapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends Activity {
    private FirebaseAuth auth;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("alumnos");
    DatabaseReference mensajeRef = ref.child("denuncias");
    List<String> denuncias= new ArrayList<>();
    List<String> pruebas= new ArrayList<>();
    ;
    List<String> listalumnos= new ArrayList<>();
    List<PojoAlumnos> pojoList= new ArrayList<>();
    private ListView lista ;
    ArrayAdapter<String> itemsAdapter;
    DatabaseReference alumnosRef = FirebaseDatabase.getInstance().getReference("alumnos");
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(TeacherActivity.this, LoginActivity.class));
                    finish();
                }else{
                    System.out.println(user.getEmail());

                }
            }
        };
        final String usermail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        ref.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    System.out.println("Maestro:"+" "+post.getMaestro());
                    System.out.println("Usuario:"+" "+usermail);

                    if(post.getMaestro().trim().matches(usermail) && post.getStatus().matches("0")) {
                        System.out.println("si entra");

                        listalumnos.add(post.getNcontrol());
                        pojoList.add(post);
                        System.out.println(post.getNombre());
                        System.out.println(post.getNcontrol());
                    }

                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    System.out.println("Maestro:"+" "+post.getMaestro());
                    System.out.println("Usuario:"+" "+usermail);
                    if(post.getMaestro().trim().matches(usermail) && post.getStatus().matches("0")) {
                        System.out.println("si entra");

                        listalumnos.add(post.getNcontrol());
                        pojoList.add(post);
                        System.out.println(post.getNombre());
                        System.out.println(post.getNcontrol());
                    }

                }else{
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    System.out.println("Maestro:"+" "+post.getMaestro());
                    System.out.println("Usuario:"+" "+usermail);
                    if(post.getMaestro().trim().matches(usermail) && post.getStatus().matches("0")) {
                        System.out.println("si entra");

                        listalumnos.add(post.getNcontrol());
                        pojoList.add(post);
                        System.out.println(post.getNombre());
                        System.out.println(post.getNcontrol());
                    }
                }else{
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    System.out.println("Maestro:"+" "+post.getMaestro());
                    System.out.println("Usuario:"+" "+usermail);
                    if(post.getMaestro().trim().matches(usermail) && post.getStatus().matches("0")) {
                        System.out.println("si entra");

                        listalumnos.add(post.getNcontrol());
                        pojoList.add(post);
                        System.out.println(post.getNombre());
                        System.out.println(post.getNcontrol());
                    }
                }else{
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });
        itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listalumnos);

        auth = FirebaseAuth.getInstance();

    }
    public void ver_alumnos(View v){
        Intent myIntent = new Intent(TeacherActivity.this, StudentsListActivity.class);
        Bundle bundle= new Bundle();
        bundle.putStringArrayList("datos", (ArrayList<String>) listalumnos);
        myIntent.putExtras(bundle);
        startActivityForResult(myIntent, 2);
        //startActivity(new Intent(TeacherActivity.this, StudentsListActivity.class));
    }
    public void ver_documentos(View v){
        startActivity(new Intent(TeacherActivity.this, LoginActivity.class));
    }
    public void add_alumno(View v){
        startActivity(new Intent(TeacherActivity.this, AddAlumnoActivity.class));
    }
    public void ver_liberados(View v){
        startActivity(new Intent(TeacherActivity.this, ReadyAlumnosActivity.class));
    }
    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {




    }

    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(TeacherActivity.this, LoginActivity.class));
                finish();
            } else {
                setDataToView(user);

            }
        }


    };
}
