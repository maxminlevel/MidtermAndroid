package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SuggestActivity extends AppCompatActivity {
    CheckBox q1_dried, q1_water, q1_both;
    CheckBox q2_fast, q2_medium, q2_long;
    CheckBox q3_sour, q3_salty, q3_sweet, q3_medium;
    CheckBox q4_alone, q4_group;
    CheckBox q5_high, q5_medium, q5_low;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        initView();
        bottomNavigation();
    }

    private void initView() {
        q1_dried = findViewById(R.id.Q1_dried);
        q1_water = findViewById(R.id.Q1_water);
        q1_both = findViewById(R.id.Q1_both);

        q2_fast = findViewById(R.id.Q2_fast);
        q2_medium = findViewById(R.id.Q2_medium);
        q2_long = findViewById(R.id.Q2_long);

        q3_sour = findViewById(R.id.Q3_sour);
        q3_salty = findViewById(R.id.Q3_salty);
        q3_sweet = findViewById(R.id.Q3_sweet);
        q3_medium = findViewById(R.id.Q2_medium);

        q4_alone = findViewById(R.id.Q4_alone);
        q4_group = findViewById(R.id.Q4_group);

        q5_high = findViewById(R.id.Q5_high);
        q5_medium = findViewById(R.id.Q5_medium);
        q5_low = findViewById(R.id.Q5_low);
    }

    private String parseData()
    {
        String res = "";
        // Q1
        res += q1_dried.isChecked() ? "1 ": "0 ";
        res += q1_water.isChecked() ? "1 ": "0 ";
        res += q1_both.isChecked() ? "1 ": "0 ";

        // Q2
        res += q2_fast.isChecked() ? "1 ": "0 ";
        res += q2_medium.isChecked() ? "1 ": "0 ";
        res += q2_long.isChecked() ? "1 ": "0 ";

        // Q3
        res += q3_sour.isChecked() ? "1 ": "0 ";
        res += q3_salty.isChecked() ? "1 ": "0 ";
        res += q3_sweet.isChecked() ? "1 ": "0 ";
        res += q3_medium.isChecked() ? "1 ": "0 ";

        // Q4
        res += q4_alone.isChecked() ? "1 ": "0 ";
        res += q4_group.isChecked() ? "1 ": "0 ";

        // Q5
        res += q5_high.isChecked() ? "1 ": "0 ";
        res += q5_medium.isChecked() ? "1 ": "0 ";
        res += q5_low.isChecked() ? "1 ": "0 ";

        return res;
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout friendBtn = findViewById(R.id.friendBtn);
        LinearLayout settingBtn = findViewById(R.id.settingBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuggestActivity.this, MainActivity.class));
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              need some bundle here
                String res = parseData();
                Toast.makeText(SuggestActivity.this, res, Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(SuggestActivity.this, SearchActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuggestActivity.this, ProfileActivity.class));
            }
        });

        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuggestActivity.this,FriendActivity.class));
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SuggestActivity.this,SettingActivity.class));
            }
        });
    }
}