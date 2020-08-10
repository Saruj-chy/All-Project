package com.example.allproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.example.allproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);


        String location = getIntent().getExtras().getString("resturant_name").toString();
        Log.d("TAG", "location: "+ location) ;

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        searchView = findViewById(R.id.sv_location);
        searchView.setQuery(location, true);

        List<Address>  addressList = null;
        if(location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(GoogleMapActivity.this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            latLng = new LatLng(address.getLatitude(), address.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();

            Log.d("TAG", "lating: "+latLng) ;
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address>  addressList = null;

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(GoogleMapActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    map.clear();
                    map.addMarker(markerOptions.position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        mapFragment.getMapAsync(this);




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.addMarker(new MarkerOptions().position(latLng));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }
}