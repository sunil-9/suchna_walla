package com.example.suchnawalla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    TextView tvBackToSignIn;
    Button btnResetPassword;
    EditText etEmail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        tvBackToSignIn = findViewById(R.id.tvBackToSignIn);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        etEmail = findViewById(R.id.etEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        tvBackToSignIn.setOnClickListener(v -> {
            startActivity(new Intent(ForgotPassword.this, Login.class));
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            finish();
        });
        btnResetPassword.setOnClickListener(v -> {
            if (etEmail.getText().toString().isEmpty()) {
                etEmail.setError("Email is required");
                return;
            }
            firebaseAuth.sendPasswordResetEmail(etEmail.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Snackbar.make(v, "Password reset link sent to your email", Snackbar.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPassword.this, Login.class));
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    finish();
                } else {
                    etEmail.setError("Email is not registered");
                }
            });
        });

    }
}