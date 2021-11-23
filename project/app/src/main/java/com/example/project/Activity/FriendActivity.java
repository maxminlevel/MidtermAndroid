package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project.Adapter.MessageAdapter;
import com.example.project.Domain.MessageDomain;
import com.example.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ImageView addFriend;
    private androidx.appcompat.widget.SearchView searchView;

    public RecyclerView recyclerView;
    public MessageAdapter adapter;
    public ArrayList<MessageDomain> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        initView();

        bottomNavigation();
        recyclerViewMessage();

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FriendActivity.this,"Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        addFriend = findViewById(R.id.addFriend);
        searchView = findViewById(R.id.sv_title);
        searchView.setOnQueryTextListener(this);
    }

    private void recyclerViewMessage() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewMessage);
        recyclerView.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();

        list.add(new MessageDomain("profile1", "Khôi Nguyên", "mới đây", "Món này ngon nè!"));
        list.add(new MessageDomain("profile2", "Trần Nhật Hào", "3 phút trước", "Mai đi quán này với t, ăn ngon lắm"));
        list.add(new MessageDomain("profile3", "Trịnh Nguyên Hưng", "10 phút trước", "Mai ra đây nhậu bro :))"));
        list.add(new MessageDomain("profile4", "Huyền Nguyễn", "1 giờ trước", "Lát ghé đây nha"));
        list.add(new MessageDomain("profile5", "Nguyễn Hữu Bình", "1 giờ trước", "Tối nay rủ cả team ra đây làm kèo"));
        list.add(new MessageDomain("profile6", "Mai Duy Nam", "3 giờ trước", "Chỗ này bán ngon nè bro"));
        list.add(new MessageDomain("profile7", "Tín Võ", "5 giờ trước", "Tối ra địa chỉ này nha"));
        list.add(new MessageDomain("profile8", "Thủy Thanh Nguyễn", "7 giờ trước", "Mai rủ mọi người ghé đây ăn nha"));
        list.add(new MessageDomain("profile9", "Nguyễn Trung Chính", "1 ngày trước", "Đá banh xong ra đây ăn lẩu"));
        list.add(new MessageDomain("profile10", "Lê Chí Đại", "3 ngày trước", "Hú, ra đây nhanh m"));

        adapter = new MessageAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<MessageDomain> filter(String text) {
        ArrayList<MessageDomain> filteredList = new ArrayList<>();

        for (MessageDomain item : list) {
            if (item.getUsername().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout settingBtn = findViewById(R.id.settingBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendActivity.this, SearchActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendActivity.this, MainActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FriendActivity.this, ProfileActivity.class));
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FriendActivity.this,SettingActivity.class));
            }
        });
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
