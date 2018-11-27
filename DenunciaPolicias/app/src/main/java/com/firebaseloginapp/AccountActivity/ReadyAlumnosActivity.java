package com.firebaseloginapp.AccountActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

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

public class ReadyAlumnosActivity extends Activity {
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("alumnos");
    DatabaseReference mensajeRef = ref.child("alumnos");
    List<String> listalumnos= new ArrayList<>();
    List<PojoAlumnos> pojoList= new ArrayList<>();
    private ListView lista ;
    ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_alumnos);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String usermail = FirebaseAuth.getInstance().getCurrentUser().getEmail();


        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    System.out.println("Maestro:"+" "+post.getMaestro());
                    System.out.println("Usuario:"+" "+usermail);

                    if(post.getMaestro().trim().matches(usermail) && post.getStatus().matches("1")) {
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
                    if(post.getMaestro().trim().matches(usermail) && post.getStatus().matches("1")) {
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
                    if(post.getMaestro().trim().matches(usermail) && post.getStatus().matches("1")) {
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
                    if(post.getMaestro().trim().matches(usermail) && post.getStatus().matches("1")) {
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
//get firebase auth instance
        auth = FirebaseAuth.getInstance();
        lista=(ListView) findViewById(R.id.list_alumnos);
        lista.setAdapter(itemsAdapter);
        lista.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String cities = String.valueOf(parent.getItemAtPosition(position));
                        PojoAlumnos p=pojoList.get(position);
                        cities= p.getId()+".-"+p.getNcontrol()+".-"+p.getCarrera()+".-"+p.getMaestro()+".-"+p.getSemestre()+".-"+p.getStatus()
                                +".-"+p.getNombre();
                        //Toast.makeText(MainActivity.this, cities, Toast.LENGTH_LONG).show();
                        Intent myIntent = new Intent(view.getContext(), ViewAlumnoActivity.class);
                        Bundle bundle= new Bundle();
                        bundle.putString("nombre",p.getNombre());
                        bundle.putString("carrera",p.getCarrera());
                        bundle.putString("maestro",p.getMaestro());
                        bundle.putString("ncontrol",p.getNcontrol());
                        bundle.putString("semestre",p.getSemestre());
                        bundle.putString("status",p.getStatus());
                        bundle.putString("id",p.getId());
                        myIntent.putExtras(bundle);
                        startActivityForResult(myIntent, 2);


                    }


                });




    }
}

