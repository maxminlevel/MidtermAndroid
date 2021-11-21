package com.example.project.Activity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.project.Domain.FoodDomain;
import com.example.project.Domain.FoodInRestaurant;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.project.databinding.ActivityMapsBinding;
import com.example.project.R;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.Serializable;
import java.util.ArrayList;

public class OneMarkerMapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCircleClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FoodInRestaurant restaurant = null;
    ArrayList<FoodInRestaurant> foodInResList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        getBundle();

    }
    private void getBundle() {

        restaurant = (FoodInRestaurant) getIntent().getSerializableExtra("restaurant");
//       listRestaurant = (ArrayList <FoodInRestaurant>)intent.getSerializableExtra("restaurant");


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        // Add a marker
        // Toạ độ KTX khu B
        LatLng self = new LatLng(10.888249399024446, 106.78917099714462);
//        CameraPosition point = new CameraPosition.Builder()
//                .target(self)
//                .zoom(16)
//                .bearing(90)
//                .tilt(30)
//                .build()
//                ;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(self));

        //addCircleOnMap(self.latitude, self.longitude, 1000, "Đồ ăn xung quanh HCMUS");

        // Cần viết thêm class store domain để lưu tọa độ cửa hàng
        // Tham số muốn truyền: id cửa hàng và id món ăn
        // hàm bên trong sẽ check đẻ hiện lên
        // Nếu ID món ăn trống khi dó sẽ load icon cửa hàng và marker link tới view cửa hàng
        // Nếu ID cửa hàng trống khi đó sẽ load icon món ăn và marker link tới view 1 món nhiều cửa hàng
        // Nếu đầy đủ 2 tham số thì marker link tới view 1 món/ 1 cửa hàng
        // marker sử dụng canvas
        // addFoodMarkerOnMap(10.88718643539816, 106.78022055111391, "Hành của HCMUS");
//        addFoodMarkerOnMap(10.7568282,106.6796836, "Burger");
//        addFoodMarkerOnMap(10.7661902,106.6835089, "Chơi đồ án");
//        if(restaurant!=null){
//            //addFoodMarkerOnMap(restaurant.getLat(),restaurant.getLng(), restaurant.getResName());
//            //LatLng self = new LatLng(restaurant.getLat(),restaurant.getLng());
//        }
        if(restaurant != null) {
            addFoodMarkerOnMap(restaurant.getLat(), restaurant.getLng(), restaurant.getResName());
        }



        mMap.setOnMarkerClickListener(this);
        mMap.setOnCircleClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);


        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style);
        mMap.setMapStyle(style);
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

    private Marker addFoodMarkerOnMap(double lat, double lng, String name) {
        LatLng position = new LatLng(lat, lng);

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        int width = 350;
        int heigh = 100;
        Bitmap bmp = Bitmap.createBitmap(width, heigh, conf);
        Canvas canvas1 = new Canvas(bmp);
        canvas1.drawColor(Color.GREEN);

        // paint defines the text color, stroke width and size
        Paint color = new Paint();
        color.setTextSize(35);
        color.setColor(Color.BLACK);

        // modify canvas
        canvas1.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.bun_cha_cat),100, 100,false), 0,0, color);
        canvas1.drawText(name, 100, 40, color);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                .anchor(0.5f, 1);
        Marker marker = mMap.addMarker(markerOptions);
        return marker;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Intent intent = new Intent(OneMarkerMapsActivity.this, FoodStoreActivity.class);
        // Truyen data la
//        intent.putExtra("idStore", restaurant.getId());
//        intent.putExtra("idFood",)
        startActivity(intent);


        return false;
    }

    @Override
    public void onCircleClick(Circle circle) {
        //circle.remove();
    }

    private int NextAvailableID = 1;
    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(OneMarkerMapsActivity.this,"Nhấn vào một món ăn để xem chi tiết",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        addFoodMarkerOnMap(latLng.latitude, latLng.longitude, String.valueOf(NextAvailableID++));
        Toast.makeText(OneMarkerMapsActivity.this,"Tạo view mới thêm món ăn",Toast.LENGTH_LONG).show();
    }
}