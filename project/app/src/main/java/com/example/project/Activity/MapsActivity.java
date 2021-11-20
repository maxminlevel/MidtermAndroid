package com.example.project.Activity;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Toast;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCircleClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng self = new LatLng(10.762913,106.6821717);
        addCircleOnMap(self.latitude, self.longitude, 1000, "Đồ ăn xung quanh HCMUS");

        // Cần viết thêm class store domain để lưu tọa độ cửa hàng
        // Tham số muốn truyền: id cửa hàng và id món ăn
        // hàm bên trong sẽ check đẻ hiện lên
        // Nếu ID món ăn trống khi dó sẽ load icon cửa hàng và marker link tới view cửa hàng
        // Nếu ID cửa hàng trống khi đó sẽ load icon món ăn và marker link tới view 1 món nhiều cửa hàng
        // Nếu đầy đủ 2 tham số thì marker link tới view 1 món/ 1 cửa hàng
        // marker sử dụng canvas
        addFoodMarkerOnMap(10.762913,106.6821717, "Hành của HCMUS");
        addFoodMarkerOnMap(10.7568282,106.6796836, "Burger");
        addFoodMarkerOnMap(10.7661902,106.6835089, "Chơi đồ án");



        mMap.setOnMarkerClickListener(this);
        mMap.setOnCircleClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(self));

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
        Toast.makeText(MapsActivity.this,"Nhấn vào một món ăn để xem chi tiết",Toast.LENGTH_LONG).show();
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
        addFoodMarkerOnMap(latLng.latitude, latLng.longitude, String.valueOf(NextAvailableID++));
        Toast.makeText(MapsActivity.this,"Tạo view mới thêm món ăn",Toast.LENGTH_LONG).show();
    }
}