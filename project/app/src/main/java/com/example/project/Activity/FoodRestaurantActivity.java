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
import android.widget.Toast;

import com.example.project.Adapter.UserCommentAdapter;
import com.example.project.Domain.UserCommentDomain;
import com.example.project.R;

import java.util.ArrayList;

public class FoodRestaurantActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;

    private ImageView mapIntent, phoneIntent;
    private TextView addressStore, phoneStore, price, rating, nameStore, addRating;

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_restaurant);

        initView();
        handleEventClick();

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

    private void handleEventClick() {
        addRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FoodRestaurantActivity.this,"Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
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
        addRating = findViewById(R.id.addRating);
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
        list.add(new UserCommentDomain("profile1", "Vũ Trường Không", "Món này ngon lắm, mùi vị không tệ"));
        list.add(new UserCommentDomain("profile2", "Hoắc Vũ Hạo", "Vote 10* cho quán ăn này"));
        list.add(new UserCommentDomain("profile3", "Đường Tam", "Về sau sẽ tới quán ăn nhiều hơn nữa"));
        list.add(new UserCommentDomain("profile4", "Bạch Tú Tú", "Quán ăn bán đồ ăn rất ngon, hôm sau sẽ thử món khác tiếp"));
        list.add(new UserCommentDomain("profile5", "Trần Anh Tuấn", "Lần đầu được ăn món này và nó đã trở thành món tủ của tôi"));

        adapter = new UserCommentAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}