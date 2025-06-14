package com.valentinesday.kamilly.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.valentinesday.kamilly.R;
import com.valentinesday.kamilly.ui.fragments.DicaDoDiaFragment;
import com.valentinesday.kamilly.ui.fragments.InsegurancaFragment;
import com.valentinesday.kamilly.ui.fragments.PensamentoFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        loadFragment(new DicaDoDiaFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_dica) {
                selectedFragment = new DicaDoDiaFragment();
            } else if (item.getItemId() == R.id.nav_inseguranca) {
                selectedFragment = new InsegurancaFragment();
            } else if (item.getItemId() == R.id.nav_pensamento) {
                selectedFragment = new PensamentoFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}