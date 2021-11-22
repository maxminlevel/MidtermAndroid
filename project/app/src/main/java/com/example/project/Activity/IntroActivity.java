package com.example.project.Activity;

import static com.example.project.Activity.LoginActivity.MY_PREFS_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.project.R;

public class IntroActivity extends AppCompatActivity {
    private ConstraintLayout startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        boolean firstTime = prefs.getBoolean("firstTime", true);
        if(!firstTime){
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
        }
        setContentView(R.layout.activity_intro);
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean("firstTime", false);
        editor.apply();
        startBtn=findViewById(R.id.startbtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
            }
        });
    }
}