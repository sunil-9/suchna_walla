package com.example.suchnawalla;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.suchnawalla.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    TextView tvSignIn;
    EditText etName, etEmail, etPassword, etConfirmPassword;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Button btnSignUp;
    private static final String TAG = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        tvSignIn = findViewById(R.id.tvSignIn);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn.setOnClickListener(v -> {
            startActivity(new Intent(SignUp.this, Login.class));
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            finish();
        });
        btnSignUp.setOnClickListener(v -> {
            if (etName.getText().toString().isEmpty()) {
                etName.setError("Name is required");
                return;
            }
            if (etEmail.getText().toString().isEmpty()) {
                etEmail.setError("Email is required");
                return;
            }
            if (etPassword.getText().toString().isEmpty()) {
                etPassword.setError("Password is required");
                return;
            }
            if (etConfirmPassword.getText().toString().isEmpty()) {
                etConfirmPassword.setError("Confirm Password is required");
                return;
            }
            if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                etConfirmPassword.setError("Password and Confirm Password should be same");
                return;
            }

            firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    firebaseFirestore.collection("User")
                            .document(firebaseAuth.getCurrentUser().getUid())
                            .set(new UserModel(etName.getText().toString(), etEmail.getText().toString(), firebaseAuth.getCurrentUser().getUid()))
                            .addOnSuccessListener(aVoid -> {
                                Log.d(TAG, "onCreate: user created successfully " + firebaseAuth.getCurrentUser().getUid());
                                startActivity(new Intent(SignUp.this, MainActivity.class));
                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Log.d(TAG, "onCreate: user data addition failed" + e.getMessage());
                            })
                            .addOnCanceledListener(() -> {
                                Log.d(TAG, "onCreate: user data addition cancelled");
                            });

                } else {
                    Log.d(TAG, "onCreate: user creation failed" + task.getException().getMessage());
                    Toast.makeText(this, "operation failed :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


    }
}