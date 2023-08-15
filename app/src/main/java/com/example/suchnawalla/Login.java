package com.example.suchnawalla;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.suchnawalla.helper.PasswordHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.suchnawalla.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class Login extends AppCompatActivity {
    EditText etEmail, etPassword;
    TextView tvSignUp, tvForgotPassword;
    Button btnLogin;
    FirebaseAuth firebaseAuth;
    ImageView ivEye;
    boolean isShowPassword = false;
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
        ivEye = findViewById(R.id.ivEye);
        ivEye.setOnClickListener(view -> isShowPassword = PasswordHelper.showPassword(etPassword, ivEye, isShowPassword));

        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, SignUp.class));
            finish();
        });
        tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, ForgotPassword.class));
            finish();
        });

        btnLogin.setOnClickListener(v -> {
            ProgressDialog progress = new ProgressDialog(Login.this);
            progress.setTitle("Logging In");
            progress.setMessage("Please wait while login in.");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();

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
                            if (progress.isShowing()) progress.dismiss();

                            FirebaseMessaging.getInstance().getToken()
                                    .addOnCompleteListener(new OnCompleteListener<String>() {
                                        @Override
                                        public void onComplete(@NonNull Task<String> task) {
                                            if (!task.isSuccessful()) {
                                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                                return;
                                            }
                                            // Get new FCM registration token
                                            String token = task.getResult();
                                            // Log and toast
                                            String msg = "token is : \n" + token;
                                            Log.d(TAG, msg);
//                                            Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            if (progress.isShowing()) progress.dismiss();
                            Snackbar.make(v, "Login Failed", Snackbar.LENGTH_LONG).show();
                        }
                    });
        });


    }

}