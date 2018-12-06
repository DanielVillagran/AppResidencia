package com.firebaseloginapp.AccountActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DenunciasActivity extends AppCompatActivity {

    private TextView mensajeTextView;
    private EditText mensajeEditText;
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
    List<Pojo> denuncias;
    public static String denunciasId;
    private final String TAG = "JSA-FCM";
    private final String SENDER_ID = "xxxxxxxxxx";
    private Random random = new Random();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("denuncias");
    DatabaseReference mensajeRef = ref.child("denuncias");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);
        String[] arraySpinner = new String[]{
                "Delito menor", "Delito Mayor", "Secuestro", "Asalto", "Otro"
        };
        Bundle extras = getIntent().getExtras();
        lat = extras.getString("lat");
        lon = extras.getString("lon");
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
        if (resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            filePath = imageEncoded;


        }
    }

    public void modificar(View view) throws IOException {
        String imagen = "null";
        if (!mensajeEditText.getText().equals("")) {
            if (filePath != null) {
                imagen = UUID.randomUUID().toString();
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child("images/" + imagen);
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
                Uri i = Uri.fromFile(f);

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
                                Toast.makeText(DenunciasActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
            }
            if (TextUtils.isEmpty(denunciasId)) {
                String id = ref.push().getKey();
                Pojo p = new Pojo(id, mensajeEditText.getText().toString(), s.getSelectedItem().toString(), "0", df.format(new Date()), de.format(new Date()), lat, lon, imagen);
                ref.child(id).setValue(p);
                Toast.makeText(this, "Denuncia Enviada con Exito!", Toast.LENGTH_LONG).show();
                FirebaseMessaging fm = FirebaseMessaging.getInstance();

                RemoteMessage message = new RemoteMessage.Builder("150444368158" + "@gcm.googleapis.com")
                        .setMessageId(Integer.toString(random.nextInt(9999)))
                        .addData("Que onda","Hi")
                        .addData("Que onda","Hello")
                        .build();



                fm.send(message);

                System.out.println("i am here");

                Intent intent = new Intent(DenunciasActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, "Favor de llenar los campos solicitados!", Toast.LENGTH_LONG).show();
        }

    }
}
