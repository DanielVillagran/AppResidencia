package com.firebaseloginapp.AccountActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import harmony.java.awt.Color;

public class AsesoriaActivity extends AppCompatActivity {

    private TextView mensajeTextView;
    private EditText mensajeEditText;
    private Button btnChoose;
    FirebaseStorage storage;
    StorageReference storageReference;
    private final static String NOMBRE_DIRECTORIO = "PDF";
    private static String NOMBRE_DOCUMENTO = "nombredelwey.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";
    Bitmap bitmap;
    private Spinner s;
    static File ficheroTemporal;
    Date d= new Date();
    SimpleDateFormat df= new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat documentodate= new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat de= new SimpleDateFormat("hh:mm");
    private String filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    String ida;
    List<Pojo> denuncias;
    public static String denunciasId;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("alumnos");
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference("alumnos");
    DatabaseReference mensajeRef = ref.child("respuestas");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asesoria);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        s = (Spinner) findViewById(R.id.spinner);
        mensajeEditText = (EditText) findViewById(R.id.mensajeEditText);
        Bundle extras = getIntent().getExtras();
        final String nombre=extras.getString("nombre");
        NOMBRE_DOCUMENTO=nombre.replaceAll(" ","")+documentodate.format(new Date())+".pdf";
        final String ncontrol=extras.getString("ncontrol");
        final String carrera=extras.getString("carrera");
        String asesora=extras.getString("asesor");
        final String email=asesora.split(".HOLA.")[1];
        final String asesor=asesora.split(".HOLA.")[0];
        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Uploading...");
//        progressDialog.show();

        final Button button = findViewById(R.id.btnImprimir);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Document documento = new Document();

                try {
                    File f = crearFichero(NOMBRE_DOCUMENTO);
                    FileOutputStream ficheroPdf = new FileOutputStream(
                            f.getAbsolutePath());
                    PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);
                    HeaderFooter cabecera = new HeaderFooter(new Phrase(
                            "Los shulos, baby"), false);
                    String cad = md5("putos");
                    HeaderFooter pie = new HeaderFooter(new Phrase(
                            "Firma digital: "+cad), false);
                    documento.setFooter(pie);
//                    HeaderFooter pie = new HeaderFooter(new Phrase(
//                            "La firma va aqui"), false);
                    documento.setHeader(cabecera);

                    documento.open();
                    Font font = FontFactory.getFont(FontFactory.HELVETICA, 28,
                            Font.BOLD, Color.black);
                    documento.add(new Paragraph("Datos del estudiante",font));
                    font = FontFactory.getFont(FontFactory.HELVETICA, 22,
                            Font.BOLD, Color.black);
                    documento.add(new Paragraph("Nombre: "+nombre, font));
                    documento.add(new Paragraph("Numero de control: "+ncontrol, font));
                    documento.add(new Paragraph("Carrera: "+carrera, font));
                    //documento.add(new Paragraph("Empresa: ", font));
                    documento.add(new Paragraph("Asesor: "+asesor, font));
                    documento.add(new Paragraph("Asesoria recibida: "+mensajeEditText.getText(), font));
                    documento.add(new Paragraph("Fecha: "+df.format(new Date())+" a las "+de.format(new Date()), font));




                    font = FontFactory.getFont(FontFactory.HELVETICA, 42, Font.BOLD,
                            Color.GRAY);
                    ColumnText.showTextAligned(writer.getDirectContentUnder(),
                            Element.ALIGN_CENTER, new Paragraph(
                                    "Chiludos.com", font), 297.5f, 421,
                            writer.getPageNumber() % 2 == 1 ? 45 : -45);

                } catch (DocumentException e) {

                    Log.e(ETIQUETA_ERROR, e.getMessage());

                } catch (IOException e) {

                    Log.e(ETIQUETA_ERROR, e.getMessage());

                } finally {

                    // Cerramos el documento.
                    documento.close();
                    StorageReference ref = storageReference.child("asesorias/"+ NOMBRE_DOCUMENTO);
                    Uri i=Uri.fromFile(ficheroTemporal);
                    ref.putFile(i)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AsesoriaActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AsesoriaActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    DatabaseReference refe = FirebaseDatabase.getInstance().getReference("documentos");

                    String id = refe.push().getKey();

                    PojoDocumentos u = new PojoDocumentos(ncontrol,id,NOMBRE_DOCUMENTO,email);
                    refe.child(id).setValue(u);
                    startActivity(new Intent(AsesoriaActivity.this, AlumnoHome.class));
                    finish();

                }
            }
        });
    }
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        ficheroTemporal=fichero;
        return fichero;
    }

    public static File getRuta() {
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }

        return ruta;
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
                                Toast.makeText(AsesoriaActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(AsesoriaActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

                Intent intent = new Intent(AsesoriaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "Favor de llenar los campos solicitados!", Toast.LENGTH_LONG).show();
        }

    }
}
