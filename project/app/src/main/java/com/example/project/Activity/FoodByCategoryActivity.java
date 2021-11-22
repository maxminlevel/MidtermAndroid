package com.example.project.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FoodByCategoryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public RecyclerView recyclerView;
    public FoodAdapter adapter;
    private String catTitle;
    private String catID;
    private TextView catName;
    private androidx.appcompat.widget.SearchView searchView;
    private ImageView voiceRecognition;

    public ArrayList<FoodDomain> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_by_category);
        initView();
        getBundle();
        recyclerViewFoodCat();
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
        catID = (String) getIntent().getStringExtra("categoryID");
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


        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList = new ArrayList<>();
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