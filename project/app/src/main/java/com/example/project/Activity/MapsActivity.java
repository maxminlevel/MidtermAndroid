package com.example.project.Activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.project.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.project.R;
import java.util.List;

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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng hcmus = new LatLng(10.762913,106.6821717);

        addMarkerOnMap(10.762913,106.6821717, "HCMUS");
        addCircleOnMap(10.8756461, 106.7991699, 1.0, "HCMUS@LinhTrung");


        mMap.setOnMarkerClickListener(this);
        mMap.setOnCircleClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        mMap.addMarker(new MarkerOptions().position(hcmus).title("Marker in HCMUS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmus));


        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
    }

    private Circle addCircleOnMap(double lat, double lng, double radius, String name) {
        LatLng position = new LatLng(lat, lng);
        CircleOptions circleOptions = new CircleOptions()
                .center(position)
                .radius(radius)
                .clickable(true)
                .fillColor(Color.RED)
                .strokeColor(Color.GREEN)
                .strokeWidth(0.5f);
        Circle circle = mMap.addCircle(circleOptions);
        return circle;
    }

    private Marker addMarkerOnMap(double lat, double lng, String name) {
        LatLng position = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title(name)
                .alpha(0.5f)
                .draggable(true)
                .visible(true);
        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag("https://www.hcmus.edu.vn");
        return marker;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String str = (String) marker.getTag();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str));
        startActivity(intent);
        //marker.remove();
        return false;
    }

    @Override
    public void onCircleClick(Circle circle) {
        //circle.remove();
    }

    private int NextAvailableID = 1;
    private Polyline polyline = null;
    @Override
    public void onMapClick(LatLng latLng) {
        if (polyline == null) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .add(latLng)
                    .color(Color.BLUE);
            polyline = mMap.addPolyline(polylineOptions);
        }
        else
        {
            List<LatLng> points = polyline.getPoints();
            points.add(latLng);
            polyline.setPoints(points);
        }

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        addMarkerOnMap(latLng.latitude, latLng.longitude, String.valueOf(NextAvailableID++));
    }
}