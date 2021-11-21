package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.Adapter.UserCommentAdapter;
import com.example.project.Domain.UserCommentDomain;
import com.example.project.R;

import java.util.ArrayList;

public class FoodRestaurantActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;

    private ImageView mapIntent, phoneIntent;
    private TextView addressStore, phoneStore, price, rating, nameStore;

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_restaurant);

        initView();

        recyclerViewUserComment();
        getBundle();

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

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();

        price.setText(String.valueOf(bundle.getDouble("price", 10000)) + " VND");
        nameStore.setText(bundle.getString("nameStore", "Tên mẫu"));
        phoneStore.setText(bundle.getString("phoneStore", "SĐT mẫu"));
        addressStore.setText(bundle.getString("addressStore","Địa chỉ mẫu"));
        rating.setText(String.valueOf(bundle.getDouble("rating", 10.0)));
    }

    private void initView() {
        mapIntent = findViewById(R.id.mapIntent);
        phoneIntent = findViewById(R.id.phoneIntent);
        addressStore = findViewById(R.id.addressStore);
        phoneStore = findViewById(R.id.phoneStore);
        nameStore = findViewById(R.id.nameStore);
        price = findViewById(R.id.price);
        rating = findViewById(R.id.rating);
    }

    private void makePhoneCall() {
        String number = phoneStore.getText().toString();

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    private void showAddressStore() {
        String latitude = "10.688289";
        String longitude = "105.376065";

        String location = "geo:" + latitude + "," + longitude;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(location));
        startActivity(intent);
    }

    private void recyclerViewUserComment() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewComment);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<UserCommentDomain> list = new ArrayList<>();
        list.add(new UserCommentDomain("user", "user1", "Bình luận gì đó về món ăn của cửa hàng này"));
        list.add(new UserCommentDomain("user", "user2", "Bình luận gì đó về món ăn của cửa hàng này"));
        list.add(new UserCommentDomain("user", "user3", "Bình luận gì đó về món ăn của cửa hàng này"));
        list.add(new UserCommentDomain("user", "user4", "Bình luận gì đó về món ăn của cửa hàng này"));
        list.add(new UserCommentDomain("user", "user5", "Bình luận gì đó về món ăn của cửa hàng này"));
        list.add(new UserCommentDomain("user", "user6", "Bình luận gì đó về món ăn của cửa hàng này"));
        list.add(new UserCommentDomain("user", "user7", "Bình luận gì đó về món ăn của cửa hàng này"));

        adapter = new UserCommentAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}