package com.example.project.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project.Adapter.FoodAdapter;
import com.example.project.Domain.FoodDomain;
import com.example.project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public RecyclerView recyclerView;
    public FoodAdapter adapter;
    private androidx.appcompat.widget.SearchView searchView;
    private ImageView voiceRecognition;

    public ArrayList<FoodDomain> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        recyclerViewFoodSearch();
        bottomNavigation();
    }

    private void initView() {
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

    private void bottomNavigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout friendBtn = findViewById(R.id.friendBtn);
        LinearLayout settingBtn = findViewById(R.id.settingBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, ProfileActivity.class));
            }
        });

        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this,FriendActivity.class));
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this,SettingActivity.class));
            }
        });
    }

    private void recyclerViewFoodSearch() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewFoodSearch);
        recyclerView.setLayoutManager(linearLayoutManager);

        foodList = new ArrayList<>();

        foodList.add(new FoodDomain("0","Cơm sườn", "com_suon_1", "Sườn nướng, Trứng chiên, Bì, Chả", "9.0"));
        foodList.add(new FoodDomain("0","Cơm gà sốt thái", "com_ga_sot_thai", "Cơm chiên, Gà chiên mắm, Nước sốt me",  "8.9"));
        foodList.add(new FoodDomain("0","Cơm gà xối mỡ", "com_ga_xoi_mo", "Cơm chiên, Gà hấp xối mỡ, Canh gà", "6.8"));
        foodList.add(new FoodDomain("0","Cơm dương châu", "com_duong_chau", "Cơm chiên, Xá xíu, Lạp xưởng, Đậu Hà Lan",  "9.0"));
        foodList.add(new FoodDomain("0","Cơm cuộn sushi", "com_cuon_sushi", "Cơm nắm, Rong biển, Trứng cuộn", "9.4"));
        foodList.add(new FoodDomain("0","Cơm gà xé", "com_ga_xe_1", "Cơm chiên, Gà luộc xé, Canh gà trứng",  "7.1"));
        foodList.add(new FoodDomain("0","Cơm chiên cá mặn", "com_chien_ca_man", "Cơm chiên, Trứng gà, Ức gà, Cá mặn",  "8.6"));
        foodList.add(new FoodDomain("0","Pizza", "pizza", "Bột mỳ, Sốt cà chua, Phô mai, Topping",  "8.7"));
        foodList.add(new FoodDomain("0","Hamburger", "burger", "Bánh mì, Sa lát, Thịt hun khói, Thịt bò", "9.4"));
        foodList.add(new FoodDomain("0","Lẩu Thái", "lau_thai", "Lẩu thái chua cay ",  "7.5"));
        foodList.add(new FoodDomain("0","Lẩu thập cẩm", "lau_thap_cam", "Lẩu thập cẩm hải sản, bò viên",  "8.9"));

        adapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection("food").get()
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