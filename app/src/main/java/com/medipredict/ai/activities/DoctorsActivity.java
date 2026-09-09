package com.medipredict.ai.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.medipredict.ai.R;

public class DoctorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Buttons to open Google Maps with different searches
        findViewById(R.id.btnDoctorsNearMe).setOnClickListener(v ->
                openGoogleMaps("doctors near me"));

        findViewById(R.id.btnHospitalsNearMe).setOnClickListener(v ->
                openGoogleMaps("hospitals near me"));

        findViewById(R.id.btnClinicsNearMe).setOnClickListener(v ->
                openGoogleMaps("clinics near me"));

        findViewById(R.id.btnPharmacyNearMe).setOnClickListener(v ->
                openGoogleMaps("pharmacy near me"));
    }

    private void openGoogleMaps(String query) {
        try {
            // Opens Google Maps app (or browser) with search
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(query));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Fallback: open in browser if Maps app not installed
                Uri webUri = Uri.parse("https://www.google.com/maps/search/" + Uri.encode(query));
                startActivity(new Intent(Intent.ACTION_VIEW, webUri));
            }
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open Google Maps", Toast.LENGTH_SHORT).show();
        }
    }
}
