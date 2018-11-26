package com.firebaseloginapp.AccountActivity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.firebaseloginapp.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DenunciaActivity extends FragmentActivity implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMarkerDragListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean mPermissionDenied = false;
    private Location myLocation;
    private LatLng markerLatLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();
        // Add a marker in Sydney and move the camera
        LatLng lugarDenuncia = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        markerLatLng = new LatLng(lugarDenuncia.latitude,lugarDenuncia.longitude);
        mMap.addMarker(new MarkerOptions().position(markerLatLng).title("Lugar de la denuncia").draggable(true));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lugarDenuncia,17));



    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            LocationManager locationManager =
                    (LocationManager)getSystemService(Context.LOCATION_SERVICE);

//Acquire the user's location
            myLocation = locationManager
                    .getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        markerLatLng = marker.getPosition();
        Log.i(">>>>>>>>>>", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Lat " + markerLatLng.latitude + " "
                + "Long " + markerLatLng.longitude);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        markerLatLng = marker.getPosition();
        Log.i(">>>>>>>>>>", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Lat " + markerLatLng.latitude + " "
                + "Long " + markerLatLng.longitude);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
    public void enviarDenuncia(View view) {
        Intent intent = new Intent(DenunciaActivity.this, DenunciasActivity.class);
        intent.putExtra("lat", markerLatLng.latitude+"");
        intent.putExtra("lon", markerLatLng.longitude+"");
        startActivity(intent);
    }
}

