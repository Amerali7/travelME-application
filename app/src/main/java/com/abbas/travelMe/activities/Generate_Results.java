package com.abbas.travelMe.activities;


//References
//Google Developers. (2018). Get Started  |  Google Places API for Android       |  Google Developers. [online] Available at: https://developers.google.com/places/android-api/start.

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.abbas.travelMe.R;
import com.abbas.travelMe.helper.customlistview;
import com.abbas.travelMe.sql.DatabaseHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//store likes of user x and compare to each other user getting a likeness rating
//likeness is no of matches over total number availible
//rank the likeness rating to get the highest
//get all the likes which are equal and display them


public class Generate_Results extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, customlistview.customButtonListener {

    private Button openmap;
    private Button likereload;
    private ImageView imageViews;
    int temp;
    GeoDataClient mGeoDataClient;
    DatabaseHelper dab;
    ListView lst;
    List result;
    Activity context;

    String [] desc={" "," ", " "," "," ", " "," ", " "," ", " "};
    Integer[] imgid={  };


    //Bitmap[] imgid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate__results);
        //initViews();
        GoogleApiClient client;
        mGeoDataClient = Places.getGeoDataClient(this, null); // connect to google api client
        client = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        client.connect();
        //https://developers.google.com/places/android-api/start
        context = this;
        dab = new DatabaseHelper(getApplicationContext());

        Intent intent = getIntent();
        temp = intent.getIntExtra("ID", 0);
        ArrayList<String> preferences = dab.getPreference(temp);
        Map<Integer, Integer> locations = dab.countNo(preferences);
        System.out.println(locations);
        result = new ArrayList<>(locations.keySet());
        final String[] Placename = dab.getlocname(result);
        System.out.println(Placename);
        final ArrayList<String> passplace= new ArrayList<String>();

        for (int k=0; k<Placename.length;k++)
        {
            passplace.add(Placename[k]);
        }


        lst = (ListView) findViewById(R.id.zz);
        customlistview Customlistview = new customlistview(this, Placename, desc, imgid);
        Customlistview.setCustomButtonListner(Generate_Results.this);
        lst.setAdapter(Customlistview);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(Generate_Results.this, DisplayLocation.class);

                intent.putExtra("place", Placename[i]);
                Log.w("hallo", Placename[i]);
                startActivity(intent);

            }
        });


        // Request photos and metadata for the specified place.
        //imageViews = (ImageView) findViewById(R.id.imageViewss);

        openmap = (Button) findViewById(R.id.openmap);
        openmap.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("hello bro");

                Intent intent = new Intent(Generate_Results.this, MapsActivity.class);
                intent.putExtra("place",passplace);
                startActivity(intent);

            }
        });


        likereload = (Button) findViewById(R.id.refresh); //jaccard index after submitting the likes to the system
        likereload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Double> Jaccard = new ArrayList<Double>();
                int matchedValues = 0;
                int k = dab.Likequeryusers();
                ArrayList<String> theuser = dab.gettheuser(temp);
                for (int r = 1; r <= k; r++) {
                    System.out.println(dab.gettheusers(r));
                    ArrayList<String> allusers = dab.gettheusers(r);

                    for (int i = 0; i < theuser.size(); i++) {
                        for (int j = 0; j < allusers.size(); j++) {
                            if (theuser.get(i).equals(allusers.get(j))) {
                                matchedValues++;
                            }
                        }
                    }
                    allusers.removeAll(theuser);
                    allusers.addAll(theuser);
                    int p = allusers.size();
                    double coefficient = ((double) matchedValues / (double) p);
                    System.out.println(coefficient);
                    Jaccard.add(coefficient);

                    matchedValues = 0;
                }
                Double number = 0.0;
                int b = 5;
                int a = temp - 1;
                for (int r = 0; r < Jaccard.size(); r++) {
                    if (r == a) {

                    } else {
                        if (Jaccard.get(r) > number) {
                            number = Jaccard.get(r);
                            b = r + 1;
                        }
                    }

                }
                ArrayList<String> highest = dab.gettheuser(b);
                ArrayList<String> finals = new ArrayList<String>();
                highest.removeAll(theuser);
                for (int q = 0; q < result.size(); q++) {
                    finals.add(String.valueOf(result.get(q)));
                }
                finals.removeAll(highest);
                highest.addAll(finals);
                List<Integer> added= new ArrayList<>();

                for (int q = 0; q < highest.size(); q++) {
                    added.add(Integer.parseInt(highest.get(q)));
                }

                String[] Places = dab.getlocname(added);
                customlistview Customlistview = new customlistview(context, Places, desc, imgid);
                lst.setAdapter(Customlistview);


            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onButtonClickListner(int position, String value) {
        Toast.makeText(Generate_Results.this, "Button click " + position,
                Toast.LENGTH_SHORT).show();
        Object m= result.get(position);
        System.out.println(m);
        System.out.println(temp);
        int loc = Integer.parseInt(String.valueOf(m));
        dab.addLike(temp,loc);


    }
}
