package com.firebaseloginapp.AccountActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebaseloginapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlumnoHome extends Activity {
    private TextView maestro;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("maestros");
    DatabaseReference refAlumnos = FirebaseDatabase.getInstance().getReference("alumnos");
    DatabaseReference mensajeRef = ref.child("maestros");
    List<String> listalumnos= new ArrayList<>();
    List<PojoMaestros> pojoList= new ArrayList<>();
    List<PojoAlumnos> pojoAlumnos= new ArrayList<>();
    PojoAlumnos temporal;
    PojoMaestros maestros_temporal;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_home);
        maestro= (TextView) findViewById(R.id.maestro);
        auth = FirebaseAuth.getInstance();
        for (PojoAlumnos p: pojoAlumnos){
            if(auth.getCurrentUser().getEmail().trim().matches(p.getEmail().trim())){
                temporal=p;
                for(PojoMaestros m:pojoList){
                    if(p.getMaestro().trim().matches(m.getEmail().trim())){
                        maestro.setText("Tu asesor es: "+m.getNombre());
                        maestros_temporal=m;
                        break;

                    }
                }
                break;
            }

        }
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
        refAlumnos.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);


                    pojoAlumnos.add(post);

                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    pojoAlumnos.add(post);

                }else{
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    pojoAlumnos.add(post);


                }else{
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    pojoAlumnos.add(post);

                }else{
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });


    }
    public void cerrarsesion(View v){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(AlumnoHome.this, LoginActivity.class));


    }

    public void create_doc(View view) {
        Intent intent = new Intent(AlumnoHome.this, AsesoriaActivity.class);

        auth = FirebaseAuth.getInstance();
        for (PojoAlumnos p: pojoAlumnos){
            if(auth.getCurrentUser().getEmail().trim().matches(p.getEmail().trim())){
                temporal=p;
                for(PojoMaestros m:pojoList){
                    if(p.getMaestro().trim().matches(m.getEmail().trim())){
                        maestro.setText("Tu asesor es: "+m.getNombre());
                        maestros_temporal=m;
                        break;

                    }
                }
                break;
            }

        }
        intent.putExtra("nombre", temporal.getNombre().trim()+"");
        intent.putExtra("ncontrol", temporal.getNcontrol().trim()+"");
        intent.putExtra("carrera", temporal.getCarrera().trim()+"");
        intent.putExtra("asesor", maestros_temporal.getNombre().trim()+"");
        startActivity(intent);
    }
}
