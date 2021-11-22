package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.Adapter.RestaurantListByFoodAdapter;
import com.example.project.Domain.FoodDomain;
import com.example.project.Domain.FoodInRestaurantDomain;
import com.example.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RestaurantListByFoodActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private FoodDomain foodDomain;
    private ImageView img;
    private TextView fName, des, rating;
    ArrayList<FoodInRestaurantDomain> foodInResList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list_by_food);
        recyclerViewFoodCat();
        initView();
        getBundle();

        bottomNavigation();

        TextView viewAllLo = findViewById(R.id.viewAllLocation);
        viewAllLo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantListByFoodActivity.this,MapsActivity.class);
                intent.putParcelableArrayListExtra("list_food", foodInResList);
                startActivity(intent);
            }
        });
    }

    private void initView() {

        img = findViewById(R.id.imageFood);
        fName = findViewById(R.id.foodName);
        des = findViewById(R.id.foodDescription);
        rating = findViewById(R.id.rating_food_res_avg);

    }

    private void getBundle() {
          String uri = "drawable";
          foodDomain = (FoodDomain) getIntent().getSerializableExtra("object");

          // set dynamically image
          img.setImageResource(getResources().getIdentifier(
                  foodDomain.getPic(), "drawable", this.getPackageName()));
          fName.setText(foodDomain.getName());
          des.setText(foodDomain.getDesc());
          rating.setText(String.valueOf(foodDomain.getAverageRating()));
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
                startActivity(new Intent(RestaurantListByFoodActivity.this, MainActivity.class));
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestaurantListByFoodActivity.this,SearchActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestaurantListByFoodActivity.this, ProfileActivity.class));
            }
        });

        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestaurantListByFoodActivity.this,FriendActivity.class));
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestaurantListByFoodActivity.this,SettingActivity.class));
            }
        });
    }

    private void recyclerViewFoodCat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewRes_Food);
        recyclerView.setLayoutManager(linearLayoutManager);


        foodInResList.add(new FoodInRestaurantDomain("Bún đậu thị nở",4.6,35000,"Kế bên KTX khu B", 10.887186435398194, 106.78022055111393, "0123456789"));
        foodInResList.add(new FoodInRestaurantDomain("Bún đậu Lão Hạc",4.3,30000,"khu phố 6 Linh Trung",10.887186435398186, 106.78022055111391, "03564829665"));
        foodInResList.add(new FoodInRestaurantDomain("Bún đậu Tự nhiên",4.7,30000,"Chợ ẩm thực làng ĐH",10.887186435398188, 106.78022055111398, "0158396839"));
        foodInResList.add(new FoodInRestaurantDomain("Bún đậu Nhà Làm",7.8,25000,"Đường vành đai làng ĐH",10.887186435398188, 106.78022055111398, "0529582960"));

        adapter = new RestaurantListByFoodAdapter(foodInResList);
        recyclerView.setAdapter(adapter);
    }
}