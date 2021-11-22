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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

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
        recyclerViewFoodCat();
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
        int resoureID = getResources().getIdentifier(foodDomain.getPic(), "drawable", this.getPackageName());
        if(resoureID==0){
            resoureID = getResources().getIdentifier("food", "drawable", this.getPackageName());
        }
        img.setImageResource(resoureID);
        fName.setText(foodDomain.getName());
        des.setText(foodDomain.getDesc());
        rating.setText(String.valueOf(foodDomain.getAverageRating()));
    }

    private void recyclerViewFoodCat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewRes_Food);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RestaurantListByFoodAdapter(foodInResList);

        recyclerView.setAdapter(adapter);
        // Lấy id món ăn duyệt trên bảng food_store lấy id cửa hàng và giá, sao
        // lấy id cửa hàng duyệt bảng store lấy ten cửa hàng và location
        FirebaseFirestore.getInstance().collection("food_store")
                .whereEqualTo("fID", foodDomain.getId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            for (DocumentSnapshot food_store : queryDocumentSnapshots.getDocuments()){
                                FirebaseFirestore.getInstance().collection("store").document(food_store.getString("sID"))
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot store) {
                                            if(store.exists()) {
                                                GeoPoint geoPoint = store.getGeoPoint("location");
                                                FoodInRestaurantDomain foodInRestaurant = new FoodInRestaurantDomain(
                                                        store.getString("name"),
                                                        food_store.getDouble("rating"),
                                                        food_store.getDouble("price"),
                                                        store.getString("addr"),
                                                        geoPoint.getLatitude(),
                                                        geoPoint.getLongitude(),
                                                        store.getString("tel")
                                                );
                                                foodInRestaurant.setFoodID(foodDomain.getId());
                                                foodInRestaurant.setFood(foodDomain);
                                                foodInResList.add(foodInRestaurant);
                                            }
                                            adapter.notifyDataSetChanged();
                                        }
                                });
                            }
                        }
                    }
                });
    }
}