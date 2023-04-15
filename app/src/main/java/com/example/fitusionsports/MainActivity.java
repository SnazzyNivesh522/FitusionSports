package com.example.fitusionsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fitusionsports.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomnavigation.setBackground(null);
        binding.bottomnavigation.setOnItemSelectedListener(item -> {
            switch ((int)item.getItemId()){
                case (int)R.id.bottom_home:
                    replacefragment(new HomeFragment());
                    break;
                case (int)R.id.bottom_fitness:
                    replacefragment(new FitnessFragment());
                    break;
                case (int)R.id.bottom_forum:
                    replacefragment(new ChatFragment());
                    break;
                case (int)R.id.bottom_person:
                    replacefragment(new ProfileFragment());
                    break;
            }
            return true;
        });

    }
    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }


}