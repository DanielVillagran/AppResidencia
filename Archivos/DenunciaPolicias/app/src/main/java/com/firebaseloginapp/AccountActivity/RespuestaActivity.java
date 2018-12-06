package com.firebaseloginapp.AccountActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebaseloginapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RespuestaActivity extends AppCompatActivity {

    private TextView mensajeTextView;
    private EditText mensajeEditText;
    private Button btnChoose;
    FirebaseStorage storage;
    StorageReference storageReference;
    Bitmap bitmap;
    private Spinner s;
    Date d= new Date();
    SimpleDateFormat df= new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat de= new SimpleDateFormat("hh:mm");
    private String filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    String ida;
    List<Pojo> denuncias;
    public static String denunciasId;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("respuestas");
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference("denuncias");
    DatabaseReference mensajeRef = ref.child("respuestas");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta);
        String[] arraySpinner = new String[] {
                "Delito menor", "Delito Mayor", "Secuestro", "Asalto", "Otro"
        };
        Bundle extras = getIntent().getExtras();
        ida=extras.getString("ida");
        btnChoose = (Button) findViewById(R.id.btnChoose);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);
        mensajeEditText = (EditText) findViewById(R.id.mensajeEditText);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == Activity.RESULT_OK)
        {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            filePath=imageEncoded;




        }
    }
    public void modificar(View view) throws IOException {
        String imagen="null";
        if(!mensajeEditText.getText().equals("")) {
            if(filePath != null)
            {
                imagen= UUID.randomUUID().toString();
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child("images/"+ imagen);
                File f = new File(getCacheDir(), imagen);
                f.createNewFile();


//Convert bitmap to byte array

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                Uri i=Uri.fromFile(f);

                ref.putFile(i)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(RespuestaActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(RespuestaActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });
            }
            if (TextUtils.isEmpty(denunciasId)) {
                String id = ref.push().getKey();
                PojoRespuesta p = new PojoRespuesta(id, mensajeEditText.getText().toString(), df.format(new Date()), de.format(new Date()),imagen);
                ref.child(id).setValue(p);
                Toast.makeText(this, "Denuncia Atendida con Exito!", Toast.LENGTH_LONG).show();
                String taskId = "-K1NRz9l5PU_0CFDtgXz";

                myref.child(ida).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("status").setValue("1");


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("User", databaseError.getMessage());
                    }
                });

                Intent intent = new Intent(RespuestaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "Favor de llenar los campos solicitados!", Toast.LENGTH_LONG).show();
        }

    }
}
