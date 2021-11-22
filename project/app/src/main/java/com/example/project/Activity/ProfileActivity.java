package com.example.project.Activity;

import static com.example.project.Activity.LoginActivity.MY_PREFS_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {

    private TextView changeProfileView, linkView, shareView, vipView, username,email, tel, dob, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();
        bottomNavigation();

        handleEventOnClick();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        renderUserInfor(prefs,"fullName", "Unknown name",username);
        renderUserInfor(prefs,"email", "Unknown",email);
        renderUserInfor(prefs,"tel", "Unknown",tel);
        renderUserInfor(prefs,"birthday", "Unknown",dob);
        renderUserInfor(prefs,"gender", "Unknown",gender);
    }

    private void renderUserInfor(SharedPreferences prefs, String key  , String defaultValue, TextView txtView) {
        String value = prefs.getString(key, defaultValue);
        txtView.setText(value);
    }

    private void handleEventOnClick() {
        changeProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this,"Tính năng đang phát triển",Toast.LENGTH_SHORT).show();

            }
        });

        vipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this,"Tính năng đang phát triển",Toast.LENGTH_SHORT).show();
            }
        });

        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this,"Tính năng đang phát triển",Toast.LENGTH_SHORT).show();
            }
        });

        linkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this,"Tính năng đang phát triển",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        changeProfileView = findViewById(R.id.changeProfileBtn);
        vipView = findViewById(R.id.vipBtn);
        linkView = findViewById(R.id.linkBtn);
        shareView = findViewById(R.id.shareBtn);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        tel = findViewById(R.id.tel);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);

    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout friendBtn = findViewById(R.id.friendBtn);
        LinearLayout settingBtn = findViewById(R.id.settingBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SearchActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,FriendActivity.class));
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,SettingActivity.class));
            }
        });
    }
}