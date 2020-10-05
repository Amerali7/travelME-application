package com.abbas.travelMe.activities;

//REFERENCES
//Google Developers. (2018). Get Started  |  Google Maps Android API       |  Google Developers. [online] Available at: https://developers.google.com/maps/documentation/android-api/start.

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.abbas.travelMe.R;
import com.abbas.travelMe.sql.DatabaseHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;

    LocationRequest request;
    GoogleApiClient client;
    GoogleApiClient client2;
    DatabaseHelper data;
    private ArrayList results;
    private  ArrayList<String> placeID=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        client = new GoogleApiClient.Builder(this) // connect to google maps client
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        client.connect();


        Intent intent = getIntent();
        data = new DatabaseHelper(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        results = bundle.getStringArrayList("place");
        System.out.println(" map array" + results);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        for (int i=0; i<results.size(); i++){
           placeID.add(data.ThePlaceID(results.get(i).toString()));
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        for (int p=0; p<placeID.size(); p++){

            getPlace(placeID.get(p));
        }



    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        request = new LocationRequest().create();
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if(location == null){
            Toast.makeText(getApplicationContext(),"Location not found", Toast.LENGTH_SHORT).show();
        }
        else
        {




        }

    }

    private void getPlace(String PlaceId){ // adds markers to the map
        final LatLng London = new LatLng(51.5, 0.1);

        Places.GeoDataApi.getPlaceById(client, PlaceId)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if(places.getStatus().isSuccess()){
                            Place myPlace = places.get(0);
                            Log.i("yep",
                                    "Place found: " + myPlace.getName() + "\n Address: " + myPlace.getAddress());
                            LatLng LOC=myPlace.getLatLng();
                            LatLng location = new LatLng(LOC.latitude, LOC.longitude);
                            mMap.addMarker(new MarkerOptions().position(location).title("Location: " + myPlace.getName()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(London, 10));

                        } else {
                            Log.i("nope","Place not found!");
                        }


                        places.release();
                    }
                });


    }


}