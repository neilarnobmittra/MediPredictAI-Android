package com.medipredict.ai.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.medipredict.ai.R;
import com.medipredict.ai.utils.PrefManager;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        PrefManager pref = new PrefManager(this);
        ((TextView) findViewById(R.id.tvName)).setText(pref.getName());
        ((TextView) findViewById(R.id.tvEmail)).setText(pref.getEmail());
    }
}
