package com.example.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Adapter.FoodAdapter;
import com.example.project.Domain.FoodDomain;
import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FoodByCategoryActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private String catTitle;
    private String catID;
    private TextView catName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_by_category);
        initView();
        getBundle();
        recyclerViewFoodCat();
    }

    private void initView() {
        catName = findViewById(R.id.textCat);
    }

    private void getBundle() {
        catTitle = (String) getIntent().getStringExtra("category");
        catID = (String) getIntent().getStringExtra("categoryID");
        catName.setText(catTitle);
    }

    private void recyclerViewFoodCat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewFoodCat);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList = new ArrayList<>();
        adapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(adapter);
        // Lấy dữ liệu = query có catID
        FirebaseFirestore.getInstance().collection("food")
                .whereEqualTo("category",catID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list_food = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list_food){
                                foodList.add(new FoodDomain(
                                        d.getId(),
                                        d.getString("name"),
                                        d.getString("pic"),
                                        d.getString("desc"),
                                        d.getString("averageRating")
                                ));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

    }
}