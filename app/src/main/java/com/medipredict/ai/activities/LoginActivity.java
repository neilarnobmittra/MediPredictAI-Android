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
import com.medipredict.ai.R;
import com.medipredict.ai.utils.PrefManager;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin;
    private FirebaseAuth mAuth;
    private PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        pref = new PrefManager(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        TextView tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(v -> loginWithEmail());
        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void loginWithEmail() {
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        btnLogin.setEnabled(false);
        btnLogin.setText("Logging in...");

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Login");

                    if (task.isSuccessful()) {
                        goToMain(mAuth.getCurrentUser());
                    } else {
                        String msg = task.getException() != null
                                ? task.getException().getMessage()
                                : "Login failed";
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void goToMain(FirebaseUser user) {
        if (user == null) return;
        String name = user.getDisplayName() != null ? user.getDisplayName() : "User";
        String email = user.getEmail() != null ? user.getEmail() : "";
        pref.saveUser(user.getUid(), name, email);
        Toast.makeText(this, "Welcome " + name, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
