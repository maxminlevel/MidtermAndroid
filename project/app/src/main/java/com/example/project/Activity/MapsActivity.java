package com.example.project.Activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.project.Domain.FoodDomain;
import com.example.project.Domain.FoodInRestaurantDomain;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.project.databinding.ActivityMapsBinding;
import com.example.project.R;

import java.io.Serializable;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCircleClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    ArrayList<FoodInRestaurantDomain> foodInResList = new ArrayList<>();
    FoodInRestaurantDomain restaurant;
    FoodDomain food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    private void getBundle() {
        restaurant = (FoodInRestaurantDomain) getIntent().getSerializableExtra("food_store");
        food = (FoodDomain) getIntent().getSerializableExtra("food");
        foodInResList = getIntent().getParcelableArrayListExtra("list_food");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        getBundle();
        mMap = googleMap;
        LatLng self = new LatLng(10.888249399024446, 106.78917099714462);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(self));
        addMarkerYourLocation(self);
        if (restaurant != null) {
            addFoodMarkerOnMap(restaurant.getLat(), restaurant.getLng(), food.getName() + " - " + restaurant.getResName(), restaurant);
            LatLng des = new LatLng(restaurant.getLat(), restaurant.getLng());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(des,16));
        }
        if (foodInResList != null && !foodInResList.isEmpty()) {
            if (!foodInResList.isEmpty()) {
                for (FoodInRestaurantDomain foodInRestaurantDomain : foodInResList) {
                    addFoodMarkerOnMap(foodInRestaurantDomain.getLat(), foodInRestaurantDomain.getLng(), foodInRestaurantDomain.getResName(), foodInRestaurantDomain);
                }
            }
        }

        mMap.setOnMarkerClickListener(this);
        mMap.setOnCircleClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style);
        mMap.setMapStyle(style);
    }

    private void addMarkerYourLocation(LatLng self) {

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
        Canvas canvas1 = new Canvas(bmp);

        Paint color = new Paint();

        canvas1.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.user_loca),80, 80,false), 0,0, color);

        mMap.addMarker(new MarkerOptions()
                .position(self)
                .title("Your Location")
                .snippet("Guess how long this could contain?")
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                .anchor(0.5f, 1)).setTag("self");

    }

    private Circle addCircleOnMap(double lat, double lng, double radius, String name) {
        LatLng position = new LatLng(lat, lng);
        CircleOptions circleOptions = new CircleOptions()
                .center(position)
                .radius(radius)
                .clickable(true)
                .strokeColor(Color.RED)
                .strokeWidth(10f);
        Circle circle = mMap.addCircle(circleOptions);
        return circle;
    }

    private Marker addFoodMarkerOnMap(double lat, double lng, String name, FoodInRestaurantDomain food_store) {
        LatLng position = new LatLng(lat, lng);

        Bitmap bmp = Bitmap.createBitmap(350, 100, Bitmap.Config.ARGB_8888);
        if (food != null) {
            Canvas canvas1 = new Canvas(bmp);
            canvas1.drawColor(Color.GREEN);

            // paint defines the text color, stroke width and size
            Paint color = new Paint();
            color.setTextSize(35);
            color.setColor(Color.BLACK);

            // modify canvas
            int resoureID = getResources().getIdentifier(food.getPic(), "drawable", this.getPackageName());
            if(resoureID==0){
                resoureID = getResources().getIdentifier("food", "drawable", this.getPackageName());
            }
            canvas1.drawBitmap(Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(getResources(),resoureID), 100, 100, false), 0, 0, color);
            canvas1.drawText(name, 100, 40, color);
        }
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                //.icon(BitmapDescriptorFactory.fromBitmap(bmp))
                .title(name)
                .anchor(0.5f, 1);
        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(food_store);
        return marker;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTag().toString() != "self") {
            Intent intent = new Intent(MapsActivity.this, FoodRestaurantActivity.class);
            intent.putExtra("food_store", (Serializable) marker.getTag());
            intent.putExtra("food", (Serializable) food);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onCircleClick(Circle circle) {
        //circle.remove();
    }

    private int NextAvailableID = 1;
    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(MapsActivity.this,"Nhấn vào một món ăn để xem chi tiết",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(MapsActivity.this,"Tạo view mới thêm món ăn",Toast.LENGTH_LONG).show();
    }
}