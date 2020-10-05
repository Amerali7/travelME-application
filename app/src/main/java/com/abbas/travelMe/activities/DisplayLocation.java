package com.abbas.travelMe.activities;

//REFERENCES
//Google Developers. (2018). Place Photos  |  Google Places API for Android       |  Google Developers. [online] Available at: https://developers.google.com/places/android-api/photos.
//Anon, (2018). [online] Available at: http://www.bragitoff.com/2017/05/parsing-first-paragraph-wikipedia-articles-using-jsoup-android-development/

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.abbas.travelMe.R;
import com.abbas.travelMe.sql.DatabaseHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Scanner;



public class DisplayLocation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final Scanner scanner = new Scanner(System.in);
    private static final String encoding = "UTF-8";
    private DatabaseHelper db;
    GeoDataClient mGeoDataClient;
    int names;
    String value;
    private TextView textView;
    private TextView address;
    private TextView phone;
    private TextView title;
    private ImageView imageView;
    private RatingBar ratingBar;
    private StringBuilder text = new StringBuilder();
    String para;
    String tplaceid;
    GoogleApiClient client;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_location);
        textView= (TextView) findViewById(R.id.texty);
        imageView=(ImageView) findViewById(R.id.imageView);
        address = (TextView) findViewById(R.id.address);
        phone = (TextView) findViewById(R.id.phone);
        title = (TextView) findViewById(R.id.Title);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        mGeoDataClient = Places.getGeoDataClient(this, null); //connect to google places API
        client = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        client.connect();

        //https://developers.google.com/places/android-api/start

        db = new DatabaseHelper(getApplicationContext()); //open DB class instance
        Bundle pass = getIntent().getExtras();
        if (pass != null) {
            value = pass.getString("place");
        }

        title.setText(value);
         tplaceid= db.ThePlaceID(value);
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        getPhotos();
        getAddress(tplaceid);

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


    private void getPhotos() {

        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(tplaceid);// getting photo for corresponding place ID
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                PlacePhotoMetadata photoMeta = photoMetadataBuffer.get(0);

                CharSequence attribution = photoMeta.getAttributions();
                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMeta);
                photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                        PlacePhotoResponse photo = task.getResult();
                        Bitmap bitmap = photo.getBitmap();
                        Bitmap resizedbitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true);
                        //imgid= new Bitmap[]{resizedbitmap};
                        System.out.println("done");
                        imageView.setImageBitmap(resizedbitmap);
                    }
                });
            }
        });
    }

    private void getAddress(String PlaceId){ // GET ADDRESS FROM THE PLACE ID
        Places.GeoDataApi.getPlaceById(client, PlaceId)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if(places.getStatus().isSuccess()){
                            Place myPlace = places.get(0);
                            Log.i("yep",
                                    "Place found: " + myPlace.getName() + "\n Address: " + myPlace.getAddress());
                            address.setText(myPlace.getAddress());
                            phone.setText(myPlace.getPhoneNumber());
                            ratingBar.setRating(myPlace.getRating());

                        } else {
                            Log.i("nope","Place not found!");
                        }
                        ;

                        places.release();
                    }
                });


    }
//from google places documentation

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {// get information regarding the location selected

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                value = value.replaceAll(" ", "_");
                Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/" + value).get();// access wikipedia for information
                org.jsoup.select.Elements paragraphs=doc.select(".mw-content-ltr p");
                Element firstParagraph = paragraphs.first();
                para=firstParagraph.text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textView.setText(para);

        }
    }


}
