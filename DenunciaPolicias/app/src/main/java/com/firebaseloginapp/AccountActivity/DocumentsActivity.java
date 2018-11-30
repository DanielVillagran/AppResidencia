package com.firebaseloginapp.AccountActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebaseloginapp.R;
import com.google.firebase.storage.FirebaseStorage;

public class DocumentsActivity extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
    }
}
