package com.example.fitusionsports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class SportsLibraryActivity extends AppCompatActivity {
    ImageButton BadmintonButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_library);
        BadmintonButton=findViewById(R.id.badmintonButton);
    }
    public void BadmintonPageOpen(View view){
        Intent intent=new Intent(this,activity_badminton.class);
        startActivity(intent);
    }
}