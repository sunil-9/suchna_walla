package com.example.suchnawalla;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.suchnawalla.fragments.HomeFragment;
import com.example.suchnawalla.fragments.ProfileFragment;
import com.example.suchnawalla.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                    if (item.getItemId() == R.id.nav_home)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                    else if (item.getItemId() == R.id.nav_profile)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                    else if (item.getItemId() == R.id.nav_setting)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
                    return true;
                }
        );

    }
}