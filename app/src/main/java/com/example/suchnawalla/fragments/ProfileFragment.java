package com.example.suchnawalla.fragments;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suchnawalla.Login;
import com.example.suchnawalla.R;
import com.example.suchnawalla.helper.PasswordHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {
    TextView tvTitle;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    EditText etName, etPhone, etPassword, etNewPassword;
    Button btnSave;
    ImageView ivEye,ivEye1;
    boolean isShowPassword = false;
    boolean isShowPassword1 = false;
    String name, phone;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTitle = view.findViewById(R.id.tv_title);
        etName = view.findViewById(R.id.etName);
        etPhone = view.findViewById(R.id.etPhone);
        etPassword = view.findViewById(R.id.etPassword);
        btnSave = view.findViewById(R.id.btnSave);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        ivEye =view.findViewById(R.id.ivEye);
        ivEye.setOnClickListener(view1 -> isShowPassword = PasswordHelper.showPassword(etPassword,ivEye,isShowPassword));

        ivEye1 =view.findViewById(R.id.ivEye1);
        ivEye1.setOnClickListener(view1 -> isShowPassword1 = PasswordHelper.showPassword(etNewPassword,ivEye1,isShowPassword1));
        tvTitle.setText("Profile");
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(getActivity(), "Please Login First", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), Login.class));
            getActivity().finish();
        } else {
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("User")
                    .document(firebaseAuth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            name=documentSnapshot.getString("name");
                            phone=documentSnapshot.getString("phone");
                            etName.setText(documentSnapshot.getString("name"));
                            etPhone.setText(documentSnapshot.getString("phone"));
                            Toast.makeText(getActivity(), "Welcome " + documentSnapshot.getString("name"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "No user found", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), Login.class));
                            getActivity().finish();
                        }
                    });

        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty()) {
                    etName.setError("Enter Name");
                    return;
                }
                if(!etPassword.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Confirm Password");
                    builder.setMessage("Are you sure you want to change password?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                        firebaseAuth.signInWithEmailAndPassword(firebaseAuth.getCurrentUser().getEmail(), etPassword.getText().toString().trim())
                                .addOnSuccessListener(authResult -> {
                                    firebaseAuth.getCurrentUser().updatePassword(etNewPassword.getText().toString().trim())
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(getActivity(), "Password Updated", Toast.LENGTH_SHORT).show();
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.d(TAG, "onClick: "+e.getMessage());
                                                Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
                                })
                                .addOnFailureListener(e -> {
                                    Log.d(TAG, "onClick: "+e.getMessage());
                                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });

                    });
                    builder.setNegativeButton("No", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    });
                    builder.show();
                }


                if(etName.getText().toString().equals(name) && etPhone.getText().toString().equals(phone)){
                    Toast.makeText(getActivity(), "No changes made", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseFirestore.collection("User")
                        .document(firebaseAuth.getCurrentUser().getUid())
                        .update("name", etName.getText().toString(),
                                "phone", etPhone.getText().toString())
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}