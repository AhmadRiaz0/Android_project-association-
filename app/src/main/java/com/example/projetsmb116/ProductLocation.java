package com.example.projetsmb116;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;




public class ProductLocation extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;

    Button btnItineraire;

    //Rajout des variable pour emulateur
    double startlatitude = 48.852381;
    double startlongitude = 2.403277;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_location);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googl_map);
        fusedLocationProviderClient = (FusedLocationProviderClient) LocationServices.getFusedLocationProviderClient(this);//Acces au service de localisation

        btnItineraire = (Button) findViewById(R.id.btnItineraire);


        //Demande de la permission de localisation a utilisateur
        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getCurrentLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }


    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation(); //Obtention de le derniere localisation connu de l'utilisateur


        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        if (location != null) {
                            //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude()); //Ma position pour mobile (avec geolocalisation )
                            LatLng latLng = new LatLng(startlatitude, startlongitude); //Ma position emulateur (position ecrite manuelement dans le code)
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Votre position");
                            googleMap.addMarker(markerOptions);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                            //recuperation de adresse + Company depuis Adapter
                            String company = getIntent().getStringExtra("Company");
                            String adresse = getIntent().getStringExtra("Adresse");

                            Log.i("Company" , company);
                            Log.i("Adresse" , adresse);

                            //Convertion de adresse vers Longitude/Latitude
                            Geocoder geocoder = new Geocoder(ProductLocation.this, Locale.getDefault());

                            try {
                                List<Address> addresses = geocoder.getFromLocationName(adresse, 4);
                                if (addresses != null && addresses.size() > 0) {
                                    Address location = addresses.get(0);
                                    double latitude = location.getLatitude();
                                    double longitude = location.getLongitude();
                                    String label = "Destination";
                                    LatLng latLng1 = new LatLng(latitude, longitude);

                                    //changer icon
                                    //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_business);

                                    MarkerOptions markerOptions1 = new MarkerOptions().position(latLng1).title(company);
                                    googleMap.addMarker(markerOptions1);
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1,15));

                                    googleMap.addMarker(markerOptions1);

                                    //Rajouter un bouton itinairaire
                                    btnItineraire.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            //Renvois sur l'app google map
                                            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + " , " + longitude  + "&label=" + label);
                                            //Uri gmmIntentUri = Uri.parse("google.navigation:q=" + startlatitude + "," + startlongitude + "&destination=" + latitude + "," + longitude + "&label=" + label);



                                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                            mapIntent.setPackage("com.google.android.apps.maps");
                                            if(mapIntent.resolveActivity(getPackageManager()) != null){
                                                startActivities(new Intent[]{mapIntent});
                                            }

                                        }
                                    });







                                } else {
                                    // Adresse introuvable
                                    Toast.makeText(ProductLocation.this, "Adresse introuvable!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }


                        } else {
                            Toast.makeText(ProductLocation.this, "Activer votre localisation !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

