package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.project.Adapter.FoodByCategoryAdapter;
import com.example.project.Adapter.PopularAdapter;
import com.example.project.Domain.FoodDomain;
import com.example.project.R;

import java.util.ArrayList;

public class FoodByCategoryActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_by_category);

        recyclerViewFoodCat();
    }

    private void recyclerViewFoodCat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewFoodCat);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodlist = new ArrayList<>();
        foodlist.add(new FoodDomain("Cơm sườn trứng", "com_suon_1", "Sườn non, Sườn nướng, Trứng chiên, Trứng cuộn", 9.76));
        foodlist.add(new FoodDomain("Cơm gà sốt thái", "com_ga_sot_thai", "Cơm chiên, Gà chiên mắm, Nước sốt me", 8.79));
        foodlist.add(new FoodDomain("Cơm gà xối mỡ", "com_ga_xoi_mo", "Cơm chiên, Gà hấp xối mỡ, Canh gà", 8.5));
        foodlist.add(new FoodDomain("Cơm dương châu", "com_duong_chau", "Cơm chiên, Xá xíu, Lạp xưởng, Đậu Hà Lan", 10.0));
        foodlist.add(new FoodDomain("Cơm cuộn sushi", "com_cuon_sushi", "Cơm nắm, Rong biển, Trứng cuộn", 9.5));
        foodlist.add(new FoodDomain("Cơm gà xé", "com_ga_xe_1", "Cơm chiên, Gà luộc xé, Canh gà trứng", 9.5));
        foodlist.add(new FoodDomain("Cơm chiên cá mặn", "com_chien_ca_man", "Cơm chiên, Trứng gà, Ức gà, Cá mặn", 9.5));

        adapter = new FoodByCategoryAdapter(foodlist);
        recyclerView.setAdapter(adapter);
    }
}