package com.firebaseloginapp.AccountActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);

                        listalumnos.add(post.getNcontrol());
                        pojoList.add(post);
                        System.out.println(post.getNombre());
                    System.out.println(post.getNcontrol());

                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                        listalumnos.add(post.getNcontrol());
                        pojoList.add(post);
                        System.out.println(post.getNombre());

                }else{
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    if(post.getStatus().contains("0")) {
                        listalumnos.add(post.getNcontrol());
                        pojoList.add(post);
                        System.out.println(post.getNombre());
                    }
                }else{
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoAlumnos post = dataSnapshot.getValue(PojoAlumnos.class);
                    if(post.getStatus().contains("0")) {
                        listalumnos.add(post.getNcontrol());
                        pojoList.add(post);
                        System.out.println(post.getNombre());
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




    }


}
