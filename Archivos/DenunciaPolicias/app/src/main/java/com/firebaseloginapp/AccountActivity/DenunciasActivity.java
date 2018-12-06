package com.firebaseloginapp.AccountActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DenunciasActivity extends AppCompatActivity {

    private TextView mensajeTextView;
    private EditText mensajeEditText;
    private ImageView imageview;
    private Button btnChoose;
    FirebaseStorage storage;
    StorageReference storageReference;
    Bitmap bitmap;
    private Spinner s;
    Date d = new Date();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat de = new SimpleDateFormat("hh:mm");
    private String filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    String lat;
    String lon;
    String id;
    String imagen;
    String fecha;
    String hora;
    String tipo;
    String desc;
    List<Pojo> denuncias;
    public static String denunciasId;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("denuncias");
    DatabaseReference mensajeRef = ref.child("denuncias");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);

        Bundle extras = getIntent().getExtras();
        lat = extras.getString("lat");
        lon = extras.getString("lon");
        id = extras.getString("id");
        desc = extras.getString("desc");
        fecha = extras.getString("fecha");
        hora = extras.getString("hora");
        tipo = extras.getString("tipo");
        imagen = extras.getString("imagen");
        String[] arraySpinner = new String[]{
                tipo
        };
        btnChoose = (Button) findViewById(R.id.btnChoose);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        s = (Spinner) findViewById(R.id.spinner);
        s.setVisibility(View.INVISIBLE);
        imageview = (ImageView) findViewById(R.id.imageview);
        imageview.setImageBitmap(null);
        imageview.setVisibility(View.INVISIBLE);
        s.setSelection(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);
        mensajeEditText = (EditText) findViewById(R.id.mensajeEditText);
        mensajeEditText.setText(tipo+" - "+desc);
        mensajeEditText.setEnabled(false);
        StorageReference ref = storageReference.child("images/"+ imagen);
        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageview.setImageBitmap(bmp);
                imageview.setVisibility(View.VISIBLE);
                System.out.println("Pruebas");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(DenunciasActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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

        if (resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            filePath = imageEncoded;


        }
    }

    public void modificar(View view) {
        Intent intent = new Intent(DenunciasActivity.this, RespuestaActivity.class);
        intent.putExtra("ida", id+"");
        startActivity(intent);

    }
    public void mapas(View view) {
        Uri gmmIntentUri = Uri.parse("geo:"+lat+","+lon+"?q="+lat+"+"+lon);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}
    /*public void modificar(View view) throws IOException {
        String imagen="null";
        if(!mensajeEditText.getText().equals("")) {
            if(filePath != null)
            {
                imagen=UUID.randomUUID().toString();
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child("images/"+ imagen);
                File f = new File(getCacheDir(), imagen);
                f.createNewFile();



                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos);
                byte[] bitmapdata = bos.toByteArray();
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
                                Toast.makeText(DenunciasActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(DenunciasActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Pojo p = new Pojo(id, mensajeEditText.getText().toString(), s.getSelectedItem().toString(), "0", df.format(new Date()), de.format(new Date()),lat,lon,imagen);
                ref.child(id).setValue(p);
                Toast.makeText(this, "Denuncia Enviada con Exito!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(DenunciasActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "Favor de llenar los campos solicitados!", Toast.LENGTH_LONG).show();
        }

    }
}
*/
