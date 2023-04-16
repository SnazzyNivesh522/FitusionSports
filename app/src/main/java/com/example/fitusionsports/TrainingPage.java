package com.example.fitusionsports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TrainingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_page);
    }
    public void OpenTrainingPageStartWorkout(View view){
        Intent StartWorkout=new Intent(this,TrainingPageWorkoutStart.class);
        startActivity(StartWorkout);
    }
}