package com.medipredict.ai.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.medipredict.ai.R;
import com.medipredict.ai.utils.PrefManager;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etPassword;
    private MaterialButton btnRegister;
    private FirebaseAuth mAuth;
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        pref = new PrefManager(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        TextView tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> register());
        tvLogin.setOnClickListener(v -> finish());
    }

    private void register() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        btnRegister.setEnabled(false);
        btnRegister.setText("Creating account...");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Set display name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(profileTask -> {
                                        pref.saveUser(user.getUid(), name, email);
                                        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(this, MainActivity.class));
                                        finish();
                                    });
                        }
                    } else {
                        btnRegister.setEnabled(true);
                        btnRegister.setText("Register");
                        String msg = task.getException() != null
                                ? task.getException().getMessage()
                                : "Registration failed";
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
