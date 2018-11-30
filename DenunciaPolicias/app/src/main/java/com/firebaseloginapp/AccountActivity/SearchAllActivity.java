package com.firebaseloginapp.AccountActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.firebaseloginapp.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAllActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText info;

    private ArtistsAdapter adapter;
    private List<PojoAlumnos> artistList;
    private  Query query6;

    DatabaseReference dbArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all);

        recyclerView = findViewById(R.id.recyclerView);
        info = findViewById(R.id.input);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistList = new ArrayList<>();
        adapter = new ArtistsAdapter(this, artistList);
        recyclerView.setAdapter(adapter);

        //1. SELECT * FROM Artists
        dbArtists = FirebaseDatabase.getInstance().getReference("alumnos");

        //2. SELECT * FROM Artists WHERE id = "-LAJ7xKNj4UdBjaYr8Ju"
        Query query = FirebaseDatabase.getInstance().getReference("alumnos")
                .orderByChild("id")
                .equalTo("-LAJ7xKNj4UdBjaYr8Ju");

        // query.addListenerForSingleValueEvent(valueEventListener);

        //3. SELECT * FROM Artists WHERE country = "India"
        query6 = FirebaseDatabase.getInstance().getReference("alumnos")
                .orderByChild("carrera")
                .startAt("Gastronomia")
                .endAt("Gastronomia\uf8ff");
//        query6 = FirebaseDatabase.getInstance().getReference("alumnos")
//                .orderByChild("carrera")
//                .startAt(carrera.getSelectedItem().toString()).endAt(carrera.getSelectedItem().toString() + "\uf8ff");
        //4. SELECT * FROM Artists LIMIT 2
        Query query4 = FirebaseDatabase.getInstance().getReference("alumnos").limitToFirst(2);


        //5. SELECT * FROM Artists WHERE age < 30
//         query6 = FirebaseDatabase.getInstance().getReference("alumnos")
//                .orderByChild("ncontrol")
//                .endAt(bus.getText().toString());


//        6. SELECT * FROM Artists WHERE name = "A%"
//         query6 = FirebaseDatabase.getInstance().getReference("alumnos")
//                .orderByChild("nombre")
//                .startAt(bus.getText().toString()
//                .endsWith("\"));



        ;
        /*
         * You just need to attach the value event listener to read the values
         * for example
         * query6.addListenerForSingleValueEvent(valueEventListener)
         * */
    }



    public void buscar(View v){
        Toast.makeText(SearchAllActivity.this,"Buscando...",Toast.LENGTH_SHORT).show();
        busqueda(info.getText().toString().trim());

    }
    public void busqueda(String valor){
        artistList.clear();
        query6 = FirebaseDatabase.getInstance().getReference("alumnos")
                .orderByChild("nombre")
                .startAt(valor)
                .endAt(valor+"\uf8ff");
        query6.addListenerForSingleValueEvent(valueEventListener);

    }




    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            artistList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PojoAlumnos artist = snapshot.getValue(PojoAlumnos.class);
                    artistList.add(artist);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}