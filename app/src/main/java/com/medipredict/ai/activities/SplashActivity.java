package com.medipredict.ai.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.medipredict.ai.R;
import com.medipredict.ai.utils.PrefManager;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            PrefManager pref = new PrefManager(this);

            Intent intent;
            if (user != null) {
                // Keep PrefManager in sync
                String name = user.getDisplayName() != null ? user.getDisplayName() : pref.getName();
                pref.saveUser(user.getUid(), name, user.getEmail());
                intent = new Intent(this, MainActivity.class);
            } else if (pref.isLoggedIn()) {
                // fallback
                intent = new Intent(this, MainActivity.class);
            } else {
                intent = new Intent(this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 1800);
    }
}
