package com.example.fitusionsports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TrainingPageWorkoutStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_page_workout_start);
    }
    public void BackToTrainingPageAndStop_timer(View view){
        Intent BackToTrainingPage =new Intent(this,TrainingPage.class);
        startActivity(BackToTrainingPage);
    }
}