package com.example.suchnawalla.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suchnawalla.Login;
import com.example.suchnawalla.R;
import com.example.suchnawalla.adapter.NoticeAdapter;
import com.example.suchnawalla.model.NoticeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView tvTitle;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    List<NoticeModel> noticeModelList;
    NoticeAdapter noticeAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("home");
        Log.d("TAG", "onViewCreated: home");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noticeModelList = new ArrayList<NoticeModel>();
        noticeAdapter = new NoticeAdapter(getContext(), noticeModelList);
        if (firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore.collection("Notice").addSnapshotListener((value, error) -> {
                if (error != null) {
                    Log.d("TAG", "onViewCreated: " + error.getMessage());
                } else {
                    noticeModelList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : value) {
                        NoticeModel noticeModel = documentSnapshot.toObject(NoticeModel.class);

                        noticeModelList.add(noticeModel);
                    }
                    noticeAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(noticeAdapter);
                }
            });
        }else{
            Toast.makeText(getContext(), "Please Login First", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), Login.class));
            getActivity().finish();

            Log.d("TAG", "onViewCreated: no user");
        }


        super.onViewCreated(view, savedInstanceState);
    }
}