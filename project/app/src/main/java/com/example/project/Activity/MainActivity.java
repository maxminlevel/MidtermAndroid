package com.example.project.Activity;

import static com.example.project.Activity.LoginActivity.MY_PREFS_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project.Adapter.CategoryAdapter;
import com.example.project.Adapter.PopularAdapter;
import com.example.project.Domain.CategoryDomain;
import com.example.project.Domain.FoodDomain;
import com.example.project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter categoryAdapter, popularAdapter;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
        Button accountBtn= findViewById(R.id.viewAccountBtn);
        ImageView avatarImg = findViewById(R.id.Avatar);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        if(!prefs.getString("fullName","").isEmpty()){
            accountBtn.setVisibility(View.GONE);
            avatarImg.setVisibility(View.VISIBLE);
            TextView accountName = findViewById(R.id.usernameInMain);
            renderUserInfor(prefs,accountName);
            if(!prefs.getString("avatar","").isEmpty()) {
                int imageResource = getResources().getIdentifier(prefs.getString("avatar",""), "drawable", getPackageName());
                Glide.with(this).load(imageResource).into(avatarImg);
            }
        }
        else{
            accountBtn.setVisibility(View.VISIBLE);
            avatarImg.setVisibility(View.GONE);
        }

        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    private void renderUserInfor(SharedPreferences prefs, TextView accountName) {
        String name = prefs.getString("fullName", "Xin chào");
        //Log.d("TAG", "renderUserInfor: "+name);
        accountName.setText("Xin chào " + name);
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
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,FriendActivity.class));
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
            }
        });
    }

    private void recyclerViewPopular() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList = new ArrayList<>();

        popularAdapter = new PopularAdapter(foodList);
        recyclerViewPopularList.setAdapter(popularAdapter);
        FirebaseFirestore.getInstance().collection("food").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list_food = queryDocumentSnapshots.getDocuments();
                            ArrayList<FoodDomain> arrayTmp = new ArrayList<>();

                            for (DocumentSnapshot d: list_food){
                                arrayTmp.add(new FoodDomain(
                                        d.getId(),
                                        d.getString("name"),
                                        d.getString("pic"),
                                        d.getString("desc"),
                                        d.getString("averageRating")
                                ));
                            }

                            for(int i = 0; i < arrayTmp.size() - 1; i++) {
                                for(int j = i; j < arrayTmp.size(); j++) {
                                    if(Double.parseDouble(arrayTmp.get(i).getAverageRating()) < Double.parseDouble(arrayTmp.get(j).getAverageRating()))
                                        Collections.swap(arrayTmp, i, j);
                                }
                            }

                            for(int i=0; i<5; i++) {
                                foodList.add(arrayTmp.get(i));
                            }

                            popularAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryList);

        recyclerViewCategoryList.setAdapter(categoryAdapter);
        FirebaseFirestore.getInstance().collection("food_category").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list){
                                CategoryDomain c = d.toObject(CategoryDomain.class);
                                categoryList.add(new CategoryDomain(
                                        d.getId(),
                                        d.getString("name"),
                                        d.getString("pic")
                                ));
                            }
                            categoryAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}