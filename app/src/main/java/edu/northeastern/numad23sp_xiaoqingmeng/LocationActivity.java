package edu.northeastern.numad23sp_xiaoqingmeng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private double totalDistance = 0.0;
    private TextView latitude;
    private TextView longitude;
    private TextView distance;
    private Location lastLocation;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latitude = findViewById(R.id.latitude_text_view);
        longitude = findViewById(R.id.longitude_text_view);
        distance = findViewById(R.id.distance_text_view);
        Button resetButton = findViewById(R.id.reset_button);

        resetButton.setOnClickListener(new View.OnClickListener() {
            // Sets a click listener on the resetButton that
            // resets the totalDistance field and updates the distance text view accordingly.
            @Override
            public void onClick(View view) {
                totalDistance = 0.0;
                distance.setText("0");
            }
        });

        // Initializes the locationManager field and requests permission to access the GPS sensor if necessary
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //If permission is granted, registers the LocationActivity as a listener for GPS updates
        // and updates the latitude and longitude text views with the user's last known location.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                latitude.setText(String.format("%.6f", lastLocation.getLatitude()));
                longitude.setText(String.format("%.6f", lastLocation.getLongitude()));
            }
        }

        // If the activity is being recreated, restores the totalDistance field from the saved state.
        if (savedInstanceState != null) {
            totalDistance = savedInstanceState.getDouble("totalDistance");
            distance.setText(String.format("%.3f meters", totalDistance));
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onLocationChanged(Location location) {
        // Called when the GPS sensor detects a change in the user's location.
        latitude.setText(String.format("%.6f", location.getLatitude()));
        longitude.setText(String.format("%.6f", location.getLongitude()));
        if (lastLocation != null) {
            double distanceInMeters = location.distanceTo(lastLocation);
            totalDistance += distanceInMeters;
            distance.setText(String.format("%.3f meters", totalDistance));
        }
        lastLocation = location;
    }

    @Override
    protected void onPause() {
        // Called when the activity is no longer visible to the user.
        super.onPause();
        locationManager.removeUpdates(this); // Unregisters the LocationActivity as a listener for GPS updates.
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("totalDistance", totalDistance);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onResume() {
        // Called when the LocationActivity is visible to the user again.
        super.onResume();
        Bundle savedState = getIntent().getExtras();
        if (savedState != null) {
            totalDistance = savedState.getDouble("totalDistance");
            distance.setText(String.valueOf(totalDistance));
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                latitude.setText(String.format("%.6f", lastLocation.getLatitude()));
                longitude.setText(String.format("%.6f", lastLocation.getLongitude()));
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Called when the user presses the back button.
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit? The distance will be lost" )
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Called when the user has accepted (or denied) the permission request.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

                    Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (lastLocation != null) {
                        latitude.setText(String.format("%.6f", lastLocation.getLatitude()));
                        longitude.setText(String.format("%.6f", lastLocation.getLongitude()));
                    }
                }
            }
        }
    }
}