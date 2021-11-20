package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.project.Adapter.FoodAdapter;
import com.example.project.Adapter.UserCommentAdapter;
import com.example.project.Domain.FoodDomain;
import com.example.project.Domain.UserCommentDomain;
import com.example.project.R;

import java.util.ArrayList;

public class FoodStoreActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_store);

        recyclerViewUserComment();
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