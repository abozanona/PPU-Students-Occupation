package com.example.abdolhameed.ppustudentsoccupation;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Map extends AppCompatActivity_withmenu {

    LocationManager locationManager;
    LocationListener ls;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setClassName("Map");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

            ((TextView) findViewById(R.id.t1)).setText("Welcome " + getSharedPreferences("rondApplication", MODE_PRIVATE).getString("realname", "Anon!!"));

            locationManager = (LocationManager)
            getSystemService(Context.LOCATION_SERVICE);

            ls = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //((TextView) findViewById(R.id.t1)).setText("Provider(): " + location.getProvider());

                //((TextView) findViewById(R.id.t2)).setText("Bearing(): " + location.getBearing());
                //((TextView) findViewById(R.id.t3)).setText("Accuracy(): " + location.getAccuracy() + "--------------------------------------------------------------------------------------------------------------------");
                //((TextView) findViewById(R.id.t4)).setText("Altitude(): " + location.getAltitude());//الارتفاع عن سطح البحر
                //((TextView) findViewById(R.id.t6)).setText("Latitude(): " + location.getLatitude());
                //((TextView) findViewById(R.id.t7)).setText("Longitude(): " + location.getLongitude());

                //((TextView) findViewById(R.id.t8)).setText("Speed(): " + location.getSpeed());
                //((TextView) findViewById(R.id.t9)).setText("Time(): " + location.getTime());

                ((TextView) findViewById(R.id.t5)).setText("Loading compleated!");
                ((Button)findViewById(R.id.showmap)).setText("Start!");
                ((Button)findViewById(R.id.showmap)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent gotorealmap=new Intent(Map.this, GameMap.class);
                        startActivity(gotorealmap);
                    }
                });



            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(Map.this, "Enableb", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(Map.this, "Disabled", Toast.LENGTH_LONG).show();
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ls);
    }

}
