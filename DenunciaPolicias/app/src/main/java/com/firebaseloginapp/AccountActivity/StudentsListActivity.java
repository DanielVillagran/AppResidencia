package com.firebaseloginapp.AccountActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebaseloginapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StudentsListActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth auth;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("alumnos");
    DatabaseReference mensajeRef = ref.child("alumnos");
    List<String> listalumnos= new ArrayList<>();
    List<PojoAlumnos> pojoList= new ArrayList<>();
    private ListView lista ;
    ArrayAdapter<String> itemsAdapter;
    DatabaseReference alumnosRef = FirebaseDatabase.getInstance().getReference("alumnos");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

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
                        String idAlumno = p.getId();
                        String ncontrol = p.getNcontrol();
                        confirmDialog(idAlumno,ncontrol);

//                        Intent myIntent = new Intent(view.getContext(), DenunciasActivity.class);
//                        Bundle bundle= new Bundle();
//                        bundle.putString("Nombre",p.getNombre());
//
//
//                        myIntent.putExtras(bundle);
//                        startActivityForResult(myIntent, 2);


                    }


                });




    }

    private void confirmDialog(final String idalumno, String ncontrol) {
        AlertDialog.Builder builder = new AlertDialog.Builder(StudentsListActivity.this);

        builder
                .setMessage("Esta seguro de liberar al alumno "+ncontrol)
                .setPositiveButton("Si",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        alumnosRef.child(idalumno).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                dataSnapshot.getRef().child("status").setValue("1");
                                Toast.makeText(StudentsListActivity.this,"Alumno liberado",Toast.LENGTH_SHORT).show();


                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("User", databaseError.getMessage());
                                Toast.makeText(StudentsListActivity.this,"Ha ocurrido un error al liberar al usuario",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }




}
