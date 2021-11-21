package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private TextView fName, des, rating, viewAllLo;
    ArrayList<FoodInRestaurant> foodInResList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list_by_food);
        recyclerViewFoodCat();
        initView();
        getBundle();
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
        viewAllLo = findViewById(R.id.viewAllLocation);
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

    private void recyclerViewFoodCat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewRes_Food);
        recyclerView.setLayoutManager(linearLayoutManager);


        // Query de lay danh sach cac quan an
        foodInResList.add(new FoodInRestaurant("Bún đậu thị nở",4.6,35000,"317 Đông Hoà, Dĩ An, Bình Dương",10.88718643539819, 106.78022055111393));
        foodInResList.add(new FoodInRestaurant("Bún đậu Lão Hạc",4.3,35000,"108 Đông Hoà, Dĩ An, Bình Dương",10.887186435498186, 106.78022055111391));
        foodInResList.add(new FoodInRestaurant("Bún đậu Tự nhiên",4.7,35000,"59 Linh Trung, Thủ Đức, Thành phố Hồ Chí Minh",10.887186436398188, 106.78022055111398));

        adapter = new RestaurantListByFoodAdapter(foodInResList);
        recyclerView.setAdapter(adapter);
    }
}