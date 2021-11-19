package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.project.Adapter.FoodByCategoryAdapter;
import com.example.project.Adapter.PopularAdapter;
import com.example.project.Domain.CategoryDomain;
import com.example.project.Domain.FoodDomain;
import com.example.project.R;

import java.util.ArrayList;

public class FoodByCategoryActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private String catTitle;
    private TextView catName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_by_category);

        recyclerViewFoodCat();

        initView();
        getBundle();
    }

    private void initView() {
        catName = findViewById(R.id.textCat);
    }

    private void getBundle() {
        catTitle = (String) getIntent().getStringExtra("category");

        catName.setText(catTitle);
    }

    private void recyclerViewFoodCat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewFoodCat);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("Cơm sườn trứng", "com_suon_1", "Sườn non, Sườn nướng, Trứng chiên, Trứng cuộn", "thành phần món ăn", 9.0));
        foodList.add(new FoodDomain("Cơm gà sốt thái", "com_ga_sot_thai", "Cơm chiên, Gà chiên mắm, Nước sốt me", "thành phần món ăn", 8.9));
        foodList.add(new FoodDomain("Cơm gà xối mỡ", "com_ga_xoi_mo", "Cơm chiên, Gà hấp xối mỡ, Canh gà", "thành phần món ăn", 6.8));
        foodList.add(new FoodDomain("Cơm dương châu", "com_duong_chau", "Cơm chiên, Xá xíu, Lạp xưởng, Đậu Hà Lan", "thành phần món ăn", 9.0));
        foodList.add(new FoodDomain("Cơm cuộn sushi", "com_cuon_sushi", "Cơm nắm, Rong biển, Trứng cuộn", "thành phần món ăn",9.4));
        foodList.add(new FoodDomain("Cơm gà xé", "com_ga_xe_1", "Cơm chiên, Gà luộc xé, Canh gà trứng", "thành phần món ăn", 7.1));
        foodList.add(new FoodDomain("Cơm chiên cá mặn", "com_chien_ca_man", "Cơm chiên, Trứng gà, Ức gà, Cá mặn", "thành phần món ăn", 8.6));

        adapter = new FoodByCategoryAdapter(foodList);
        recyclerView.setAdapter(adapter);
    }
}