package com.example.fitusionsports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }
    public void OpenTrainingPageButton(View view){
        Intent intent=new Intent(this,TrainingPage.class);
        startActivity(intent);
    }
}