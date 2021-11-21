package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.project.Adapter.CategoryAdapter;
import com.example.project.Adapter.PopularAdapter;
import com.example.project.Domain.CategoryDomain;
import com.example.project.Domain.FoodDomain;
import com.example.project.Helper.FirebaseHelper;
import com.example.project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
        ImageView accountBtn= findViewById(R.id.viewAccount);
        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        firebaseHelper = FirebaseHelper.getInstance();
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

        foodList.add(new FoodDomain("Pizza", "pizza", "slices pepperoni ,mozzarella cheese, fresh oregano,  ground black pepper, pizza sauce", 8.7));
        foodList.add(new FoodDomain("Hamburger", "burger", "beef, Gouda Cheese, Special sauce, Lettuce, tomato ",  9.4));
        foodList.add(new FoodDomain("Cơm sườn trứng", "com_suon_1", " Cơm sườn bì Ngô Quyền", 8.4));
        foodList.add(new FoodDomain("Lẩu Thái", "lau_thai", "Lẩu thái chua cay ",  7.5));
        foodList.add(new FoodDomain("Lẩu thập cẩm", "lau_thap_cam", "Lẩu thập cẩm hải sản, bò viên",  8.9));

        adapter2 = new PopularAdapter(foodList);
        recyclerViewPopularList.setAdapter(adapter2);

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        adapter = new CategoryAdapter(categoryList);

        recyclerViewCategoryList.setAdapter(adapter);
        FirebaseFirestore.getInstance().collection("food_category").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list){
                                CategoryDomain c = d.toObject(CategoryDomain.class);
                                categoryList.add(c);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}