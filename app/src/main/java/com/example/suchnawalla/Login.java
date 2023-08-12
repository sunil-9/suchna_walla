package com.example.suchnawalla;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.suchnawalla.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText etEmail, etPassword;
    TextView tvSignUp,tvForgotPassword;
    Button btnLogin;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, SignUp.class));
            finish();
        });
        tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, ForgotPassword.class));
            finish();
        });

        btnLogin.setOnClickListener(v -> {
            if (etEmail.getText().toString().isEmpty()) {
                etEmail.setError("Email is required");
                return;
            }
            if (etPassword.getText().toString().isEmpty()) {
                etPassword.setError("Password is required");
                return;
            }
            firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            Snackbar.make(v, "Login Failed", Snackbar.LENGTH_LONG).show();
                        }
                    });
        });


    }

}