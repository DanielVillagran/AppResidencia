package com.firebaseloginapp.AccountActivity;

import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class DocumentsActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("documents");
    DatabaseReference mensajeRef = ref.child("documents");
    List<String> listalumnos= new ArrayList<>();
    List<PojoDocumentos> pojoList= new ArrayList<>();
    private ListView lista ;
    ArrayAdapter<String> itemsAdapter;
    DatabaseReference alumnosRef = FirebaseDatabase.getInstance().getReference("documents");
    FirebaseUser user;
    String usermail;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        user = FirebaseAuth.getInstance().getCurrentUser();
        usermail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        // refreshList();
        ref.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoDocumentos post = dataSnapshot.getValue(PojoDocumentos.class);
                    if(post.getEmail().trim().matches(usermail)) {
                        System.out.println("si entra");

                        listalumnos.add(post.getNcontrol()+" --> "+post.getDocumento().split(".")[0]);
                        pojoList.add(post);
                        System.out.println(post.getNcontrol());
                    }

                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoDocumentos post = dataSnapshot.getValue(PojoDocumentos.class);
                    if(post.getEmail().trim().matches(usermail)) {
                        System.out.println("si entra");

                        listalumnos.add(post.getNcontrol()+" --> "+post.getDocumento().split(".")[0]);
                        pojoList.add(post);
                        System.out.println(post.getNcontrol());
                    }

                }else{
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    PojoDocumentos post = dataSnapshot.getValue(PojoDocumentos.class);
                    if(post.getEmail().trim().matches(usermail)) {
                        System.out.println("si entra");

                        listalumnos.add(post.getNcontrol()+" --> "+post.getDocumento().split(".")[0]);
                        pojoList.add(post);
                        System.out.println(post.getNcontrol());
                    }
                }else{
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    PojoDocumentos post = dataSnapshot.getValue(PojoDocumentos.class);
                    if(post.getEmail().trim().matches(usermail)) {
                        System.out.println("si entra");

                        listalumnos.add(post.getNcontrol()+" --> "+post.getDocumento().split(".")[0]);
                        pojoList.add(post);
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
        lista=(ListView) findViewById(R.id.list_documents);
        lista.setAdapter(itemsAdapter);


//get firebase auth instance
        auth = FirebaseAuth.getInstance();


        lista.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String cities = String.valueOf(parent.getItemAtPosition(position));
                        PojoDocumentos p=pojoList.get(position);
                        String idAlumno = p.getId();
                        String ncontrol = p.getNcontrol();
                        //confirmDialog(idAlumno,ncontrol);

                    }


                });




    }

}
