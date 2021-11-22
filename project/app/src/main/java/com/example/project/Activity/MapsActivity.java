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

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCircleClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    ArrayList<FoodInRestaurantDomain> foodInResList = new ArrayList<>();

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

//       listRestaurant = (ArrayList <FoodInRestaurant>)intent.getSerializableExtra("restaurant");
        foodInResList =  getIntent().getParcelableArrayListExtra("list_food");
        // set dynamically image


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
//        addFoodMarkerOnMap(foodInResList.get(0).getLat(),foodInResList.get(0).getLng(),foodInResList.get(0).getResName());
//        addFoodMarkerOnMap(foodInResList.get(1).getLat(),foodInResList.get(1).getLng(),foodInResList.get(1).getResName());
        addMarkerYourLocation(self);

//        addFoodMarkerOnMap(10.7568282,106.6796836,"a",null);
//        addFoodMarkerOnMap(10.7661902,106.6835089,"b",null);
        addFoodMarkerOnMap(10.7568282 ,106.6796836,"a",null);
        ArrayList<FoodInRestaurantDomain> markersArray = new ArrayList<>();
        markersArray = foodInResList;
        for(int i = 0 ; i < markersArray.size() ; i++) {

            createMarker(markersArray.get(i).getLat(), markersArray.get(i).getLng(), markersArray.get(i).getResName());
        }



        if(!foodInResList.isEmpty()) {
            for(FoodInRestaurantDomain foodInRestaurantDomain :foodInResList){
                addFoodMarkerOnMap(foodInRestaurantDomain.getLat(), foodInRestaurantDomain.getLng(), foodInRestaurantDomain.getResName(), foodInRestaurantDomain);
            }
        }

                //Log.d("TAG", "onMapReady: "+ foodInRestaurant.getResName());
                //addCircleOnMap(foodInRestaurant.getLat(),foodInRestaurant.getLng(),500,foodInRestaurant.getResName());

        mMap.setOnMarkerClickListener(this);
        mMap.setOnCircleClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);


        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style);
        mMap.setMapStyle(style);
    }
    protected Marker createMarker(double latitude, double longitude, String title) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
               );
    }
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
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
                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                .anchor(0.5f, 1));
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

    private Marker addFoodMarkerOnMap(double lat, double lng, String name, FoodInRestaurantDomain foodInRestaurant) {
        LatLng position = new LatLng(lat, lng);

//        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
//        int width = 350;
//        int heigh = 100;
//        Bitmap bmp = Bitmap.createBitmap(width, heigh, conf);
//        Canvas canvas1 = new Canvas(bmp);
//        canvas1.drawColor(Color.GREEN);
//
//        // paint defines the text color, stroke width and size
//        Paint color = new Paint();
//        color.setTextSize(35);
//        color.setColor(Color.BLACK);
//
//        // modify canvas
//        canvas1.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
//                R.drawable.bun_cha_cat),100, 100,false), 0,0, color);
//        canvas1.drawText(name, 100, 40, color);
//        MarkerOptions markerOptions = new MarkerOptions()
//                .position(position)
//                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
//                .anchor(0.5f, 1);

        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title(name)
                .draggable(true)
                .visible(true);
        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(foodInRestaurant);
        return marker;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

//        Toast.makeText(MapsActivity.this,"Nhấn vào một món ăn để xem chi tiết",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MapsActivity.this, FoodRestaurantActivity.class);
        // Truyen data la
//        intent.putExtra("idStore", restaurant.getId());
//        intent.putExtra("idFood",)


        FoodInRestaurantDomain restaurant = (FoodInRestaurantDomain) marker.getTag();

        intent.putExtra("nameStore",restaurant.getResName());
        intent.putExtra("phoneStore",restaurant.getTel());
        intent.putExtra("addressStore",restaurant.getAddress());
        intent.putExtra("rating",restaurant.getRating());
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

        Toast.makeText(MapsActivity.this,"Nhấn vào một món ăn để xem chi tiết",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(MapsActivity.this,"Tạo view mới thêm món ăn",Toast.LENGTH_LONG).show();
    }
}