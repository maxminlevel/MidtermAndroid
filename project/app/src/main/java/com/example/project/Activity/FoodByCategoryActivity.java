package com.example.project.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Adapter.FoodAdapter;
import com.example.project.Domain.FoodDomain;
import com.example.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FoodByCategoryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public RecyclerView recyclerView;
    public FoodAdapter adapter;
    private String catTitle;
    private TextView catName;
    private androidx.appcompat.widget.SearchView searchView;
    private ImageView voiceRecognition;

    public ArrayList<FoodDomain> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_by_category);

        recyclerViewFoodCat();

        initView();
        getBundle();
        bottomNavigation();
    }

    private void initView() {
        catName = findViewById(R.id.textCat);
        searchView = findViewById(R.id.sv_title);
        searchView.setOnQueryTextListener(this);

        voiceRecognition = findViewById(R.id.voice_recognition);
        voiceRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(intent, 200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == RESULT_OK) {
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voice = arrayList.get(0);
            // Toast.makeText(this,voice,Toast.LENGTH_SHORT).show();
            searchView.setQuery(voice, false);
            searchView.setFocusable(true);
        } else {
            Toast.makeText(this,"Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBundle() {
        catTitle = (String) getIntent().getStringExtra("category");

        catName.setText(catTitle);
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
                startActivity(new Intent(FoodByCategoryActivity.this, MainActivity.class));
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodByCategoryActivity.this,SearchActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodByCategoryActivity.this, ProfileActivity.class));
            }
        });

        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodByCategoryActivity.this,FriendActivity.class));
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodByCategoryActivity.this,SettingActivity.class));
            }
        });
    }


    private void recyclerViewFoodCat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewFoodCat);
        recyclerView.setLayoutManager(linearLayoutManager);

        foodList = new ArrayList<>();
        foodList.add(new FoodDomain("Cơm sườn trứng", "com_suon_1", "Sườn non, Sườn nướng, Trứng chiên, Trứng cuộn",  "9.0"));
        foodList.add(new FoodDomain("Cơm gà sốt thái", "com_ga_sot_thai", "Cơm chiên, Gà chiên mắm, Nước sốt me",  "8.9"));
        foodList.add(new FoodDomain("Cơm gà xối mỡ", "com_ga_xoi_mo", "Cơm chiên, Gà hấp xối mỡ, Canh gà", "6.8"));
        foodList.add(new FoodDomain("Cơm dương châu", "com_duong_chau", "Cơm chiên, Xá xíu, Lạp xưởng, Đậu Hà Lan",  "9.0"));
        foodList.add(new FoodDomain("Cơm cuộn sushi", "com_cuon_sushi", "Cơm nắm, Rong biển, Trứng cuộn", "9.4"));
        foodList.add(new FoodDomain("Cơm gà xé", "com_ga_xe_1", "Cơm chiên, Gà luộc xé, Canh gà trứng",  "7.1"));
        foodList.add(new FoodDomain("Cơm chiên cá mặn", "com_chien_ca_man", "Cơm chiên, Trứng gà, Ức gà, Cá mặn", "8.6"));

        adapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<FoodDomain> filter(String text) {
        ArrayList<FoodDomain> filteredList = new ArrayList<>();

        for (FoodDomain item : foodList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchView.clearFocus();
        adapter.filterList(filter(s));
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filterList(filter(s));
        return false;
    }
}