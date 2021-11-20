package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.project.Adapter.FoodAdapter;
import com.example.project.Adapter.RestaurantListByFoodAdapter;
import com.example.project.Domain.FoodDomain;
import com.example.project.Domain.FoodInRestaurant;
import com.example.project.R;

import java.util.ArrayList;

public class RestaurantListByFoodActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private FoodDomain foodDomain;
    private ImageView img;
    private TextView fName, des, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list_by_food);
        recyclerViewFoodCat();
        initView();
        getBundle();
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
          fName.setText(foodDomain.getTitle());
          des.setText(foodDomain.getDescription());
          rating.setText(String.valueOf(foodDomain.getAverageRating()));

//        catTitle = (String) getIntent().getStringExtra("category");
//
//        catName.setText(catTitle);
    }

    private void recyclerViewFoodCat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewRes_Food);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<FoodInRestaurant> foodInResList = new ArrayList<>();
        foodInResList.add(new FoodInRestaurant("Bún đậu thị nở",4.6,35000,"10.887186435398194, 106.78022055111393"));
        foodInResList.add(new FoodInRestaurant("Bún đậu Lão Hạc",4.3,35000,"10.887186435398186, 106.78022055111391"));
        foodInResList.add(new FoodInRestaurant("Bún đậu Tự nhiên",4.7,35000,"10.887186435398188, 106.78022055111398"));

        adapter = new RestaurantListByFoodAdapter(foodInResList);
        recyclerView.setAdapter(adapter);
    }
}