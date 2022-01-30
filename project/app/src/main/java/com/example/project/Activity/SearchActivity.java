package com.example.project.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Adapter.FoodAdapter;
import com.example.project.Domain.FoodDomain;
import com.example.project.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public RecyclerView recyclerView;
    public FoodAdapter adapter;
    private androidx.appcompat.widget.SearchView searchView;
    private ImageView voiceRecognition;
    private TextView suggestBtn;
    private boolean isSuggested = false;    // nếu đi từ layout Suggest qua
    private String url = new String();
    public ArrayList<FoodDomain> foodList;
    String[] foodListSuggestId; // code cứng dữ liệu foodList được gợi ý

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

        suggestBtn = findViewById(R.id.suggestBtn);
        suggestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, SuggestActivity.class));
            }
        });

        Intent intent = getIntent();
        if(intent.hasExtra("isSuggested")) {
            isSuggested = true;
        }

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

        adapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection("food").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list_food = queryDocumentSnapshots.getDocuments();
                            ArrayList<FoodDomain> arrayGetDB = new ArrayList<>();

                            for (DocumentSnapshot d: list_food){
                                arrayGetDB.add(new FoodDomain(
                                        d.getId(),
                                        d.getString("name"),
                                        d.getString("pic"),
                                        d.getString("desc"),
                                        d.getString("averageRating")
                                ));
                            }

                            if(!isSuggested) {
                                // di tu cac trang khac qua
                                for(int i=0; i<arrayGetDB.size();i++) {
                                    foodList.add(arrayGetDB.get(i));
                                }

                            } else {
                                foodListSuggestId = new String[]{"1", "3", "4", "6", "7"}; // code cứng dữ liệu foodList được gợi ý
                                url = getIntent().getStringExtra("api");

                                RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // Display the first 500 characters of the response string.
                                                // parse response to predict id list
                                                foodListSuggestId = response.split(",");
                                                // di tu Suggest Activity qua
                                                for(int i=0; i<foodListSuggestId.length; i++) {
                                                    for(int j=0;j<arrayGetDB.size();j++) {
                                                        if(arrayGetDB.get(j).getId().equals(foodListSuggestId[i])) {
                                                            Log.d("MML1",arrayGetDB.get(j).getId().toString());
                                                            foodList.add(arrayGetDB.get(j));
                                                            break;
                                                        }
                                                    }
                                                }
                                                adapter.notifyDataSetChanged();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(SearchActivity.this, "Lỗi kết nối đến server",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                queue.add(stringRequest);
                            }

                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
//    final OkHttpClient client = new OkHttpClient();
//
//    String run(String url) throws IOException {
//        Request request = new Request.Builder()
//            .url(url)
//            .build();
//        try {
//            Response response = client.newCall(request).execute();
//            return response.body().string();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        };
//        return null;
//    }

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