package com.medipredict.ai.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.medipredict.ai.R;
import com.medipredict.ai.utils.PrefManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = new PrefManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText("Hello, " + pref.getName());

        View header = navView.getHeaderView(0);
        TextView tvNavName = header.findViewById(R.id.tvNavName);
        TextView tvNavEmail = header.findViewById(R.id.tvNavEmail);
        tvNavName.setText(pref.getName());
        tvNavEmail.setText(pref.getEmail());

        findViewById(R.id.cardSymptoms).setOnClickListener(v ->
                startActivity(new Intent(this, SymptomsActivity.class)));
        findViewById(R.id.cardVitals).setOnClickListener(v ->
                startActivity(new Intent(this, VitalsActivity.class)));
        findViewById(R.id.cardHistory).setOnClickListener(v ->
                startActivity(new Intent(this, HistoryActivity.class)));
        findViewById(R.id.cardDoctors).setOnClickListener(v ->
                startActivity(new Intent(this, DoctorsActivity.class)));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_symptoms) {
            startActivity(new Intent(this, SymptomsActivity.class));
        } else if (id == R.id.nav_vitals) {
            startActivity(new Intent(this, VitalsActivity.class));
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, HistoryActivity.class));
        } else if (id == R.id.nav_doctors) {
            startActivity(new Intent(this, DoctorsActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            pref.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
