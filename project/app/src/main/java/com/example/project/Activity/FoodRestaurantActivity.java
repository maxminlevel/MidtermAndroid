package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Adapter.MessageAdapter;
import com.example.project.Adapter.UserCommentAdapter;
import com.example.project.Domain.FoodDomain;
import com.example.project.Domain.FoodInRestaurantDomain;
import com.example.project.Domain.UserCommentDomain;
import com.example.project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodRestaurantActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;

    private ImageView mapIntent, phoneIntent, foodImg;
    private TextView addressStore, phoneStore, price, rating, nameStore, addRating, foodName, foodDesc;

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    FoodInRestaurantDomain foodInRestaurant;
    FoodDomain food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_restaurant);

        initView();
        handleEventClick();
        getBundle();

        recyclerViewUserComment();

        phoneIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });

        mapIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddressStore();
            }
        });
    }

    private void handleEventClick() {
        addRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FoodRestaurantActivity.this,"Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBundle() {
        foodInRestaurant = (FoodInRestaurantDomain) getIntent().getSerializableExtra("food_store");
        price.setText(foodInRestaurant.getPrice()+ " VND");
        nameStore.setText(foodInRestaurant.getResName());
        phoneStore.setText(foodInRestaurant.getTel());
        addressStore.setText(foodInRestaurant.getAddress());
        rating.setText(foodInRestaurant.getRating()+"");

        food = (FoodDomain) getIntent().getSerializableExtra("food");
        if (food == null) {
            FirebaseFirestore.getInstance().collection("food").document(foodInRestaurant.getFoodID())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot d) {
                            food = new FoodDomain(
                                    d.getId(),
                                    d.getString("name"),
                                    d.getString("category"),
                                    d.getString("desc"),
                                    d.getString("averageRating")
                            );
                        }
                    });
        }
        foodName.setText(food.getName());
        foodDesc.setText(food.getDesc());
        int resoureID = getResources().getIdentifier(food.getPic(), "drawable", this.getPackageName());
        if(resoureID==0){
            resoureID = getResources().getIdentifier("food", "drawable", this.getPackageName());
        }
        foodImg.setImageResource(resoureID);
    }

    private void initView() {
        mapIntent = findViewById(R.id.mapIntent);
        phoneIntent = findViewById(R.id.phoneIntent);
        addressStore = findViewById(R.id.addressStore);
        phoneStore = findViewById(R.id.phoneStore);
        nameStore = findViewById(R.id.nameStore);
        price = findViewById(R.id.price);
        rating = findViewById(R.id.rating);
        addRating = findViewById(R.id.addRating);
        foodName = findViewById(R.id.foodName);
        foodImg = findViewById(R.id.foodImg);
        foodDesc = findViewById(R.id.foodDesc);
    }

    private void makePhoneCall() {
        String number = phoneStore.getText().toString();

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    private void showAddressStore() {
//        String latitude = "10.688289";
//        String longitude = "105.376065";
//
//        String location = "geo:" + latitude + "," + longitude;
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(location));
        Intent intent = new Intent(FoodRestaurantActivity.this, MapsActivity.class);
        intent.putExtra("restaurant",  (Serializable) foodInRestaurant);
        startActivity(intent);
    }

    private void recyclerViewUserComment() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewComment);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<UserCommentDomain> list = new ArrayList<>();
        list.add(new UserCommentDomain("profile1", "Vũ Trường Không", "Món này ngon lắm, mùi vị không tệ"));
        list.add(new UserCommentDomain("profile2", "Hoắc Vũ Hạo", "Vote 10* cho quán ăn này"));
        list.add(new UserCommentDomain("profile3", "Đường Tam", "Về sau sẽ tới quán ăn nhiều hơn nữa"));
        list.add(new UserCommentDomain("profile4", "Bạch Tú Tú", "Quán ăn bán đồ ăn rất ngon, hôm sau sẽ thử món khác tiếp"));
        list.add(new UserCommentDomain("profile5", "Trần Anh Tuấn", "Lần đầu được ăn món này và nó đã trở thành món tủ của tôi"));

        adapter = new UserCommentAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}